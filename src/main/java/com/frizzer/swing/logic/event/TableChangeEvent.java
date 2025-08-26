package com.frizzer.swing.logic.event;

public record TableChangeEvent(String instanceId,
                               String tableId,
                               DataChangeType type,
                               long firstId,
                               long lastId) implements Event {}