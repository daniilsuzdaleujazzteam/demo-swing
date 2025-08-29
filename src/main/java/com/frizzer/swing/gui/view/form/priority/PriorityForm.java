package com.frizzer.swing.gui.view.form.priority;

import com.frizzer.swing.domain.Priority;
import com.frizzer.swing.gui.event.sender.EventSender;
import com.frizzer.swing.gui.model.priority.PriorityModel;
import com.frizzer.swing.gui.view.CrudComponent;
import com.frizzer.swing.logic.repository.PriorityRepository;
import com.frizzer.swing.logic.tasks.priority.RemovePriorityTask;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Getter
@Component
@RequiredArgsConstructor
public class PriorityForm extends CrudComponent {

    private final UpsertPriorityForm upsertPriorityForm;
    private final PriorityModel priorityModel;
    private final PriorityRepository priorityRepository;
    private final EventSender eventSender;
    private final ApplicationEventPublisher applicationEventPublisher;

    private JList<Priority> priorityList;

    @Override
    protected void initComponents() {
        setTitle("Priority Form");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    protected void initButtons() {
        super.initButtons();
        createButton.addActionListener(e -> upsertPriorityForm.setVisible(true));
        editButton.addActionListener(this::openUpsertForEditPriority);
        deleteButton.addActionListener(this::deletePriority);
    }

    @Override
    protected void placeComponents() {
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));
        mainPanel.add(createPrioritiesList());

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(createButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(editButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(deleteButton);

        mainPanel.add(buttonPanel, 0);
        add(mainPanel);

        pack();
    }

    @Override
    protected JPanel createMainPanel() {
        return new JPanel();
    }

    @Override
    protected JPanel createButtonPanel() {
        return new JPanel();
    }

    @Override
    protected JButton createCreateButton() {
        JButton button = new JButton("Add");

        Dimension buttonSize = new Dimension(150, 30);

        button.setPreferredSize(buttonSize);
        button.setMaximumSize(buttonSize);

        return button;
    }

    @Override
    protected JButton createEditButton() {
        JButton button = new JButton("Edit");

        Dimension buttonSize = new Dimension(150, 30);

        button.setPreferredSize(buttonSize);
        button.setMaximumSize(buttonSize);

        return button;
    }

    @Override
    protected JButton createDeleteButton() {
        JButton button = new JButton("Delete");

        Dimension buttonSize = new Dimension(150, 30);
        button.setPreferredSize(buttonSize);
        button.setMaximumSize(buttonSize);

        return button;
    }

    private JScrollPane createPrioritiesList() {
        priorityList = new JList<>(priorityModel);
        JScrollPane scrollPane = new JScrollPane(priorityList);
        scrollPane.setPreferredSize(new Dimension(200, 200));

        return scrollPane;
    }

    private void deletePriority(ActionEvent e) {
        Priority priority = priorityList.getSelectedValue();
        new RemovePriorityTask(priorityRepository, priority, applicationEventPublisher).execute();

    }

    private void openUpsertForEditPriority(ActionEvent e) {
        var selectedPriority = priorityList.getSelectedValue();
        if (selectedPriority != null) {
            upsertPriorityForm.setSelectedPriority(selectedPriority);
            upsertPriorityForm.setVisible(true);
        }
    }

}
