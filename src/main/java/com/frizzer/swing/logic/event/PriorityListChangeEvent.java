package com.frizzer.swing.logic.event;

public record PriorityListChangeEvent(String instanceId,
                                      String listId,
                                      DataChangeType type,
                                      long firstId,
                                      long lastId) implements Event {}
