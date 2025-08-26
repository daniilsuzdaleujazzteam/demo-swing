package com.frizzer.swing.task.task;

import com.frizzer.swing.event.DataChangeType;
import com.frizzer.swing.repository.TaskRepository;
import com.frizzer.swing.task.TableTask;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.swing.table.DefaultTableModel;

@Slf4j
public class DeleteTask extends TableTask<Void, Object[]> {
    private final TaskRepository taskRepository;
    private final Long taskId;
    private final int row;
    private final DefaultTableModel taskModel;

    public DeleteTask(TaskRepository taskRepository, Long taskId, int row ,DefaultTableModel taskModel) {
        this.taskRepository = taskRepository;
        this.taskId = taskId;
        this.taskModel = taskModel;
        this.row = row;
    }

    @Override
    protected Void doInBackground() {
        log.info("Started deleting task with id {}", taskId);
        taskRepository.deleteById(taskId);
        firstId = taskId;
        return null;
    }

    @SneakyThrows
    @Override
    protected void done() {
        get();
        taskModel.removeRow(row);
    }

    @Override
    public DataChangeType getType() {
        return DataChangeType.DELETE;
    }
}
