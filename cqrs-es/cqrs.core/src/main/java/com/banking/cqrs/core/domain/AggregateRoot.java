package com.banking.cqrs.core.domain;

import com.banking.cqrs.core.events.BaseEvent;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class AggregateRoot {

    protected String id;
    private Integer version = -1;

    private final List<BaseEvent> changes = new ArrayList<>();

    public String getId() {
        return this.id;
    }

    public int getVersion(){
        return this.version;
    }

    public void setVersion(int version){
        this.version = version;
    }

    public List<BaseEvent> getUncommitedChanges(){
        return this.changes;
    }

    public void markChangesAsCommitted(){
        this.changes.clear();
    }

    protected void applyChange(BaseEvent event, boolean isNewEvent){
        try {
            var method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch(NoSuchMethodException noSuchMethodException) {
            log.info(MessageFormat.format("Apply method not found for: {0}", event.getClass().getName()));
        } catch(Exception exception) {
            log.error("Error applying aggregate event", exception);
        } finally {
            if (isNewEvent) {
                changes.add(event);
            }
        }
    }

    public void raiseEvent(BaseEvent event){
        applyChange(event, true);
    }

    public void replayEvents(Iterable<BaseEvent> events){
        events.forEach(event -> applyChange(event, false));
    }
}


