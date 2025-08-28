package com.frizzer.swing.gui.event.handler;

import com.frizzer.swing.gui.event.Event;

public interface EventHandler<T extends Event> {
    Class<T> getEventClass();
    void handle(Event event);
}
