package com.frizzer.swing.logic.controller;

import com.frizzer.swing.domain.Priority;
import com.frizzer.swing.domain.Todo;
import com.frizzer.swing.logic.event.sender.EventSender;
import com.frizzer.swing.gui.model.TodoModel;
import com.frizzer.swing.config.registry.ListRegistry;
import com.frizzer.swing.config.registry.TableRegistry;
import com.frizzer.swing.logic.repository.PriorityRepository;
import com.frizzer.swing.logic.repository.TodoRepository;
import com.frizzer.swing.logic.task.BaseTask;
import com.frizzer.swing.logic.task.priority.CreatePriorityTask;
import com.frizzer.swing.logic.task.priority.LoadPriorityTask;
import com.frizzer.swing.logic.task.task.LoadTodoTask;
import com.frizzer.swing.gui.view.MainView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

import static com.frizzer.swing.config.registry.RegistryNames.PRIORITY_LIST;
import static com.frizzer.swing.config.registry.RegistryNames.TASK_MAP;

@Component
@Slf4j
public class TodoController {

    private final TodoModel model;
    private final DefaultListModel<Priority> priorities;
    private final MainView view;
    private final EventSender eventSender;
    private final TodoRepository todoRepository;
    private final PriorityRepository priorityRepository;
    private final TableRegistry taskRegistry;
    private final ListRegistry<Priority> priorityRegistry;

    public TodoController(TodoModel model,
                          DefaultListModel<Priority> priorities,
                          MainView view,
                          EventSender eventSender,
                          TodoRepository todoRepository,
                          TableRegistry taskRegistry,
                          ListRegistry<Priority> priorityRegistry,
                          PriorityRepository priorityRepository) {
        this.model = model;
        this.priorities = priorities;
        this.view = view;
        this.eventSender = eventSender;
        this.todoRepository = todoRepository;
        this.priorityRepository = priorityRepository;
        this.taskRegistry = taskRegistry;
        this.priorityRegistry = priorityRegistry;

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
        task.addPropertyChangeListener(change -> {
            if (change.getNewValue() == SwingWorker.StateValue.DONE) {
                taskRegistry.register(TASK_MAP, model.getModel());
            }
        });
    }

    private void loadPriorities() {
        BaseTask<List<Priority>, Object[]> task = new LoadPriorityTask(priorityRepository, priorities::addAll);
        task.execute();
        task.addPropertyChangeListener(change -> {
            if (change.getNewValue() == SwingWorker.StateValue.DONE) {
                priorityRegistry.register(PRIORITY_LIST, priorities);
            }
        });

    }

    private void initListeners() {
        view.getAddTask().addActionListener(e -> view.openAddTaskFrame().setVisible(true));
        view.getPriorityList().addActionListener(e -> view.getPriorityForm().setVisible(true));

        view.getPriorityForm()
            .getAddPriorityButton()
            .addActionListener(e -> view.getPriorityForm().getAddPriorityForm().setVisible(true));

        view.getPriorityForm().getAddPriorityForm().getSubmitButton().addActionListener(this::addPriorityLogic);


    }

    private void addPriorityLogic(ActionEvent e) {
        var priority = new Priority().withName(view.getPriorityForm().getAddPriorityForm().getNameField().getText());
        var priorityList = view.getPriorityForm().getPriorityModel();
        eventSender.executeAndSend(new CreatePriorityTask(priorityRepository, priority, priorityList));
        //TODO: Add error if already exists logic
        view.getPriorityForm().getAddPriorityForm().dispose();
    }

}
