package com.frizzer.swing.event.listener;

import com.frizzer.swing.event.Event;
import com.frizzer.swing.event.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ItemEventListener {

    private final List<EventHandler<? extends Event>> listeners;

    @JmsListener(destination = "eventTopic")
    public void listen(Event event) {
        var handler = listeners.stream()
                               .filter(e -> e.getEventClass().isAssignableFrom(event.getClass()))
                               .findFirst()
                               .orElseThrow(() -> new IllegalStateException("Handler for class " + event.getClass() + " not found"));
        handler.handle(event);
    }
}
