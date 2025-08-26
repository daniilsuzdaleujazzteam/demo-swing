package com.frizzer.swing.gui.view;

import com.frizzer.swing.gui.model.TodoModel;
import com.frizzer.swing.gui.view.form.PriorityForm;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Getter
public class MainView extends JFrame {

    private JPanel mainPanel;
    private JButton addTask;
    private JButton deleteTask;
    private JButton editTask;
    private JButton moveUpTask;
    private JButton moveDownTask;
    private JButton priorityList;
    private final TodoModel model;
    private JTable mainTable;
    private final PriorityForm priorityForm;

    public MainView(TodoModel model, PriorityForm priorityForm) {
        this.model = model;
        this.priorityForm = priorityForm;

        setName("Demo Swing App");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initComponents();
        add(mainPanel);
        pack();

    }

    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(createButtonsPanel());
        mainPanel.add(createTable());
    }

    private JPanel createButtonsPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addTaskButton());
        buttonPanel.add(editTaskButton());
        buttonPanel.add(deleteTaskButton());
        buttonPanel.add(moveUpButton());
        buttonPanel.add(moveDownButton());
        buttonPanel.add(addPriorityButton());

        return buttonPanel;
    }

    private JButton addTaskButton() {
        addTask = new JButton("Add task");
        return addTask;
    }

    private JButton editTaskButton() {
        editTask = new JButton("Edit task");
        return editTask;
    }

    private JButton deleteTaskButton() {
        deleteTask = new JButton("Delete task");
        return deleteTask;
    }


    private JButton addPriorityButton() {
        priorityList = new JButton("Priority list");
        return priorityList;
    }

    private JButton moveUpButton() {
        moveUpTask = new JButton("↑");
        return moveUpTask;
    }

    private JButton moveDownButton() {
        moveDownTask = new JButton("↓");
        return moveDownTask;
    }

    public JFrame openAddTaskFrame() {
        JFrame addFrame = new JFrame("New task");
        addFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        return addFrame;
    }

    private JScrollPane createTable() {
        mainTable = new JTable(model.getModel());
        mainTable.setBorder(BorderFactory.createEmptyBorder());
        JScrollPane scrollPane = new JScrollPane(mainTable);
        scrollPane.setMaximumSize(new Dimension(500, 400));
        return scrollPane;
    }

}