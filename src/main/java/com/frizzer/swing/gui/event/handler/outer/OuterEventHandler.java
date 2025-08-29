package com.frizzer.swing.gui.event.handler.outer;

import com.frizzer.swing.gui.event.event.Event;
import com.frizzer.swing.gui.event.handler.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.frizzer.swing.config.IdGenerator.INSTANCE_ID;

@Slf4j
@Component
public abstract class OuterEventHandler implements EventHandler {

    @JmsListener(destination = "eventTopic")
    @Override
    public void preHandle(Event event) {
        if (Objects.equals(event.getInstanceId(), INSTANCE_ID)) {
            log.debug("Skipping event {}", event);
            return;
        }
        log.info("Listening event {} from instance {}", event.getClass(), event.getInstanceId());
        handle(event);
    }

}
