package com.frizzer.swing.event.handler;

import com.frizzer.swing.event.Event;
import com.frizzer.swing.event.PriorityListChangeEvent;
import com.frizzer.swing.registry.UUIDProvider;
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
