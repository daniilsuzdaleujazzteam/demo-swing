package com.frizzer.swing.gui.view.form.todo;

import com.frizzer.swing.domain.Todo;
import com.frizzer.swing.gui.model.todo.TodoModel;
import com.frizzer.swing.gui.view.CrudComponent;
import com.frizzer.swing.gui.view.form.priority.PriorityForm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Component
@Getter
@RequiredArgsConstructor
public class TodoForm extends CrudComponent {

    private final UpsertTodoForm upsertTodoForm;
    private final TodoModel todoModel;
    private final PriorityForm priorityForm;

    private JTable mainTable;
    private JButton listPriorityButton;
    private JButton moveUpButton;
    private JButton moveDownButton;

    @Override
    protected void initComponents() {
        setName("Demo Swing App");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    protected void initButtons() {
        super.initButtons();
        moveUpButton = createMoveUpButton();
        moveDownButton = createMoveDownButton();
        listPriorityButton = createListPriorityButton();
    }

    @Override
    protected void placeComponents() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(createTable());

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(createButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(moveDownButton);
        buttonPanel.add(moveUpButton);
        buttonPanel.add(listPriorityButton);

        mainPanel.add(buttonPanel, 0);

        add(mainPanel);
        pack();
    }

    @Override
    protected JPanel createMainPanel() {
        mainPanel = new JPanel();
        return mainPanel;
    }

    @Override
    protected JPanel createButtonPanel() {
        buttonPanel = new JPanel();
        return buttonPanel;
    }

    @Override
    protected JButton createCreateButton() {
        createButton = new JButton("Add");
        createButton.addActionListener(e -> upsertTodoForm.setVisible(true));
        return createButton;
    }

    @Override
    protected JButton createEditButton() {
        editButton = new JButton("Edit");
        editButton.addActionListener(this::openEditTodo);
        return editButton;
    }

    @Override
    protected JButton createDeleteButton() {
        deleteButton = new JButton("Delete");
        return deleteButton;
    }

    private JButton createListPriorityButton() {
        listPriorityButton = new JButton("Priority list");
        listPriorityButton.addActionListener(e -> priorityForm.setVisible(true));
        return listPriorityButton;
    }

    private JButton createMoveUpButton() {
        return new JButton("↑");
    }

    private JButton createMoveDownButton() {
        return new JButton("↓");
    }

    private JScrollPane createTable() {
        mainTable = new JTable(todoModel);
        mainTable.setBorder(BorderFactory.createEmptyBorder());
        JScrollPane scrollPane = new JScrollPane(mainTable);
        scrollPane.setMaximumSize(new Dimension(500, 400));
        return scrollPane;
    }

    private void openEditTodo(ActionEvent e) {
        int selectedRowIndex = getMainTable().getSelectedRow();
        if (selectedRowIndex != -1) {
            Todo selectedTodo = todoModel.getTodoAt(selectedRowIndex);
            upsertTodoForm.setSelectedTodo(selectedTodo);
            upsertTodoForm.setVisible(true);
        }
    }

}