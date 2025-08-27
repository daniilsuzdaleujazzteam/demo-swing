package com.frizzer.swing.gui.view.form.todo;

import com.frizzer.swing.domain.Priority;
import com.frizzer.swing.gui.listener.PriorityListListener;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Arrays;

@Component
@Getter
public class AddTodoForm extends JFrame implements TodoForm {

    private JPanel mainPanel;
    private JTextField todoName;
    private JTextField description;
    private JTextField dateField;
    private JComboBox<Priority> priorityComboBox;
    private JButton saveButton;
    private JButton cancelButton;
    private final ListModel<Priority> priorityModel;

    public AddTodoForm(ListModel<Priority> priorityModel) {
        this.priorityModel = priorityModel;

        setTitle("Task creator");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        initComponents();
        add(mainPanel);
        pack();
    }

    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(namePanel());
        mainPanel.add(descriptionPanel());
        mainPanel.add(datePanel());
        mainPanel.add(priorityPanel());
        mainPanel.add(buttonPanel());
    }

    private JPanel namePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JLabel label = new JLabel("Task name:");
        todoName = new JTextField(20);

        panel.add(label);
        panel.add(todoName);

        return panel;
    }

    private JPanel descriptionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JLabel label = new JLabel("Description:");
        description = new JTextField(20);

        panel.add(label);
        panel.add(description);

        return panel;
    }

    private JPanel datePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JLabel label = new JLabel("Date (yyyy-MM-dd):");
        dateField = new JTextField(10);
        dateField.setText(LocalDate.now().toString());

        panel.add(label);
        panel.add(dateField);

        return panel;
    }

    private JPanel priorityPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JLabel label = new JLabel("Priority:");

        priorityModel.addListDataListener(new PriorityListListener(this));
        Priority[] priorityList = Arrays.stream(((DefaultListModel<Priority>) priorityModel).toArray())
                                        .map(Priority.class::cast)
                                        .toArray(Priority[]::new);
        priorityComboBox = new JComboBox<>(priorityList);

        panel.add(label);
        panel.add(priorityComboBox);

        return panel;
    }

    public void refreshPriorityPanel() {
        Priority[] priorityList = Arrays.stream(((DefaultListModel<Priority>) priorityModel).toArray())
                                        .map(Priority.class::cast)
                                        .toArray(Priority[]::new);

        priorityComboBox.setModel(new DefaultComboBoxModel<>(priorityList));
    }

    private JPanel buttonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        cancelButton.addActionListener(e -> dispose());

        panel.add(saveButton);
        panel.add(cancelButton);

        return panel;
    }

}
