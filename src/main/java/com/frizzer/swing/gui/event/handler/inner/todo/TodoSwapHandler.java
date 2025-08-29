package com.frizzer.swing.gui.event.handler.inner.todo;

import com.frizzer.swing.gui.event.event.Event;
import com.frizzer.swing.gui.event.event.impl.PlacementSwapEvent;
import com.frizzer.swing.gui.event.handler.inner.InnerEventHandler;
import com.frizzer.swing.gui.model.todo.TodoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TodoSwapHandler extends InnerEventHandler {

    private final TodoModel todoModel;

    @Override
    public void handle(Event event) {
        if (event instanceof PlacementSwapEvent placementSwapEvent) {
            swapElements(placementSwapEvent);
        }
    }

    private void swapElements(PlacementSwapEvent placementSwapEvent) {
        todoModel.swapRows(placementSwapEvent.oldPlace(), placementSwapEvent.newPlace());
    }
}
