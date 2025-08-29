package com.frizzer.swing.gui.event.event.impl;

import com.frizzer.swing.gui.event.event.Event;

public record PlacementSwapEvent(int oldPlace, int newPlace, String instanceId) implements Event {
    @Override
    public String getInstanceId() {
        return instanceId;
    }
}
