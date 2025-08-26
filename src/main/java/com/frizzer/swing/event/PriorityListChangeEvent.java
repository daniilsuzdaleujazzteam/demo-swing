package com.frizzer.swing.event;

public record PriorityListChangeEvent(String instanceId,
                                      String listId,
                                      DataChangeType type,
                                      long firstId,
                                      long lastId) implements Event {}
