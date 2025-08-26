package com.frizzer.swing.listener;

import com.frizzer.swing.view.form.PriorityForm;
import lombok.RequiredArgsConstructor;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

@RequiredArgsConstructor
public class ChangePriorityListener implements ListDataListener {

    private final PriorityForm form;

    @Override
    public void intervalAdded(ListDataEvent e) {
        form.refreshPrioritiesList();
    }

    @Override
    public void intervalRemoved(ListDataEvent e) {
        form.refreshPrioritiesList();
    }

    @Override
    public void contentsChanged(ListDataEvent e) {
        form.refreshPrioritiesList();
    }
}
