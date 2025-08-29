package com.frizzer.swing.config;

import com.frizzer.swing.gui.controller.TodoController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.awt.*;

import static com.frizzer.swing.config.IdGenerator.INSTANCE_ID;

@Component
@Slf4j
class SwingStarter implements ApplicationRunner {
    private final TodoController controller;

    public SwingStarter(TodoController controller) {
        this.controller = controller;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Starting application with instance id {}", INSTANCE_ID);
        EventQueue.invokeLater(controller::show);
    }
}