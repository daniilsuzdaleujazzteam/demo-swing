package com.frizzer.swing.listener;

import com.frizzer.swing.event.sender.EventSender;
import com.frizzer.swing.model.TaskModel;
import com.frizzer.swing.repository.TaskRepository;
import com.frizzer.swing.task.task.DeleteTask;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class DeleteItemListener implements MouseListener {

    private final TaskRepository taskRepository;
    private final TaskModel model;
    private final EventSender eventSender;

    public DeleteItemListener(TaskRepository taskRepository,
                              TaskModel model,
                              EventSender eventSender) {
        this.taskRepository = taskRepository;
        this.model = model;
        this.eventSender = eventSender;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        JTable table = (JTable) e.getComponent();
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        if (row >= 0 && Objects.equals(table.getColumnName(col), "Delete")) {
            Long taskId = model.getTaskIdAt(row);
            eventSender.executeAndSend(new DeleteTask(taskRepository, taskId, row, model.getModel()));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Not needed
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Not needed
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Not needed
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Not needed
    }
}
