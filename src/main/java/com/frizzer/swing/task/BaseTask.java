package com.frizzer.swing.task;

import com.frizzer.swing.event.DataChangeType;
import com.frizzer.swing.event.Event;
import lombok.Getter;

import javax.swing.*;

@Getter
public abstract class BaseTask<T,V> extends SwingWorker<T, V> {
    public abstract DataChangeType getType();
    public abstract Event createEvent(String id);
    protected long firstId;
    protected long lastId;
}
