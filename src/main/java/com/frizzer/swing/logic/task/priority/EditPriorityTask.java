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
public class EditPriorityTask extends PriorityTask<Priority, Object[]> {

    private final PriorityRepository repository;
    private final Priority priority;
    private final DefaultListModel<Priority> listModel;

    @Override
    protected Priority doInBackground() {
        log.info("Started editing priority with name {}", priority.getName());
        Priority updated = repository.save(priority);
        idList.add(updated.getId());
        return updated;
    }

    @Override
    @SneakyThrows
    public void done() {
        Priority updated = get();
        SwingUtilities.invokeLater(() -> {
            int index = java.util.stream.IntStream.range(0, listModel.getSize())
                                                  .filter(i -> listModel.get(i).getId().equals(updated.getId()))
                                                  .findFirst()
                                                  .orElse(-1);

            if (index != -1) {
                listModel.set(index, updated);
            }
        });
        log.info("Finished editing priority with name {}", priority.getName());
    }

    @Override
    public DataChangeType getType() {
        return DataChangeType.UPDATE;
    }
}
