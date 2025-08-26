package com.frizzer.swing.model;

import com.frizzer.swing.entity.Task;
import lombok.Getter;

import javax.swing.table.DefaultTableModel;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Getter
public class TaskModel implements Serializable {

    public static final int ID_COLUMN = 0;

    private final String[] columns = Arrays.stream(TaskTableColumns.values())
                                           .map(TaskTableColumns::getName)
                                           .toArray(String[]::new);
    private final DefaultTableModel model;

    public TaskModel(DefaultTableModel model) {
        model.setColumnIdentifiers(columns);
        this.model = model;
    }

    public void setTasks(List<Task> tasks) {
        model.setDataVector(tasks.stream().map(Task::toRow).toArray(Object[][]::new), columns);
    }

    public Long getTaskIdAt(int row) {
        return Long.parseLong(model.getValueAt(row, ID_COLUMN).toString());
    }
}