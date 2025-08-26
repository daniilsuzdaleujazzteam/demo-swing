package com.frizzer.swing.task.task;

import com.frizzer.swing.entity.Todo;
import com.frizzer.swing.event.DataChangeType;
import com.frizzer.swing.repository.TodoRepository;
import com.frizzer.swing.task.TableTask;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public class LoadTodoTask extends TableTask<List<Todo>, Object[]> {

    private final TodoRepository todoRepository;
    private final Consumer<List<Todo>> taskConsumer;

    @Override
    public DataChangeType getType() {
        return DataChangeType.LOAD;
    }

    @Override
    protected List<Todo> doInBackground() {
        log.info("Started loading tasks");
        return todoRepository.findAll();
    }

    @SneakyThrows
    @Override
    protected void done() {
        taskConsumer.accept(get());
        log.info("Finished loading tasks");
    }
}
