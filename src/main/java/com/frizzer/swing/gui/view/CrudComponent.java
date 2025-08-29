package com.frizzer.swing.gui.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.swing.*;

@Getter
@RequiredArgsConstructor
public abstract class CrudComponent extends FrameComponent {
    protected JButton createButton;
    protected JButton editButton;
    protected JButton deleteButton;

    protected abstract JButton createCreateButton();

    protected abstract JButton createEditButton();

    protected abstract JButton createDeleteButton();

    @Override
    protected void initButtons() {
        this.createButton = createCreateButton();
        this.editButton = createEditButton();
        this.deleteButton = createDeleteButton();
    }
}
