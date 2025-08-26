package com.frizzer.swing.starter;

import com.frizzer.swing.controller.TodoController;
import com.frizzer.swing.registry.UUIDProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@Slf4j
class SwingStarter implements ApplicationRunner {
    private final TodoController controller;
    private final UUIDProvider idProvider;

    public SwingStarter(TodoController controller, UUIDProvider idProvider) {
        this.controller = controller;
        this.idProvider = idProvider;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Starting application with instance id {}", idProvider.getId());
        EventQueue.invokeLater(controller::show);
    }
}