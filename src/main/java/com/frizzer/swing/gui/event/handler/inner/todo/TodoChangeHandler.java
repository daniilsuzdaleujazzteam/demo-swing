package com.frizzer.swing.gui.event.handler.inner.todo;

import com.frizzer.swing.domain.Todo;
import com.frizzer.swing.gui.event.event.Event;
import com.frizzer.swing.gui.event.event.impl.EntityChangedEvent;
import com.frizzer.swing.gui.event.handler.inner.InnerEventHandler;
import com.frizzer.swing.gui.model.todo.TodoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TodoChangeHandler extends InnerEventHandler {

    private final TodoModel todoModel;

    @Override
    public void handle(Event event) {
        if (event instanceof EntityChangedEvent entityChangedEvent && entityChangedEvent.entityClass() == Todo.class) {
            updateTableModel(entityChangedEvent);
        }
    }

    private void updateTableModel(EntityChangedEvent<Todo> entityChangedEvent) {
        List<Todo> todos = entityChangedEvent.entity();
        switch (entityChangedEvent.dataChangeType()) {
            case LOAD -> todoModel.addAll(todos);
            case UPSERT -> todoModel.upsert(todos.getFirst());
            case DELETE -> todoModel.remove(todos.getFirst());
        }
    }
}
