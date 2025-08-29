package com.frizzer.swing.gui.event.listener;

import com.frizzer.swing.domain.Priority;
import com.frizzer.swing.gui.event.event.impl.EntityChangedEvent;
import com.frizzer.swing.gui.model.priority.PriorityModel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PriorityModelListener {

    private final PriorityModel priorityModel;

    @EventListener(EntityChangedEvent.class)
    private void onPriorityChanged(EntityChangedEvent<?> event) {
        if (event.entityClass() == Priority.class) {
            List<Priority> priorities = (List<Priority>) event.entity();
            switch (event.dataChangeType()) {
                case DELETE -> priorityModel.remove(priorities.getFirst());
                case UPSERT -> priorityModel.upsert(priorities.getFirst());
                case LOAD -> priorityModel.addAll(priorities);
            }
        }
    }

}
