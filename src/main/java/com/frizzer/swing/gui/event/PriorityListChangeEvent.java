package com.frizzer.swing.gui.event;

import java.util.List;

public record PriorityListChangeEvent(String instanceId,
                                      String listId,
                                      DataChangeType type,
                                      List<Long> affectedId) implements Event {}
