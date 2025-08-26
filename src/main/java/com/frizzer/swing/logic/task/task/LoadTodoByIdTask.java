package com.frizzer.swing.logic.task.task;

import com.frizzer.swing.domain.Todo;
import com.frizzer.swing.logic.event.DataChangeType;
import com.frizzer.swing.logic.repository.TodoRepository;
import com.frizzer.swing.logic.task.TableTask;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class LoadTodoByIdTask extends TableTask<List<Todo>, Object[]> {
    private final TodoRepository todoRepository;
    private final Consumer<List<Todo>> taskConsumer;


    public LoadTodoByIdTask(TodoRepository todoRepository, long firstId, long lastId, Consumer<List<Todo>> taskConsumer) {
        this.todoRepository = todoRepository;
        this.firstId = firstId;
        this.lastId = lastId;
        this.taskConsumer = taskConsumer;
    }

    @Override
    public DataChangeType getType() {
        return DataChangeType.LOAD;
    }

    @Override
    protected List<Todo> doInBackground() {
        return todoRepository.findAllByIdBetween(firstId, lastId);
    }

    @SneakyThrows
    @Override
    protected void done(){
        taskConsumer.accept(get());
    }
}
