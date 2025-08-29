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
public class LoadPriorityTask extends BaseTask<List<Priority>, Object[]> {

    private final PriorityRepository priorityRepository;

    public LoadPriorityTask(PriorityRepository priorityRepository,
                            ApplicationEventPublisher applicationEventPublisher) {
        super(applicationEventPublisher);
        this.priorityRepository = priorityRepository;
    }

    @Override
    public DataChangeType getChangeType() {
        return DataChangeType.LOAD;
    }

    @Override
    public Class<Priority> getTaskType() {
        return Priority.class;
    }

    @Override
    protected List<Priority> doInBackground() {
        log.info("Started loading priorities");
        return priorityRepository.findAll();
    }

    @Override
    @SneakyThrows
    public void done() {
        eventPublisher.publishEvent(new EntityChangedEvent<>(get(), getTaskType(), getChangeType(), INSTANCE_ID));
        log.info("Finished loading priorities");
    }
}
