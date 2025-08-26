package com.frizzer.swing.task.task;

import com.frizzer.swing.entity.Task;
import com.frizzer.swing.event.DataChangeType;
import com.frizzer.swing.repository.TaskRepository;
import com.frizzer.swing.task.TableTask;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public class LoadTask extends TableTask<List<Task>, Object[]> {

    private final TaskRepository taskRepository;
    private final Consumer<List<Task>> taskConsumer;

    @Override
    public DataChangeType getType() {
        return DataChangeType.LOAD;
    }

    @Override
    protected List<Task> doInBackground() {
        log.info("Started loading tasks");
        return taskRepository.findAll();
    }

    @SneakyThrows
    @Override
    protected void done() {
        taskConsumer.accept(get());
        log.info("Finished loading tasks");
    }
}
