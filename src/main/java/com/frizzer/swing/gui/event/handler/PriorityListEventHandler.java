package com.frizzer.swing.gui.event.handler;

import com.frizzer.swing.config.UUIDProvider;
import com.frizzer.swing.domain.Priority;
import com.frizzer.swing.gui.model.priority.PriorityModel;
import com.frizzer.swing.gui.event.Event;
import com.frizzer.swing.gui.event.PriorityListChangeEvent;
import com.frizzer.swing.logic.repository.PriorityRepository;
import com.frizzer.swing.logic.tasks.priority.LoadPriorityByIdTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class PriorityListEventHandler implements EventHandler<PriorityListChangeEvent> {

    private final UUIDProvider idProvider;
    private final PriorityRepository priorityRepository;
    private final PriorityModel priorityModel;


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
        new LoadPriorityByIdTask(priorityRepository, event.affectedId(), tasks -> {
            switch (event.type()) {
                case INSERT -> priorityModel.getData().addAll(tasks);
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
                priorityModel.getData().set(index, tasks.getFirst());
            }
        });
    }

    private void handleDelete(PriorityListChangeEvent event) {
        SwingUtilities.invokeLater(() -> {
            int index = findPriorityIndex(event);
            if (index >= 0) {
                priorityModel.getData().remove(index);
            }
        });
    }

    private int findPriorityIndex(PriorityListChangeEvent event) {
        return priorityModel.getData()
                            .stream()
                            .filter(item -> Objects.equals(item.getId(), event.affectedId().getFirst()))
                            .map(priorityModel.getData()::indexOf)
                            .findFirst()
                            .orElse(-1);
    }
}
