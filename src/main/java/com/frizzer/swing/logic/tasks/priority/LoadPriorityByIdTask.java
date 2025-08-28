package com.frizzer.swing.logic.tasks.priority;

import com.frizzer.swing.domain.Priority;
import com.frizzer.swing.gui.event.DataChangeType;
import com.frizzer.swing.logic.repository.PriorityRepository;
import com.frizzer.swing.logic.tasks.BaseTask;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class LoadPriorityByIdTask extends BaseTask<List<Priority>, Object[]> {

    private final PriorityRepository priorityRepository;
    private final List<Long> ids;
    private final Consumer<List<Priority>> priorityConsumer;


    @Override
    protected List<Priority> doInBackground() {
        log.info("Started loading priorities");
        return priorityRepository.findByIdIn(ids);
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
