package com.frizzer.swing.controller;

import com.frizzer.swing.entity.Priority;
import com.frizzer.swing.entity.Task;
import com.frizzer.swing.event.sender.EventSender;
import com.frizzer.swing.listener.ChangePriorityListener;
import com.frizzer.swing.listener.DeleteItemListener;
import com.frizzer.swing.model.TaskModel;
import com.frizzer.swing.registry.ListRegistry;
import com.frizzer.swing.registry.TableRegistry;
import com.frizzer.swing.repository.PriorityRepository;
import com.frizzer.swing.repository.TaskRepository;
import com.frizzer.swing.task.BaseTask;
import com.frizzer.swing.task.priority.CreatePriorityTask;
import com.frizzer.swing.task.priority.LoadPriorityTask;
import com.frizzer.swing.task.task.LoadTask;
import com.frizzer.swing.view.MainView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;

import static com.frizzer.swing.registry.RegistryNames.PRIORITY_LIST;
import static com.frizzer.swing.registry.RegistryNames.TASK_MAP;

@Component
@Slf4j
public class TaskController {

    private final TaskModel model;
    private final DefaultListModel<Priority> priorities;
    private final MainView view;
    private final EventSender eventSender;
    private final TaskRepository taskRepository;
    private final PriorityRepository priorityRepository;
    private final TableRegistry taskRegistry;
    private final ListRegistry<Priority> priorityRegistry;

    public TaskController(TaskModel model,
                          DefaultListModel<Priority> priorities,
                          MainView view,
                          EventSender eventSender,
                          TaskRepository taskRepository,
                          TableRegistry taskRegistry,
                          ListRegistry<Priority> priorityRegistry,
                          PriorityRepository priorityRepository) {
        this.model = model;
        this.priorities = priorities;
        this.view = view;
        this.eventSender = eventSender;
        this.taskRepository = taskRepository;
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
        BaseTask<List<Task>, Object[]> task = new LoadTask(taskRepository, model::setTasks);
        task.execute();
        task.addPropertyChangeListener(change -> {
            if (change.getNewValue() == SwingWorker.StateValue.DONE) {
                view.updateTable(model);
                taskRegistry.register(TASK_MAP, model.getModel());
            }
        });
    }

    private void loadPriorities() {
        BaseTask<List<Priority>, Object[]> task = new LoadPriorityTask(priorityRepository, priorities::addAll);
        task.execute();
        task.addPropertyChangeListener(change -> {
            if (change.getNewValue() == SwingWorker.StateValue.DONE) {
                view.addPriorities(Collections.list(priorities.elements()));
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

        view.getPriorityForm()
            .getPriorityModel()
            .addListDataListener(new ChangePriorityListener(view.getPriorityForm()));

        view.getMainTable().addMouseListener(new DeleteItemListener(taskRepository, model, eventSender));
    }

    private void addPriorityLogic(ActionEvent e) {
        var priority = new Priority().withName(view.getPriorityForm().getAddPriorityForm().getTextField().getText());
        var priorityList = view.getPriorityForm().getPriorityModel();
        eventSender.executeAndSend(new CreatePriorityTask(priorityRepository, priority, priorityList));
        //TODO: Add error if already exists logic
        view.getPriorityForm().getAddPriorityForm().dispose();
    }

}
