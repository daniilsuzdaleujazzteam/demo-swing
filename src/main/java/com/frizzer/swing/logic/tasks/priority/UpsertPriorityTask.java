package com.frizzer.swing.logic.tasks.priority;

import com.frizzer.swing.domain.Priority;
import com.frizzer.swing.gui.event.event.DataChangeType;
import com.frizzer.swing.gui.event.event.impl.EntityChangedEvent;
import com.frizzer.swing.logic.repository.PriorityRepository;
import com.frizzer.swing.logic.tasks.BaseTask;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static com.frizzer.swing.config.IdGenerator.INSTANCE_ID;

@Slf4j
public class UpsertPriorityTask extends BaseTask<Priority, Object[]> {

    private final PriorityRepository repository;
    private final Priority priority;

    public UpsertPriorityTask(PriorityRepository repository,
                              Priority priority,
                              ApplicationEventPublisher applicationEventPublisher) {
        super(applicationEventPublisher);
        this.repository = repository;
        this.priority = priority;
    }

    @Override
    public DataChangeType getChangeType() {
        return DataChangeType.UPSERT;
    }

    @Override
    public Class<Priority> getTaskType() {
        return Priority.class;
    }

    @Override
    protected Priority doInBackground() {
        log.info("Started modifying task with name {}", priority.getName());
        Priority saved = repository.save(priority);
        idList.add(saved.getId());
        return saved;
    }

    @Override
    @SneakyThrows
    public void done() {
        eventPublisher.publishEvent(new EntityChangedEvent<>(List.of(get()),
                getTaskType(),
                getChangeType(),
                INSTANCE_ID));
        log.info("Finished modifying task with name {}", priority.getName());
    }

}
