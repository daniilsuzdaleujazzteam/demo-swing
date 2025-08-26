package com.frizzer.swing.task;

import com.frizzer.swing.event.Event;
import com.frizzer.swing.event.PriorityListChangeEvent;

import static com.frizzer.swing.registry.RegistryNames.PRIORITY_LIST;

public abstract class PriorityTask<T, V> extends BaseTask<T, V> {

    @Override
    public Event createEvent(String id) {
        return new PriorityListChangeEvent(id, PRIORITY_LIST, getType(), firstId, lastId);
    }
}