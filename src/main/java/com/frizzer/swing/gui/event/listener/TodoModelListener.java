package com.frizzer.swing.gui.event.listener;

import com.frizzer.swing.domain.Priority;
import com.frizzer.swing.domain.Todo;
import com.frizzer.swing.gui.event.event.impl.EntityChangedEvent;
import com.frizzer.swing.gui.event.event.impl.PlacementSwapEvent;
import com.frizzer.swing.gui.model.todo.TodoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TodoModelListener {

    private final TodoModel todoModel;

    @EventListener(EntityChangedEvent.class)
    private void onTodoChanged(EntityChangedEvent<?> event) {
        if (event.entityClass() == Priority.class) {
            Priority priority = ((List<Priority>) event.entity()).getFirst();
            switch (event.dataChangeType()) {
                case UPSERT -> todoModel.upsertPriority(priority);
                case DELETE -> todoModel.removeByPriority(priority);
                default -> {}
            }
        }
        if (event.entityClass() == Todo.class) {
            List<Todo> todos = (List<Todo>) event.entity();
            switch (event.dataChangeType()) {
                case LOAD -> todoModel.addAll(todos);
                case UPSERT -> todoModel.upsert(todos.getFirst());
                case DELETE -> todoModel.remove(todos.getFirst());
            }
        }
    }

    @EventListener(PlacementSwapEvent.class)
    private void onPlacementSwap(PlacementSwapEvent event) {
        todoModel.swapRows(event.newPlace(), event.oldPlace());
    }

}
