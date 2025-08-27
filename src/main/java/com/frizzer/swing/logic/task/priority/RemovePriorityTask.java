package com.frizzer.swing.logic.task.priority;

import com.frizzer.swing.domain.Priority;
import com.frizzer.swing.logic.event.DataChangeType;
import com.frizzer.swing.logic.repository.PriorityRepository;
import com.frizzer.swing.logic.task.PriorityTask;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

@RequiredArgsConstructor
@Slf4j
public class RemovePriorityTask extends PriorityTask<Priority, Object[]> {

    private final PriorityRepository repository;
    private final Priority priority;
    private final DefaultListModel<Priority> listModel;

    @Override
    protected Priority doInBackground(){
        log.info("Started deleting priority with name {}", priority.getName());
        repository.removePriorityByName(priority.getName());
        idList.add(priority.getId());
        return priority;
    }

    @Override
    @SneakyThrows
    public void done(){
        Priority removed = get();
        listModel.removeElement(removed);
        log.info("Finished delete task with name {}", priority.getName());
    }

    @Override
    public DataChangeType getType() {
        return DataChangeType.DELETE;
    }
}
