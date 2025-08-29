package com.frizzer.swing.gui.controller;

import com.frizzer.swing.gui.view.form.todo.TodoForm;
import com.frizzer.swing.logic.repository.PriorityRepository;
import com.frizzer.swing.logic.repository.TodoRepository;
import com.frizzer.swing.logic.tasks.priority.LoadPriorityTask;
import com.frizzer.swing.logic.tasks.todo.LoadTodoTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TodoController {
    private final TodoForm todoForm;
    private final TodoRepository todoRepository;
    private final PriorityRepository priorityRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @EventListener(ApplicationReadyEvent.class)
    private void loadData() {
        loadTask();
        loadPriorities();
    }

    public void show() {
        todoForm.setVisible(true);
    }

    private void loadTask() {
        new LoadTodoTask(todoRepository, applicationEventPublisher).execute();
    }

    private void loadPriorities() {
        new LoadPriorityTask(priorityRepository, applicationEventPublisher).execute();
    }


}
