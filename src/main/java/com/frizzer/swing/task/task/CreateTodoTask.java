package com.frizzer.swing.task.task;

import com.frizzer.swing.entity.Todo;
import com.frizzer.swing.event.DataChangeType;
import com.frizzer.swing.repository.TodoRepository;
import com.frizzer.swing.task.TableTask;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.swing.table.DefaultTableModel;

@Slf4j
@Getter
@RequiredArgsConstructor
public class CreateTodoTask extends TableTask<Todo, Object[]> {

    private final TodoRepository todoRepository;
    private final Todo todo;
    private final DefaultTableModel tableModel;

    @Override
    public DataChangeType getType() {
        return DataChangeType.INSERT;
    }

    @Override
    protected Todo doInBackground() {
        log.info("Started saving task with description {}", todo.getDescription());
        Todo saved = todoRepository.save(todo);
        firstId = saved.getId();
        lastId = saved.getId();
        return saved;
    }

    @SneakyThrows
    @Override
    protected void done() {
        Todo saved = get();
        tableModel.addRow(saved.toRow());
        log.info("Finished saving task with description {}", todo.getDescription());
    }
}
