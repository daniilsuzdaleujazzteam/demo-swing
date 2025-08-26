package com.frizzer.swing.gui.view.form;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Getter
@Component
public class AddPriorityForm extends JFrame {

    private JTextField nameField;
    private JTextField weightField;
    private JButton submitButton;
    private JPanel mainPanel;

    public AddPriorityForm() {
        setTitle("Add priority");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        initComponents();
        add(mainPanel);
        pack();
    }

    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(namePanel());
        mainPanel.add(weightPanel());
        mainPanel.add(submitButton());
    }

    private JPanel namePanel() {
        JPanel panel = new JPanel();

        JLabel label = new JLabel("Priority name:");
        nameField = new JTextField(20);

        panel.add(label);
        panel.add(nameField);

        return panel;
    }

    private JPanel weightPanel() {
        JPanel panel = new JPanel();

        JLabel label = new JLabel("Priority weight:");
        weightField = new JTextField(20);

        panel.add(label);
        panel.add(weightField);

        return panel;
    }

    private JButton submitButton() {
        this.submitButton = new JButton("Submit");
        return submitButton;
    }
}
