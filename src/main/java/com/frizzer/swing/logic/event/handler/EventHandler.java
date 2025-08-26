package com.frizzer.swing.logic.event.handler;

import com.frizzer.swing.logic.event.Event;

public interface EventHandler<T extends Event> {
    Class<T> getEventClass();
    void handle(Event event);
}
