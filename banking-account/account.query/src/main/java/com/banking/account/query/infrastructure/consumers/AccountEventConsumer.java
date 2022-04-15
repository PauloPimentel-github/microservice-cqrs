package com.banking.account.query.infrastructure.consumers;

import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithdrawnEvent;
import com.banking.account.query.infrastructure.handlers.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class AccountEventConsumer implements EventConsumer{

    public static final String KAFKA_CONSUMER_GROUP_ID = "${spring.kafka.consumer.group-id}";

    @Autowired
    private EventHandler eventHandler;

    @KafkaListener(topics = "AccountOpenedEvent", groupId = KAFKA_CONSUMER_GROUP_ID)
    @Override
    public void consume(@Payload AccountOpenedEvent event, Acknowledgment acknowledgment) {
        this.eventHandler.on(event);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "FundsDepositedEvent", groupId = KAFKA_CONSUMER_GROUP_ID)
    @Override
    public void consume(@Payload FundsDepositedEvent event, Acknowledgment acknowledgment) {
        this.eventHandler.on(event);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "FundsWithdrawnEvent", groupId = KAFKA_CONSUMER_GROUP_ID)
    @Override
    public void consume(@Payload FundsWithdrawnEvent event, Acknowledgment acknowledgment) {
        this.eventHandler.on(event);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "AccountClosedEvent", groupId = KAFKA_CONSUMER_GROUP_ID)
    @Override
    public void consume(@Payload AccountClosedEvent event, Acknowledgment acknowledgment) {
        this.eventHandler.on(event);
        acknowledgment.acknowledge();
    }
}
