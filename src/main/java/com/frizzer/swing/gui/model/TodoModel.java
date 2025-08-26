package com.frizzer.swing.gui.model;

import com.frizzer.swing.domain.Todo;
import lombok.Getter;

import javax.swing.table.DefaultTableModel;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Getter
public class TodoModel implements Serializable {

    private final String[] columns = Arrays.stream(TodoColumns.values())
                                           .map(TodoColumns::getName)
                                           .toArray(String[]::new);
    private final DefaultTableModel model;

    public TodoModel(DefaultTableModel model) {
        model.setColumnIdentifiers(columns);
        this.model = model;
    }

    public void setTasks(List<Todo> todos) {
        model.setDataVector(todos.stream().map(Todo::toRow).toArray(Object[][]::new), columns);
    }
}