package com.frizzer.swing.gui.model.todo;

import com.frizzer.swing.domain.Todo;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class TodoModel extends AbstractTableModel {

    private final List<Todo> data = new ArrayList<>();

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return TodoColumns.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Todo todo = data.get(rowIndex);
        return switch (TodoColumns.values()[columnIndex]) {
            case TITLE -> todo.getTitle();
            case DESCRIPTION -> todo.getDescription();
            case DATE -> todo.getDate();
            case PRIORITY -> todo.getPriority();
        };
    }

    @Override
    public String getColumnName(int column) {
        return TodoColumns.values()[column].name();
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return TodoColumns.values()[column].getType();
    }

    public Todo getTodoAt(int row) {
        return data.get(row);
    }

    public void addAll(List<Todo> todoList) {
        int before = data.size();
        data.addAll(todoList);
        int after = data.size();
        fireTableChanged(new TableModelEvent(this, before, after - 1));
    }
}


