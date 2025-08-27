package com.frizzer.swing.gui.view.form.priority;

import com.frizzer.swing.domain.Priority;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Getter
@Component
public class PriorityForm extends JFrame {

    private final AddPriorityForm addPriorityForm;
    private final EditPriorityForm editPriorityForm;
    private JPanel mainPanel;
    private JList<Priority> priorityList;
    private JButton addPriorityButton;
    private JButton editPriorityButton;
    private JButton deletePriorityButton;
    private final ListModel<Priority> priorityModel;

    public PriorityForm(AddPriorityForm addPriorityForm,
                        EditPriorityForm editPriorityForm,
                        ListModel<Priority> priorityModel) {
        this.addPriorityForm = addPriorityForm;
        this.editPriorityForm = editPriorityForm;
        this.priorityModel = priorityModel;

        setTitle("Priority Form");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
        add(mainPanel);
        pack();
    }

    public void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));
        mainPanel.add(buttonPanel());
        mainPanel.add(prioritiesList());
    }

    public JScrollPane prioritiesList() {
        priorityList = new JList<>(priorityModel);
        JScrollPane scrollPane = new JScrollPane(priorityList);
        scrollPane.setPreferredSize(new Dimension(200, 200));

        return scrollPane;
    }

    private JPanel buttonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(addPriorityButton());
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(editPriorityButton());
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(deletePriorityButton());

        return buttonPanel;
    }

    private JButton addPriorityButton() {
        addPriorityButton = new JButton("Add Priority");

        Dimension buttonSize = new Dimension(150, 30);

        addPriorityButton.setPreferredSize(buttonSize);
        addPriorityButton.setMaximumSize(buttonSize);

        return addPriorityButton;
    }

    private JButton editPriorityButton() {
        editPriorityButton = new JButton("Edit Priority");

        Dimension buttonSize = new Dimension(150, 30);

        editPriorityButton.setPreferredSize(buttonSize);
        editPriorityButton.setMaximumSize(buttonSize);

        return editPriorityButton;
    }

    private JButton deletePriorityButton() {
        deletePriorityButton = new JButton("Delete Priority");

        Dimension buttonSize = new Dimension(150, 30);
        deletePriorityButton.setPreferredSize(buttonSize);
        deletePriorityButton.setMaximumSize(buttonSize);

        return deletePriorityButton;
    }

}
