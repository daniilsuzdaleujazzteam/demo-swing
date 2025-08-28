package com.frizzer.swing.gui.model.todo;

import com.frizzer.swing.domain.Priority;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
enum TodoColumns {

    TITLE( 0, String.class),
    DESCRIPTION(1, String.class),
    DATE(2, LocalDate.class),
    PRIORITY(3, Priority.class);

    private final int order;
    private final Class<?> type;
}
