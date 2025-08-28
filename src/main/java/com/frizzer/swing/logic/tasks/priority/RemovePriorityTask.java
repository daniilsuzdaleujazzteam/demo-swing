package com.frizzer.swing.logic.tasks.priority;

import com.frizzer.swing.domain.Priority;
import com.frizzer.swing.gui.model.priority.PriorityModel;
import com.frizzer.swing.gui.event.DataChangeType;
import com.frizzer.swing.logic.repository.PriorityRepository;
import com.frizzer.swing.logic.tasks.BaseTask;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public class RemovePriorityTask extends BaseTask<Priority, Object[]> {

    private final PriorityRepository repository;
    private final Priority priority;
    private final Consumer<Priority> consumer;

    @Override
    protected Priority doInBackground() {
        log.info("Started deleting priority with name {}", priority.getName());
        repository.removePriorityByName(priority.getName());
        idList.add(priority.getId());
        return priority;
    }

    @Override
    @SneakyThrows
    public void done() {
        consumer.accept(priority);
        log.info("Finished delete task with name {}", priority.getName());
    }

    @Override
    public DataChangeType getType() {
        return DataChangeType.DELETE;
    }
}
