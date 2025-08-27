package com.frizzer.swing.logic.task.todo;

import com.frizzer.swing.domain.Todo;
import com.frizzer.swing.logic.event.DataChangeType;
import com.frizzer.swing.logic.repository.TodoRepository;
import com.frizzer.swing.logic.task.TableTask;
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
        idList.add(saved.getId());
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
