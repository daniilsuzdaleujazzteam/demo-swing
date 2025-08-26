package com.frizzer.swing.task.priority;

import com.frizzer.swing.entity.Priority;
import com.frizzer.swing.event.DataChangeType;
import com.frizzer.swing.repository.PriorityRepository;
import com.frizzer.swing.task.PriorityTask;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public class LoadPriorityTask extends PriorityTask<List<Priority>, Object[]> {

    private final PriorityRepository priorityRepository;
    private final Consumer<List<Priority>> priorityConsumer;

    @Override
    protected List<Priority> doInBackground(){
        log.info("Started loading priorities");
        return priorityRepository.findAll();
    }

    @Override
    @SneakyThrows
    public void done() {
        priorityConsumer.accept(get());
        log.info("Finished loading priorities");
    }

    @Override
    public DataChangeType getType() {
        return DataChangeType.LOAD;
    }
}
