package com.frizzer.swing.task.task;

import com.frizzer.swing.event.DataChangeType;
import com.frizzer.swing.repository.TodoRepository;
import com.frizzer.swing.task.TableTask;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.swing.table.DefaultTableModel;

@Slf4j
public class DeleteTodoTask extends TableTask<Void, Object[]> {
    private final TodoRepository todoRepository;
    private final Long taskId;
    private final int row;
    private final DefaultTableModel taskModel;

    public DeleteTodoTask(TodoRepository todoRepository, Long taskId, int row , DefaultTableModel taskModel) {
        this.todoRepository = todoRepository;
        this.taskId = taskId;
        this.taskModel = taskModel;
        this.row = row;
    }

    @Override
    protected Void doInBackground() {
        log.info("Started deleting task with id {}", taskId);
        todoRepository.deleteById(taskId);
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
