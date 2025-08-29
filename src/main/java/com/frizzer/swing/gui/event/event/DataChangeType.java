package com.frizzer.swing.gui.event.event;

import lombok.Getter;

@Getter
public enum DataChangeType {
    UPSERT(1), LOAD(0), DELETE(-1);

    final int value;

    DataChangeType(int i) {
        this.value = i;
    }
}
