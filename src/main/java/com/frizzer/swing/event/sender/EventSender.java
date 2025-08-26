package com.frizzer.swing.event.sender;

import com.frizzer.swing.registry.UUIDProvider;
import com.frizzer.swing.task.BaseTask;
import com.frizzer.swing.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.swing.*;

@Service
@Slf4j
public class EventSender {

    private final JmsTemplate jmsTemplate;
    private final UUIDProvider idProvider;

    public EventSender(JmsTemplate jmsTemplate, UUIDProvider idProvider) {
        this.jmsTemplate = jmsTemplate;
        this.idProvider = idProvider;
    }

    public void sendEvent(Event event) {
        log.info("Sending event: {}", event);
        jmsTemplate.convertAndSend("eventTopic", event);
    }

    public void executeAndSend(BaseTask<?,?> task) {
        task.execute();
        task.addPropertyChangeListener(change -> {
            if (change.getNewValue() == SwingWorker.StateValue.DONE) {
                sendEvent(task.createEvent(idProvider.getId()));
            }
        });
    }

}
