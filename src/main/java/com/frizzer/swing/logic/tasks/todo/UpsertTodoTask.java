package com.frizzer.swing.logic.tasks.todo;

import com.frizzer.swing.domain.Todo;
import com.frizzer.swing.gui.event.event.DataChangeType;
import com.frizzer.swing.gui.event.event.impl.EntityChangedEvent;
import com.frizzer.swing.logic.repository.TodoRepository;
import com.frizzer.swing.logic.tasks.BaseTask;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static com.frizzer.swing.config.IdGenerator.INSTANCE_ID;

@Slf4j
@Getter
public class UpsertTodoTask extends BaseTask<Todo, Object[]> {

    private final TodoRepository todoRepository;
    private final Todo todo;

    public UpsertTodoTask(TodoRepository todoRepository,
                          Todo todo,
                          ApplicationEventPublisher applicationEventPublisher) {
        super(applicationEventPublisher);
        this.todoRepository = todoRepository;
        this.todo = todo;
    }

    @Override
    public DataChangeType getChangeType() {
        return DataChangeType.UPSERT;
    }

    @Override
    public Class<Todo> getTaskType() {
        return Todo.class;
    }

    @Override
    protected Todo doInBackground() {
        log.info("Started saving task with description {}", todo.getDescription());
        Todo saved = todoRepository.save(todo);
        idList.add(saved.getId());
        return saved;
    }

    @SneakyThrows
    @Override
    protected void done() {
        Todo saved = get();
        eventPublisher.publishEvent(new EntityChangedEvent<>(List.of(saved), getTaskType(), getChangeType(), INSTANCE_ID));
        log.info("Finished saving task with description {}", todo.getDescription());
    }
}
