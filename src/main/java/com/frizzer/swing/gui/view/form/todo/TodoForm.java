package com.frizzer.swing.gui.view.form.todo;

import com.frizzer.swing.domain.Todo;
import com.frizzer.swing.gui.event.event.impl.PlacementSwapEvent;
import com.frizzer.swing.gui.model.todo.TodoModel;
import com.frizzer.swing.gui.view.CrudComponent;
import com.frizzer.swing.gui.view.form.priority.PriorityForm;
import com.frizzer.swing.logic.repository.TodoRepository;
import com.frizzer.swing.logic.tasks.todo.DeleteTodoTask;
import com.frizzer.swing.logic.tasks.todo.UpsertTodoTask;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.awt.event.ActionEvent;

import static com.frizzer.swing.config.IdGenerator.INSTANCE_ID;

@Component
@Getter
@RequiredArgsConstructor
public class TodoForm extends CrudComponent {

    private final UpsertTodoForm upsertTodoForm;
    private final TodoModel todoModel;
    private final PriorityForm priorityForm;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final TodoRepository todoRepository;

    private JScrollPane mainPane;
    private JTable mainTable;
    private JButton listPriorityButton;
    private JButton moveUpButton;
    private JButton moveDownButton;

    @Override
    protected void initComponents() {
        setName("Demo Swing App");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainPane = createScrollPane();
        todoModel.addTableModelListener(this::updateTableFromCell);
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
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(createButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(moveUpButton);
        buttonPanel.add(moveDownButton);
        buttonPanel.add(listPriorityButton);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(buttonPanel);
        mainPanel.add(mainPane);

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
        deleteButton.addActionListener(this::deleteTodo);
        return deleteButton;
    }

    private JButton createListPriorityButton() {
        listPriorityButton = new JButton("Priority list");
        listPriorityButton.addActionListener(e -> priorityForm.setVisible(true));
        return listPriorityButton;
    }

    private JButton createMoveUpButton() {
        moveUpButton = new JButton("↓");
        moveUpButton.addActionListener(this::moveUp);
        return moveUpButton;
    }

    private JButton createMoveDownButton() {
        moveDownButton = new JButton("↑");
        moveDownButton.addActionListener(this::moveDown);
        return moveDownButton;
    }

    private JScrollPane createScrollPane() {
        mainPane = new JScrollPane(createTable());
        mainPane.setMaximumSize(new Dimension(500, 400));
        return mainPane;
    }

    private JTable createTable() {
        mainTable = new JTable(todoModel);
        mainTable.setBorder(BorderFactory.createEmptyBorder());
        return mainTable;
    }

    private void openEditTodo(ActionEvent e) {
        int selectedRowIndex = mainTable.getSelectedRow();
        if (selectedRowIndex != -1) {
            Todo selectedTodo = todoModel.getTodoAt(selectedRowIndex);
            upsertTodoForm.setSelectedTodo(selectedTodo);
            upsertTodoForm.setVisible(true);
        }
    }

    private void deleteTodo(ActionEvent e) {
        int selectedRow = mainTable.getSelectedRow();
        if (selectedRow != -1) {
            Todo selectedTodo = todoModel.getTodoAt(selectedRow);
            new DeleteTodoTask(todoRepository, selectedTodo, applicationEventPublisher).execute();
        }
    }

    private void moveUp(ActionEvent e) {
        int selectedRow = mainTable.getSelectedRow();
        if (selectedRow < mainTable.getRowCount() - 1) {
            applicationEventPublisher.publishEvent(new PlacementSwapEvent(selectedRow,
                    selectedRow + 1,
                    INSTANCE_ID));
        }
    }

    private void moveDown(ActionEvent e) {
        int selectedRow = mainTable.getSelectedRow();
        if (selectedRow > 0) {
            applicationEventPublisher.publishEvent(new PlacementSwapEvent(selectedRow,
                    selectedRow - 1,
                    INSTANCE_ID));
        }
    }

    private void updateTableFromCell(TableModelEvent e) {
        if (!todoModel.isInternalUpdate() && e.getType() == TableModelEvent.UPDATE) {
            int row = e.getFirstRow();
            Todo updatedTodo = todoModel.getTodoAt(row);
            new UpsertTodoTask(todoRepository, updatedTodo, applicationEventPublisher).execute();
        }
    }

}