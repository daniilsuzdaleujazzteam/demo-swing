package com.frizzer.swing.gui.event.handler.inner.priority;

import com.frizzer.swing.domain.Priority;
import com.frizzer.swing.gui.event.event.Event;
import com.frizzer.swing.gui.event.event.impl.EntityChangedEvent;
import com.frizzer.swing.gui.event.handler.inner.InnerEventHandler;
import com.frizzer.swing.gui.model.priority.PriorityModel;
import com.frizzer.swing.gui.model.todo.TodoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PriorityChangeHandler extends InnerEventHandler {

    private final PriorityModel priorityModel;
    private final TodoModel todoModel;

    @Override
    public void handle(Event event) {
        if (event instanceof EntityChangedEvent entityChangedEvent && entityChangedEvent.entityClass() == Priority.class) {
            updatePriorityModel(entityChangedEvent);
            updateTableModel(entityChangedEvent);
        }
    }

    private void updatePriorityModel(EntityChangedEvent<Priority> entityChangedEvent) {
        List<Priority> priorities = entityChangedEvent.entity();
        switch (entityChangedEvent.dataChangeType()) {
            case DELETE -> priorityModel.remove(priorities.getFirst());
            case UPSERT -> priorityModel.upsert(priorities.getFirst());
            case LOAD -> priorityModel.addAll(priorities);
        }
    }

    private void updateTableModel(EntityChangedEvent<Priority> entityChangedEvent) {
        Priority priority = entityChangedEvent.entity().getFirst();
        switch (entityChangedEvent.dataChangeType()) {
            case UPSERT -> todoModel.upsertPriority(priority);
            case DELETE -> todoModel.removeByPriority(priority);
            default -> {}
        }
    }

}
