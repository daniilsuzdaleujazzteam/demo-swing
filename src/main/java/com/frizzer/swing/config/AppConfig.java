package com.frizzer.swing.config;

import com.frizzer.swing.controller.TaskController;
import com.frizzer.swing.entity.Priority;
import com.frizzer.swing.event.sender.EventSender;
import com.frizzer.swing.model.TaskModel;
import com.frizzer.swing.registry.ListRegistry;
import com.frizzer.swing.registry.TableRegistry;
import com.frizzer.swing.repository.PriorityRepository;
import com.frizzer.swing.repository.TaskRepository;
import com.frizzer.swing.view.MainView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@Configuration
public class AppConfig {

    @Bean
    public TaskModel taskModel() {
        return new TaskModel(new DefaultTableModel());
    }

    @Bean
    public DefaultListModel<Priority> priorityModel() {return new DefaultListModel<>();}

    @Bean
    public TaskController taskController(EventSender eventSender,
                                         TaskRepository taskRepository,
                                         TableRegistry tableRegistry,
                                         MainView mainView,
                                         ListRegistry<Priority> listRegistry,
                                         PriorityRepository priorityRepository) {
        return new TaskController(taskModel(),
                new DefaultListModel<>(),
                mainView,
                eventSender,
                taskRepository,
                tableRegistry,
                listRegistry,
                priorityRepository);
    }

}
