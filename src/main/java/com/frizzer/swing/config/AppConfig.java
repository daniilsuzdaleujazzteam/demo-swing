package com.frizzer.swing.config;

import com.frizzer.swing.domain.Priority;
import com.frizzer.swing.gui.model.TodoModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static com.frizzer.swing.config.registry.RegistryNames.TODO_MAP;

@Configuration
public class AppConfig {

    @Bean
    public TodoModel todoModel() {
        return new TodoModel(new DefaultTableModel(), TODO_MAP);
    }

    @Bean
    public DefaultListModel<Priority> priorityModel() {
        return new DefaultListModel<>();
    }

}
