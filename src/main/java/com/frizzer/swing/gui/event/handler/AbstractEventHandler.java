package com.frizzer.swing.gui.event.handler;

import com.frizzer.swing.gui.event.event.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.frizzer.swing.config.IdGenerator.INSTANCE_ID;

@Component
@Slf4j
@RequiredArgsConstructor
public class AbstractEventHandler implements EventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void handle(Event event) {
        log.info("Event of class {} received from instance {}", event.getClass(), event.getInstanceId());
        if (Objects.equals(event.getInstanceId(), INSTANCE_ID)) {
            return;
        }

        applicationEventPublisher.publishEvent(event);
    }

}
