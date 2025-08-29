package com.frizzer.swing.gui.event.handler.outer;

import com.frizzer.swing.gui.event.event.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AppEventHandler extends OuterEventHandler {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void handle(Event event) {
        applicationEventPublisher.publishEvent(event);
    }
}
