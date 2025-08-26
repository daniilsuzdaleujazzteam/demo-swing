package com.frizzer.swing.task.task;

import com.frizzer.swing.entity.Task;
import com.frizzer.swing.event.DataChangeType;
import com.frizzer.swing.repository.TaskRepository;
import com.frizzer.swing.task.TableTask;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class LoadByIdTask extends TableTask<List<Task>, Object[]> {
    private final TaskRepository taskRepository;
    private final Consumer<List<Task>> taskConsumer;


    public LoadByIdTask(TaskRepository taskRepository, long firstId, long lastId, Consumer<List<Task>> taskConsumer) {
        this.taskRepository = taskRepository;
        this.firstId = firstId;
        this.lastId = lastId;
        this.taskConsumer = taskConsumer;
    }

    @Override
    public DataChangeType getType() {
        return DataChangeType.LOAD;
    }

    @Override
    protected List<Task> doInBackground() {
        return taskRepository.findAllByIdBetween(firstId, lastId);
    }

    @SneakyThrows
    @Override
    protected void done(){
        taskConsumer.accept(get());
    }
}
