package com.banking.cqrs.core.events;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "eventStore")
public class EventModel {

    @Id
    private String id;
    private LocalDateTime timeStamp;
    private String aggregateIdentifier;
    private String aggregateType;
    private Integer version;
    private String eventType;
    private BaseEvent eventData;
}
