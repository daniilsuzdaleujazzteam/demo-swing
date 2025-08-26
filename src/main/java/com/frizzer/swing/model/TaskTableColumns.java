package com.frizzer.swing.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskTableColumns {

    ID("Id", 0),
    DESCRIPTION("Description", 1),
    DATE("Date", 2),
    PRIORITY("Priority", 3),
    DELETE("Delete", 4);

    private final String name;
    private final int order;
}
