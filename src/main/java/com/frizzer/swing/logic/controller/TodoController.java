package com.frizzer.swing.logic.controller;

import com.frizzer.swing.domain.Priority;
import com.frizzer.swing.domain.Todo;
import com.frizzer.swing.gui.model.TodoModel;
import com.frizzer.swing.gui.view.MainView;
import com.frizzer.swing.logic.event.sender.EventSender;
import com.frizzer.swing.logic.repository.PriorityRepository;
import com.frizzer.swing.logic.repository.TodoRepository;
import com.frizzer.swing.logic.task.BaseTask;
import com.frizzer.swing.logic.task.priority.CreatePriorityTask;
import com.frizzer.swing.logic.task.priority.EditPriorityTask;
import com.frizzer.swing.logic.task.priority.LoadPriorityByName;
import com.frizzer.swing.logic.task.priority.LoadPriorityTask;
import com.frizzer.swing.logic.task.priority.RemovePriorityTask;
import com.frizzer.swing.logic.task.todo.LoadTodoTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

import static com.frizzer.swing.gui.model.TodoColumns.DATE;
import static com.frizzer.swing.gui.model.TodoColumns.DESCRIPTION;
import static com.frizzer.swing.gui.model.TodoColumns.PRIORITY;
import static com.frizzer.swing.gui.model.TodoColumns.TITLE;

@Component
@Slf4j
public class TodoController {

    private final TodoModel model;
    private final DefaultListModel<Priority> priorities;
    private final MainView view;
    private final EventSender eventSender;
    private final TodoRepository todoRepository;
    private final PriorityRepository priorityRepository;

    public TodoController(TodoModel model,
                          DefaultListModel<Priority> priorities,
                          MainView view,
                          EventSender eventSender,
                          TodoRepository todoRepository,
                          PriorityRepository priorityRepository) {
        this.model = model;
        this.priorities = priorities;
        this.view = view;
        this.eventSender = eventSender;
        this.todoRepository = todoRepository;
        this.priorityRepository = priorityRepository;

        initView();
        initListeners();
    }

    public void show() {
        view.setVisible(true);
    }

    private void initView() {
        loadTask();
        loadPriorities();
    }

    private void loadTask() {
        BaseTask<List<Todo>, Object[]> task = new LoadTodoTask(todoRepository, model::setTasks);
        task.execute();
    }

    private void loadPriorities() {
        BaseTask<List<Priority>, Object[]> task = new LoadPriorityTask(priorityRepository, priorities::addAll);
        task.execute();
    }

    private void initListeners() {
        view.getAddTask().addActionListener(e -> view.getAddTodoForm().setVisible(true));
        view.getEditTask().addActionListener(this::openEditTodo);

        view.getPriorityList().addActionListener(e -> view.getPriorityForm().setVisible(true));

        view.getPriorityForm()
            .getAddPriorityButton()
            .addActionListener(e -> view.getPriorityForm().getAddPriorityForm().setVisible(true));

        view.getPriorityForm().getEditPriorityButton().addActionListener(this::openEditPriority);
        view.getPriorityForm().getDeletePriorityButton().addActionListener(this::deletePriorityLogic);
        view.getPriorityForm().getEditPriorityForm().getSubmitButton().addActionListener(this::editPriorityLogic);
        view.getPriorityForm().getAddPriorityForm().getSubmitButton().addActionListener(this::addPriorityLogic);


    }

    private void openEditTodo(ActionEvent e) {
        var selectedTodo = view.getMainTable().getSelectedRow();
        if (selectedTodo != -1) {
            Vector<?> todoVector = model.getModel().getDataVector().get(selectedTodo);
            new LoadPriorityByName(priorityRepository, (String) todoVector.get(PRIORITY.getOrder()), priority -> {
                Todo todo = new Todo(null,
                        (String) todoVector.get(TITLE.getOrder()),
                        (String) todoVector.get(DESCRIPTION.getOrder()),
                        LocalDate.parse((String) todoVector.get(DATE.getOrder())),
                        priority);

                view.getEditTodoForm().setSelectedTodo(todo);
                view.getEditTodoForm().setVisible(true);
            }).execute();
        }
    }

    private void addPriorityLogic(ActionEvent e) {
        var priority = view.getPriorityForm().getAddPriorityForm().buildPriority();
        var priorityList = view.getPriorityForm().getPriorityModel();
        eventSender.executeAndSend(new CreatePriorityTask(priorityRepository,
                priority,
                (DefaultListModel<Priority>) priorityList));
        view.getPriorityForm().getAddPriorityForm().dispose();
    }

    private void editPriorityLogic(ActionEvent e) {
        String oldName = view.getPriorityForm().getEditPriorityForm().getSelectedPriority().getName();
        String name = view.getPriorityForm().getEditPriorityForm().getNameField().getText();
        long weight = Long.parseLong(view.getPriorityForm().getEditPriorityForm().getWeightField().getText());
        new LoadPriorityByName(priorityRepository, oldName, priority -> {
            Priority editedPriority = priority.withName(name).withWeight(weight);
            eventSender.executeAndSend(new EditPriorityTask(priorityRepository, editedPriority, priorities));
        }).execute();
        view.getPriorityForm().getEditPriorityForm().dispose();
    }

    private void deletePriorityLogic(ActionEvent e) {
        String name = view.getPriorityForm().getPriorityList().getSelectedValue().getName();
        new LoadPriorityByName(priorityRepository, name, priority -> {
            eventSender.executeAndSend(new RemovePriorityTask(priorityRepository, priority, priorities));
        }).execute();
    }

    private void openEditPriority(ActionEvent e) {
        var selectedPriority = view.getPriorityForm().getPriorityList().getSelectedValue();
        if (selectedPriority != null) {
            view.getPriorityForm().getEditPriorityForm().setSelectedPriority(selectedPriority);
            view.getPriorityForm().getEditPriorityForm().setVisible(true);
        }
    }

}
