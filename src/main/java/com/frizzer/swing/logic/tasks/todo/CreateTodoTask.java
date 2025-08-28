package com.frizzer.swing.logic.tasks.todo;

import com.frizzer.swing.domain.Todo;
import com.frizzer.swing.gui.event.DataChangeType;
import com.frizzer.swing.gui.model.todo.TodoModel;
import com.frizzer.swing.logic.repository.TodoRepository;
import com.frizzer.swing.logic.tasks.BaseTask;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public class CreateTodoTask extends BaseTask<Todo, Object[]> {

    private final TodoRepository todoRepository;
    private final Todo todo;
    private final TodoModel todoModel;

    @Override
    public DataChangeType getType() {
        return DataChangeType.INSERT;
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
        log.info("Finished saving task with description {}", todo.getDescription());
    }
}
