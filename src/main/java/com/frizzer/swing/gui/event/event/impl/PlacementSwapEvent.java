package com.frizzer.swing.gui.event.event.impl;

import com.frizzer.swing.gui.event.event.Event;

import static com.frizzer.swing.config.IdGenerator.INSTANCE_ID;

public record PlacementSwapEvent(int oldPlace, int newPlace) implements Event {
    @Override
    public String getInstanceId() {
        return INSTANCE_ID;
    }
}
