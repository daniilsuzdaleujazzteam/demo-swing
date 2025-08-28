package com.frizzer.swing.gui.view.form.todo;

import com.frizzer.swing.domain.Priority;
import com.frizzer.swing.domain.Todo;
import com.frizzer.swing.gui.model.priority.PriorityModel;
import com.frizzer.swing.gui.view.UpsertComponent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

@Component
@Getter
@RequiredArgsConstructor
public class UpsertTodoForm extends UpsertComponent {

    private final PriorityModel priorityModel;

    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField dateField;
    private JComboBox<Priority> priorityComboBox;

    private JPanel nameFieldPanel;
    private JPanel descriptionFieldPanel;
    private JPanel dateFieldPanel;
    private JPanel priorityFieldPanel;

    private Todo selectedTodo;

    public void setSelectedTodo(Todo todo) {
        if (todo == null) {
            throw new IllegalStateException("Todo is not selected");
        }
        this.selectedTodo = todo;
        nameField.setText(todo.getTitle());
        descriptionField.setText(todo.getDescription());
        dateField.setText(todo.getDate().toString());
        priorityComboBox.setSelectedItem(todo.getPriority());
    }

    @Override
    protected void initComponents() {
        setTitle("Task creator");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        nameFieldPanel = createNameFieldPanel();
        descriptionFieldPanel = createDescriptionFieldPanel();
        dateFieldPanel = createDateFieldPanel();
        priorityFieldPanel = createPriorityComboBoxPanel();
    }

    @Override
    protected void initButtons() {
        super.initButtons();
        cancelButton.addActionListener(e -> dispose());
    }

    @Override
    protected void placeComponents() {

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        mainPanel.add(nameFieldPanel);
        mainPanel.add(descriptionFieldPanel);
        mainPanel.add(dateFieldPanel);
        mainPanel.add(priorityFieldPanel);
        mainPanel.add(buttonPanel);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        add(mainPanel);
        pack();
    }

    @Override
    protected JPanel createMainPanel() {
        return new JPanel();
    }

    @Override
    protected JPanel createButtonPanel() {
        return new JPanel();
    }

    @Override
    protected JButton createCancelButton() {
        return new JButton("Cancel");
    }

    @Override
    protected JButton createSaveButton() {
        return new JButton("Save");
    }

    private JPanel createNameFieldPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JLabel label = new JLabel("Task name:");
        nameField = new JTextField(20);

        panel.add(label);
        panel.add(nameField);

        return panel;
    }

    private JPanel createDescriptionFieldPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JLabel label = new JLabel("Description:");
        descriptionField = new JTextField(20);

        panel.add(label);
        panel.add(descriptionField);

        return panel;
    }

    private JPanel createDateFieldPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JLabel label = new JLabel("Date (yyyy-MM-dd):");
        dateField = new JTextField(10);
        dateField.setText(LocalDate.now().toString());

        panel.add(label);
        panel.add(dateField);

        return panel;
    }

    private JPanel createPriorityComboBoxPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JLabel label = new JLabel("Priority:");
        priorityComboBox = new JComboBox<>(priorityModel.getData().toArray(new Priority[0]));
        panel.add(label);
        panel.add(priorityComboBox);

        return panel;
    }

}
