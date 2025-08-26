package com.frizzer.swing.task.task;

import com.frizzer.swing.entity.Task;
import com.frizzer.swing.event.DataChangeType;
import com.frizzer.swing.repository.TaskRepository;
import com.frizzer.swing.task.TableTask;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.swing.table.DefaultTableModel;

@Slf4j
@Getter
@RequiredArgsConstructor
public class CreateTask extends TableTask<Task, Object[]> {

    private final TaskRepository taskRepository;
    private final Task task;
    private final DefaultTableModel tableModel;

    @Override
    public DataChangeType getType() {
        return DataChangeType.INSERT;
    }

    @Override
    protected Task doInBackground() {
        log.info("Started saving task with description {}", task.getDescription());
        Task saved = taskRepository.save(task);
        firstId = saved.getId();
        lastId = saved.getId();
        return saved;
    }

    @SneakyThrows
    @Override
    protected void done() {
        Task saved = get();
        tableModel.addRow(saved.toRow());
        log.info("Finished saving task with description {}", task.getDescription());
    }
}
