package com.frizzer.swing.logic.task;

import com.frizzer.swing.logic.event.TableChangeEvent;
import com.frizzer.swing.logic.event.Event;

import static com.frizzer.swing.config.registry.RegistryNames.TASK_MAP;

public abstract class TableTask<T, V> extends BaseTask<T, V> {

    @Override
    public Event createEvent(String id) {
        return new TableChangeEvent(id, TASK_MAP, getType(), getFirstId(), getLastId());
    }
}
