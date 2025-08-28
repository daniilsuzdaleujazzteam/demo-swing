package com.frizzer.swing.gui.event;

import lombok.Getter;

@Getter
public enum DataChangeType {
    INSERT(1), UPDATE(0), DELETE(-1), LOAD(0);

    final int value;

    DataChangeType(int i) {
        this.value = i;
    }
}
