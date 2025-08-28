package com.frizzer.swing.gui.event.sender;

import com.frizzer.swing.config.UUIDProvider;
import com.frizzer.swing.gui.event.Event;
import com.frizzer.swing.logic.tasks.BaseTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventSender {

    private final JmsTemplate jmsTemplate;
    private final UUIDProvider idProvider;

    public void sendEvent(Event event) {
        log.info("Sending event: {}", event);
        jmsTemplate.convertAndSend("eventTopic", event);
    }

    //TODO: Change logic to work with events separate from tasks
    public void executeAndSend(BaseTask<?, ?> task) {
        task.execute();
        task.addPropertyChangeListener(change -> {
//            if (change.getNewValue() == SwingWorker.StateValue.DONE) {
//                sendEvent(task.createEvent(idProvider.getId()));
//            }
        });
    }

}
