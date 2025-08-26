package com.frizzer.swing.gui.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TodoColumns {

    ID("Title", 0),
    DESCRIPTION("Description", 1),
    DATE("Date", 2),
    PRIORITY("Priority", 3);

    private final String name;
    private final int order;
}
