package com.frizzer.swing.logic.tasks;

import com.frizzer.swing.gui.event.event.DataChangeType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public abstract class BaseTask<T, V> extends SwingWorker<T, V> {
    public abstract DataChangeType getChangeType();

    public abstract Class<?> getTaskType();

    protected final List<Long> idList = new ArrayList<>();
    protected final ApplicationEventPublisher eventPublisher;
}
