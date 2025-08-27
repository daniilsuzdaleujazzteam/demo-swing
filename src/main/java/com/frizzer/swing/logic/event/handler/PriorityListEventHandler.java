package com.frizzer.swing.logic.event.handler;

import com.frizzer.swing.config.registry.UUIDProvider;
import com.frizzer.swing.domain.Priority;
import com.frizzer.swing.logic.event.Event;
import com.frizzer.swing.logic.event.PriorityListChangeEvent;
import com.frizzer.swing.logic.repository.PriorityRepository;
import com.frizzer.swing.logic.task.priority.LoadPriorityByIdTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class PriorityListEventHandler implements EventHandler<PriorityListChangeEvent> {

    private final UUIDProvider idProvider;
    private final PriorityRepository repository;
    private final DefaultListModel<Priority> priorityModel;


    @Override
    public Class<PriorityListChangeEvent> getEventClass() {
        return PriorityListChangeEvent.class;
    }

    @Override
    public void handle(Event e) {
        PriorityListChangeEvent event = (PriorityListChangeEvent) e;
        log.info("PriorityListChangeEvent received with type {} for list {} with id {}",
                event.type(),
                event.listId(),
                event.instanceId());

        if (Objects.equals(event.instanceId(), idProvider.getId())) {
            return;
        }
        new LoadPriorityByIdTask(repository, event.affectedId(), tasks -> {
            switch (event.type()) {
                case INSERT -> priorityModel.addAll(tasks);
                case UPDATE -> handleUpdate(event, tasks);
                case DELETE -> handleDelete(event);
                default -> throw new IllegalStateException("Unexpected value: " + event.type());
            }
        }).execute();

    }

    private void handleUpdate(PriorityListChangeEvent event, List<Priority> tasks) {
        SwingUtilities.invokeLater(() -> {
            int index = findPriorityIndex(event);
            if (index >= 0) {
                priorityModel.set(index, tasks.getFirst());
            }
        });
    }

    private void handleDelete(PriorityListChangeEvent event) {
        SwingUtilities.invokeLater(() -> {
            int index = findPriorityIndex(event);
            if (index >= 0) {
                priorityModel.removeElementAt(index);
            }
        });
    }

    private int findPriorityIndex(PriorityListChangeEvent event) {
        return Collections.list(priorityModel.elements())
                          .stream()
                          .filter(item -> Objects.equals(item.getId(), event.affectedId().getFirst()))
                          .map(priorityModel::indexOf)
                          .findFirst()
                          .orElse(-1);
    }
}
