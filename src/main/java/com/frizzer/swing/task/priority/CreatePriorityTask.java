package com.frizzer.swing.task.priority;

import com.frizzer.swing.entity.Priority;
import com.frizzer.swing.event.DataChangeType;
import com.frizzer.swing.repository.PriorityRepository;
import com.frizzer.swing.task.PriorityTask;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

@RequiredArgsConstructor
@Slf4j
public class CreatePriorityTask extends PriorityTask<Priority, Object[]> {

    private final PriorityRepository repository;
    private final Priority priority;
    private final DefaultListModel<Priority> listModel;

    @Override
    protected Priority doInBackground(){
        log.info("Started saving task with name {}", priority.getName());
        Priority saved = repository.save(priority);
        firstId = saved.getId();
        lastId = saved.getId();
        return saved;
    }

    @Override
    @SneakyThrows
    public void done(){
        Priority saved = get();
        listModel.addElement(saved);
        log.info("Finished updating task with name {}", priority.getName());
    }

    @Override
    public DataChangeType getType() {
        return DataChangeType.INSERT;
    }
}
