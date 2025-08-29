package com.frizzer.swing.gui.event.event.impl;

import com.frizzer.swing.gui.event.event.DataChangeType;
import com.frizzer.swing.gui.event.event.Event;

import java.util.List;

public record EntityChangedEvent<T>(
        List<T> entity,
        Class<T> entityClass,
        DataChangeType dataChangeType,
        String instanceId
) implements Event {
    @Override
    public String getInstanceId() {
        return instanceId;
    }
}
