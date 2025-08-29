package com.frizzer.swing.logic.tasks.todo;

import com.frizzer.swing.domain.Todo;
import com.frizzer.swing.gui.event.event.DataChangeType;
import com.frizzer.swing.gui.event.event.impl.EntityChangedEvent;
import com.frizzer.swing.logic.repository.TodoRepository;
import com.frizzer.swing.logic.tasks.BaseTask;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static com.frizzer.swing.config.IdGenerator.INSTANCE_ID;

@Slf4j
public class LoadTodoTask extends BaseTask<List<Todo>, Object[]> {

    private final TodoRepository todoRepository;

    public LoadTodoTask(TodoRepository todoRepository, ApplicationEventPublisher applicationEventPublisher) {
        super(applicationEventPublisher);
        this.todoRepository = todoRepository;
    }

    @Override
    public DataChangeType getChangeType() {
        return DataChangeType.LOAD;
    }

    @Override
    public Class<Todo> getTaskType() {
        return Todo.class;
    }

    @Override
    protected List<Todo> doInBackground() {
        log.info("Started loading tasks");
        return todoRepository.findAll();
    }

    @SneakyThrows
    @Override
    protected void done() {
        eventPublisher.publishEvent(new EntityChangedEvent<>(get(), getTaskType(), getChangeType(), INSTANCE_ID));
        log.info("Finished loading tasks");
    }
}
