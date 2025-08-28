package com.frizzer.swing.gui.event.handler;

import com.frizzer.swing.config.UUIDProvider;
import com.frizzer.swing.gui.model.todo.TodoModel;
import com.frizzer.swing.gui.event.Event;
import com.frizzer.swing.gui.event.TableChangeEvent;
import com.frizzer.swing.logic.repository.TodoRepository;
import com.frizzer.swing.logic.tasks.todo.LoadTodoByIdTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class TableChangeEventHandler implements EventHandler<TableChangeEvent> {

    private final UUIDProvider idProvider;
    private final TodoRepository todoRepository;
    private final TodoModel todoModel;

    @Override
    public Class<TableChangeEvent> getEventClass() {
        return TableChangeEvent.class;
    }

    @Override
    public void handle(Event e) {
        TableChangeEvent event = (TableChangeEvent) e;
        log.info("TableChangeEvent received with type {} for table {} with id {}",
                event.type(),
                event.tableId(),
                event.instanceId());

        if (Objects.equals(event.instanceId(), idProvider.getId())) {
            return;
        }

        new LoadTodoByIdTask(todoRepository, event.affectedId(), tasks -> {
            switch (event.type()) {
                case INSERT -> todoModel.getData().addAll(tasks);
                case DELETE -> handleDelete(todoModel, event);
                case UPDATE -> {}//TODO
                case LOAD -> {}//TODO
            }
        }).execute();
    }

    private void handleDelete(TodoModel model, TableChangeEvent event) {
        SwingUtilities.invokeLater(() -> {
            var index = model.getData()
                             .stream()
                             .filter(it -> Objects.equals(it.getId(), event.affectedId().getFirst()))
                             .findFirst()
                             .orElseThrow();
            model.getData().remove(index);
        });
    }

}
