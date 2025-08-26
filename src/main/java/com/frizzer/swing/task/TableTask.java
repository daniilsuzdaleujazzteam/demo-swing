package com.frizzer.swing.task;

import com.frizzer.swing.event.TableChangeEvent;
import com.frizzer.swing.event.Event;

import static com.frizzer.swing.registry.RegistryNames.TASK_MAP;

public abstract class TableTask<T, V> extends BaseTask<T, V> {

    @Override
    public Event createEvent(String id) {
        return new TableChangeEvent(id, TASK_MAP, getType(), getFirstId(), getLastId());
    }
}
