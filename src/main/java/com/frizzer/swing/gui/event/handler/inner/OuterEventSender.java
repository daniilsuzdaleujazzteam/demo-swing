package com.frizzer.swing.gui.event.handler.inner;

import com.frizzer.swing.gui.event.event.Event;
import com.frizzer.swing.gui.event.event.impl.EntityChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static com.frizzer.swing.config.IdGenerator.INSTANCE_ID;
import static com.frizzer.swing.gui.event.event.DataChangeType.LOAD;

@RequiredArgsConstructor
@Component
@Slf4j
public class OuterEventSender extends InnerEventHandler {

    private final JmsTemplate jmsTemplate;

    @Override
    public void handle(Event event) {
        if (excludeFilter(event)) {
            log.debug("Skipping event {}", event);
            return;
        }
        jmsTemplate.convertAndSend("eventTopic", event);
    }

    private boolean excludeFilter(Event event) {
        return notFromThisInstance(event) || isLoadTask(event);
    }

    private boolean notFromThisInstance(Event event) {
        return !event.getInstanceId().equals(INSTANCE_ID);
    }

    private boolean isLoadTask(Event event) {
        return event instanceof EntityChangedEvent<?> entityChangedEvent && entityChangedEvent.dataChangeType() == LOAD;
    }
}
