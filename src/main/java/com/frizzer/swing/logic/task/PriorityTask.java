package com.frizzer.swing.logic.task;

import com.frizzer.swing.logic.event.Event;
import com.frizzer.swing.logic.event.PriorityListChangeEvent;

import static com.frizzer.swing.config.registry.RegistryNames.PRIORITY_LIST;

public abstract class PriorityTask<T, V> extends BaseTask<T, V> {

    @Override
    public Event createEvent(String id) {
        return new PriorityListChangeEvent(id, PRIORITY_LIST, getType(), firstId, lastId);
    }
}