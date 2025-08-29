package com.frizzer.swing.gui.event.handler.inner;

import com.frizzer.swing.gui.event.event.Event;
import com.frizzer.swing.gui.event.handler.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public abstract class InnerEventHandler implements EventHandler {

    @EventListener(Event.class)
    @Override
    public void preHandle(Event event) {
        log.info("Listening event {} from instance {}", event.getClass(), event.getInstanceId());
        handle(event);
    }

}
