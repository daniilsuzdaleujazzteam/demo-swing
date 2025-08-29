package com.frizzer.swing.gui.model.todo;

import com.frizzer.swing.domain.Priority;
import com.frizzer.swing.domain.Todo;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Component
public class TodoModel extends AbstractTableModel {

    private final List<Todo> data = new ArrayList<>();
    private boolean isInternalUpdate;

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
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Todo todo = data.get(rowIndex);
        switch (TodoColumns.values()[columnIndex]) {
            case TITLE -> todo.setTitle((String) aValue);
            case DESCRIPTION -> todo.setDescription((String) aValue);
            case DATE -> todo.setDate((LocalDate) aValue);
            case PRIORITY -> todo.setPriority((Priority) aValue);
        }

        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return TodoColumns.values()[column].name();
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return TodoColumns.values()[column].getType();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public Todo getTodoAt(int row) {
        return data.get(row);
    }

    public void addAll(List<Todo> todoList) {
        SwingUtilities.invokeLater(() -> {
            int before = data.size();
            data.addAll(todoList);
            int after = data.size() - 1;
            fireTableRowsInserted(before, after);
        });
    }

    public void upsert(Todo todo) {
        SwingUtilities.invokeLater(() -> {
            isInternalUpdate = true;
            try {
                data.stream()
                    .filter(it -> Objects.equals(it.getId(), todo.getId()))
                    .findFirst()
                    .ifPresentOrElse(existing -> {
                        int index = data.indexOf(existing);
                        data.set(index, todo);
                        fireTableRowsUpdated(index, index);
                    }, () -> {
                        data.add(todo);
                        fireTableRowsInserted(data.size() - 1, data.size() - 1);
                    });
            } finally {
                isInternalUpdate = false;
            }
        });
    }

    public void upsertPriority(Priority priority) {
        SwingUtilities.invokeLater(() -> {
            data.stream()
                .filter(todo -> Objects.equals(todo.getPriority() != null ? todo.getPriority().getId() : -1,
                        priority.getId()))
                .forEach(todo -> {
                    todo.setPriority(priority);
                    int index = data.indexOf(todo);
                    fireTableRowsUpdated(index, index);
                });
        });
    }

    public void remove(Todo todo) {
        SwingUtilities.invokeLater(() -> {
            int index = data.indexOf(todo);
            data.remove(index);
            fireTableRowsDeleted(index, index);
        });
    }

    public void removeByPriority(Priority priority) {
        for (int i = data.size() - 1; i >= 0; i--) {
            if (Objects.equals(data.get(i).getPriority().getId(), priority.getId())) {
                data.set(i, data.get(i).withPriority(null));
                fireTableRowsUpdated(i, i);
            }
        }
    }

    public void swapRows(int index1, int index2) {
        if (index1 < 0 || index2 < 0 || index1 >= data.size() || index2 >= data.size()) {
            return;
        }
        SwingUtilities.invokeLater(() -> {
            Collections.swap(data, index1, index2);
            fireTableRowsUpdated(index1, index1);
            fireTableRowsUpdated(index2, index2);
        });
    }
}


