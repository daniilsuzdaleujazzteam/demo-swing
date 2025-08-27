package com.frizzer.swing.logic.task.priority;

import com.frizzer.swing.domain.Priority;
import com.frizzer.swing.logic.event.DataChangeType;
import com.frizzer.swing.logic.repository.PriorityRepository;
import com.frizzer.swing.logic.task.PriorityTask;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class LoadPriorityByName extends PriorityTask<Optional<Priority>, Object[]> {

    private final PriorityRepository priorityRepository;
    private final String name;
    private final Consumer<Priority> priorityConsumer;


    @Override
    protected Optional<Priority> doInBackground(){
        log.info("Started loading priorities");
        return priorityRepository.findByName(name);
    }

    @Override
    @SneakyThrows
    public void done() {
        var priority = get().orElseThrow(() ->new IllegalStateException("Priority with name " + name + " not found"));
        priorityConsumer.accept(priority);
        log.info("Finished loading priorities");
    }

    @Override
    public DataChangeType getType() {
        return DataChangeType.LOAD;
    }
}
