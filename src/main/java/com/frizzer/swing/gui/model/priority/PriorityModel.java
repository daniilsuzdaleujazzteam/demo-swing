package com.frizzer.swing.gui.model.priority;

import com.frizzer.swing.domain.Priority;
import com.frizzer.swing.gui.event.event.impl.EntityChangedEvent;
import lombok.Getter;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Getter
public class PriorityModel extends AbstractListModel<Priority> {

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

    @EventListener(EntityChangedEvent.class)
    private void onPriorityChanged(EntityChangedEvent<?> event) {
        if (event.entityClass() == Priority.class) {
            List<Priority> priorities = (List<Priority>) event.entity();
            switch (event.dataChangeType()) {
                case DELETE -> remove(priorities.getFirst());
                case UPSERT -> upsert(priorities.getFirst());
                case LOAD -> addAll(priorities);
            }
        }
    }
}
