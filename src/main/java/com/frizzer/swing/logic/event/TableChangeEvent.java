package com.frizzer.swing.logic.event;

import java.util.List;

public record TableChangeEvent(String instanceId,
                               String tableId,
                               DataChangeType type,
                               List<Long> affectedId) implements Event {}