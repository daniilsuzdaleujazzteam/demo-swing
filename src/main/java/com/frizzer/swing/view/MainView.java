package com.frizzer.swing.view;

import com.frizzer.swing.entity.Priority;
import com.frizzer.swing.model.TaskModel;
import com.frizzer.swing.view.form.PriorityForm;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

@Component
@Getter
public class MainView extends JFrame {

    private JPanel mainPanel;
    private JButton addTask;
    private JButton priorityList;
    private final TaskModel model;
    private JTable mainTable;
    private final PriorityForm priorityForm;

    public MainView(TaskModel model, PriorityForm priorityForm) {
        this.model = model;
        this.priorityForm = priorityForm;

        setName("Demo Swing App");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initComponents();
        add(mainPanel);
        pack();

    }

    public void updateTable(TaskModel model) {
        model.getModel().setDataVector(model.getModel().getDataVector(), new Vector<>(List.of(model.getColumns())));
    }

    public void addPriorities(List<Priority> priorities) {
        priorityForm.addToPriorityList(priorities.stream().map(Priority::getName).toList());
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
        buttonPanel.add(addPriorityButton());

        return buttonPanel;
    }

    private JButton addTaskButton() {
        addTask = new JButton("Add task");
        addTask.setBounds(150, 200, 220, 50);
        return addTask;
    }

    private JButton addPriorityButton() {
        priorityList = new JButton("Priority list");
        priorityList.setBounds(150, 200, 220, 50);
        return priorityList;
    }

    public JFrame openAddTaskFrame() {
        JFrame addFrame = new JFrame("New task");
        addFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        addFrame.setSize(500, 500);

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