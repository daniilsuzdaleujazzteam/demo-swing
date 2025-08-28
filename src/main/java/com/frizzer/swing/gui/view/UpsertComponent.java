package com.frizzer.swing.gui.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.swing.*;

@Getter
@RequiredArgsConstructor
public abstract class UpsertComponent extends FrameComponent {
    protected JButton saveButton;
    protected JButton cancelButton;

    protected abstract JButton createSaveButton();

    protected abstract JButton createCancelButton();

    @Override
    protected void initButtons() {
        this.saveButton = createSaveButton();
        this.cancelButton = createCancelButton();
    }
}
