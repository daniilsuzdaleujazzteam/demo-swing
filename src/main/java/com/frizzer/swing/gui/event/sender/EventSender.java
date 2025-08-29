package com.frizzer.swing.gui.event.sender;

import com.frizzer.swing.gui.event.event.Event;
import com.frizzer.swing.gui.event.event.impl.EntityChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import static com.frizzer.swing.config.IdGenerator.INSTANCE_ID;
import static com.frizzer.swing.gui.event.event.DataChangeType.LOAD;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventSender {

    private final JmsTemplate jmsTemplate;

    @EventListener(Event.class)
    public void sendEvent(Event event) {
        if (filter(event)) {
            return;
        }
        log.info("Sending event: {}", event);
        jmsTemplate.convertAndSend("eventTopic", event);
    }

    private boolean filter(Event event) {
        return notFromThisInstance(event) || isLoadTask(event);
    }

    private boolean notFromThisInstance(Event event) {
        return !event.getInstanceId().equals(INSTANCE_ID);
    }

    private boolean isLoadTask(Event event) {
        return event instanceof EntityChangedEvent<?> entityChangedEvent && entityChangedEvent.dataChangeType() == LOAD;
    }

}
