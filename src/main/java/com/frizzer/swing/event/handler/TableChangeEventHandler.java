package com.frizzer.swing.event.handler;

import com.frizzer.swing.event.Event;
import com.frizzer.swing.event.TableChangeEvent;
import com.frizzer.swing.registry.TableRegistry;
import com.frizzer.swing.registry.UUIDProvider;
import com.frizzer.swing.repository.TaskRepository;
import com.frizzer.swing.task.task.LoadByIdTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class TableChangeEventHandler implements EventHandler<TableChangeEvent> {

    private final UUIDProvider idProvider;
    private final TaskRepository taskRepository;
    private final TableRegistry registry;

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

        new LoadByIdTask(taskRepository, event.firstId(), event.lastId(), tasks -> {
            DefaultTableModel model = (DefaultTableModel) registry.get(event.tableId());
            switch (event.type()) {
                case INSERT -> tasks.forEach(task -> model.addRow(task.toRow()));
                case DELETE -> SwingUtilities.invokeLater(() -> {
                    var vector = model.getDataVector();
                    var toRemove = vector.stream()
                                         .filter(it -> Long.parseLong(it.getFirst().toString()) == event.firstId())
                                         .findFirst()
                                         .orElseThrow();
                    vector.removeElement(toRemove);
                    model.fireTableDataChanged();
                });
                case UPDATE -> {}//TODO
                case LOAD -> {}//TODO
            }
        }).execute();
    }

    @Override
    public Class<TableChangeEvent> getEventClass() {
        return TableChangeEvent.class;
    }
}
