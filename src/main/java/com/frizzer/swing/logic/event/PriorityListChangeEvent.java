package com.frizzer.swing.logic.event;

import java.util.List;

public record PriorityListChangeEvent(String instanceId,
                                      String listId,
                                      DataChangeType type,
                                      List<Long> affectedId) implements Event {}
