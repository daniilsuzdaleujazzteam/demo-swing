package com.frizzer.swing.view.form;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Getter
@Component
public class AddPriorityForm extends JFrame {

    private JTextField textField;
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

        JLabel label = new JLabel("Priority name:");
        textField = new JTextField(20);
        submitButton = new JButton("Save");


        mainPanel.add(label);
        mainPanel.add(textField);
        mainPanel.add(submitButton);
    }
}
