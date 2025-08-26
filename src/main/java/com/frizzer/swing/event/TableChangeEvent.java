package com.frizzer.swing.event;

public record TableChangeEvent(String instanceId,
                               String tableId,
                               DataChangeType type,
                               long firstId,
                               long lastId) implements Event {}