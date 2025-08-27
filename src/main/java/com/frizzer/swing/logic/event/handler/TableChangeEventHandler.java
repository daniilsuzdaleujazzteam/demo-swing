package com.frizzer.swing.logic.event.handler;

import com.frizzer.swing.config.registry.UUIDProvider;
import com.frizzer.swing.gui.model.TodoModel;
import com.frizzer.swing.logic.event.Event;
import com.frizzer.swing.logic.event.TableChangeEvent;
import com.frizzer.swing.logic.repository.TodoRepository;
import com.frizzer.swing.logic.task.todo.LoadTodoByIdTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class TableChangeEventHandler implements EventHandler<TableChangeEvent> {

    private final UUIDProvider idProvider;
    private final TodoRepository todoRepository;
    private final List<TodoModel> todoModels;

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
            DefaultTableModel model = todoModels.stream()
                                                .filter(it -> it.getName().equals(event.tableId()))
                                                .findFirst()
                                                .orElseThrow(() -> new IllegalStateException("Unknow table id " + event.tableId()))
                                                .getModel();
            switch (event.type()) {
                case INSERT -> tasks.forEach(task -> model.addRow(task.toRow()));
                case DELETE -> handleDelete(model, event);
                case UPDATE -> {}//TODO
                case LOAD -> {}//TODO
            }
        }).execute();
    }

    private void handleDelete(DefaultTableModel model, TableChangeEvent event) {
        SwingUtilities.invokeLater(() -> {
            var vector = model.getDataVector();
            var index = vector.stream()
                              .filter(it -> Long.parseLong(it.getFirst().toString()) == event.affectedId().getFirst())
                              .findFirst()
                              .orElseThrow();
            vector.removeElement(index);
        });
    }

}
