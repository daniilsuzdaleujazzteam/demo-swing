package com.frizzer.swing.logic.tasks.todo;

import com.frizzer.swing.domain.Todo;
import com.frizzer.swing.gui.event.DataChangeType;
import com.frizzer.swing.logic.repository.TodoRepository;
import com.frizzer.swing.logic.tasks.BaseTask;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class LoadTodoByIdTask extends BaseTask<List<Todo>, Object[]> {
    private final TodoRepository todoRepository;
    private final Consumer<List<Todo>> taskConsumer;


    public LoadTodoByIdTask(TodoRepository todoRepository, List<Long> ids, Consumer<List<Todo>> taskConsumer) {
        this.todoRepository = todoRepository;
        this.taskConsumer = taskConsumer;
        idList.addAll(ids);
    }

    @Override
    public DataChangeType getType() {
        return DataChangeType.LOAD;
    }

    @Override
    protected List<Todo> doInBackground() {
        return todoRepository.findAllByIdIn(idList);
    }

    @SneakyThrows
    @Override
    protected void done() {
        taskConsumer.accept(get());
    }
}
