package com.frizzer.swing.gui.model.priority;

import com.frizzer.swing.domain.Priority;

import javax.swing.*;
import java.util.List;

public class PriorityComboBoxModel extends AbstractListModel<Priority> implements ComboBoxModel<Priority> {

    private final List<Priority> data;
    private Object selected;

    public PriorityComboBoxModel(PriorityModel model) {
        this.data = model.getData();
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selected = anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selected;
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public Priority getElementAt(int index) {
        return data.get(index);
    }
}
