package com.frizzer.swing.gui.view;

import jakarta.annotation.PostConstruct;

import javax.swing.*;

public abstract class FrameComponent extends JFrame {

    protected JPanel mainPanel;
    protected JPanel buttonPanel;

    protected abstract void initComponents();

    protected abstract void initButtons();

    protected abstract void placeComponents();

    protected abstract JPanel createMainPanel();

    protected abstract JPanel createButtonPanel();

    @PostConstruct
    protected void init() {
        this.mainPanel = createMainPanel();
        this.buttonPanel = createButtonPanel();
        initComponents();
        initButtons();
        placeComponents();
    }
}
