package com.frizzer.swing.logic.event.handler;

import com.frizzer.swing.logic.event.Event;
import com.frizzer.swing.logic.event.PriorityListChangeEvent;
import com.frizzer.swing.config.registry.UUIDProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PriorityListEventHandler implements EventHandler<PriorityListChangeEvent> {

    private final UUIDProvider idProvider;

    @Override
    public Class<PriorityListChangeEvent> getEventClass() {
        return PriorityListChangeEvent.class;
    }

    @Override
    public void handle(Event e) {
        PriorityListChangeEvent event = (PriorityListChangeEvent) e;
        log.info("PriorityListChangeEvent received with type {} for list {} with id {}",
                event.type(),
                event.listId(),
                event.instanceId());

        //TODO: add handle logic

    }
}
