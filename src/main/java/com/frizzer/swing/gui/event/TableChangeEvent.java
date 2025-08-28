package com.frizzer.swing.gui.event;

import java.util.List;

public record TableChangeEvent(String instanceId,
                               String tableId,
                               DataChangeType type,
                               List<Long> affectedId) implements Event {}