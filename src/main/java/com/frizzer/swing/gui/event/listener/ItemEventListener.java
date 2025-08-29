package com.frizzer.swing.gui.event.listener;

import com.frizzer.swing.gui.event.event.Event;
import com.frizzer.swing.gui.event.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ItemEventListener {

    private final List<EventHandler> listeners;

    @JmsListener(destination = "eventTopic")
    public void listen(Event event) {
        listeners.forEach(handler -> handler.handle(event));
    }
}
