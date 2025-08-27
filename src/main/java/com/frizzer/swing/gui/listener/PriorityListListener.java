package com.frizzer.swing.gui.listener;

import com.frizzer.swing.gui.view.form.todo.TodoForm;
import lombok.RequiredArgsConstructor;

import javax.swing.event.ListDataListener;

@RequiredArgsConstructor
public class PriorityListListener implements ListDataListener {

    private final TodoForm todoForm;

    @Override
    public void intervalAdded(javax.swing.event.ListDataEvent e) {
        todoForm.refreshPriorityPanel();
    }

    @Override
    public void intervalRemoved(javax.swing.event.ListDataEvent e) {
        todoForm.refreshPriorityPanel();
    }

    @Override
    public void contentsChanged(javax.swing.event.ListDataEvent e) {
        todoForm.refreshPriorityPanel();
    }
}


