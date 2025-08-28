package com.frizzer.swing.logic.tasks;

import com.frizzer.swing.gui.event.DataChangeType;
import lombok.Getter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class BaseTask<T,V> extends SwingWorker<T, V> {
    public abstract DataChangeType getType();
    protected final List<Long> idList = new ArrayList<>();
}
