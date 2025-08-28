package com.frizzer.swing.gui.model.priority;

import com.frizzer.swing.domain.Priority;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Getter
public class PriorityModel extends DefaultListModel<Priority> {

    private final List<Priority> data = new ArrayList<>();

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public Priority getElementAt(int index) {
        return data.get(index);
    }

    public void upsert(Priority priority) {
        data.stream()
            .filter(it -> Objects.equals(it.getId(), priority.getId()))
            .findFirst()
            .ifPresentOrElse(existing -> {
                        int index = data.indexOf(existing);
                        data.set(index, priority);
                        fireContentsChanged(this, index, index);
                    }, () -> {
                        data.add(priority);
                        fireIntervalAdded(this, getSize() - 1, getSize() - 1);
                    }

            );
    }

    public void addAll(List<Priority> priorityList) {
        int before = data.size();
        data.addAll(priorityList);
        int after = data.size();
        fireContentsChanged(this, before, after);
    }

    public void remove(Priority priority) {
        data.remove(priority);
        fireContentsChanged(this, data.size() - 1, data.size() - 1);
    }
}
