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
public class DeleteTodoTask extends BaseTask<Todo, Object[]> {
    private final TodoRepository todoRepository;
    private final Todo todo;

    public DeleteTodoTask(TodoRepository todoRepository,
                          Todo todo,
                          ApplicationEventPublisher applicationEventPublisher) {
        super(applicationEventPublisher);
        this.todoRepository = todoRepository;
        this.todo = todo;
    }

    @Override
    public DataChangeType getChangeType() {
        return DataChangeType.DELETE;
    }

    @Override
    public Class<Todo> getTaskType() {
        return Todo.class;
    }

    @Override
    protected Todo doInBackground() {
        log.info("Started deleting task with id {}", todo.getId());
        todoRepository.deleteById(todo.getId());
        idList.add(todo.getId());
        return todo;
    }

    @SneakyThrows
    @Override
    protected void done() {
        eventPublisher.publishEvent(new EntityChangedEvent<>(List.of(get()),
                getTaskType(),
                getChangeType(),
                INSTANCE_ID));
        log.info("Finished deleting todo with id {}", todo.getId());
    }
}
