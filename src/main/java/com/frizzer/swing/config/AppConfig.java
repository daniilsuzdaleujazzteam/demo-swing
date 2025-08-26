package com.frizzer.swing.config;

import com.frizzer.swing.entity.Priority;
import com.frizzer.swing.model.TodoModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@Configuration
public class AppConfig {

    @Bean
    public TodoModel taskModel() {
        return new TodoModel(new DefaultTableModel());
    }

    @Bean
    public DefaultListModel<Priority> priorityModel() {return new DefaultListModel<>();}

}
