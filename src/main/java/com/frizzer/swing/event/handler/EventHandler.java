package com.frizzer.swing.event.handler;

import com.frizzer.swing.event.Event;

public interface EventHandler<T extends Event> {
    Class<T> getEventClass();
    void handle(Event event);
}
