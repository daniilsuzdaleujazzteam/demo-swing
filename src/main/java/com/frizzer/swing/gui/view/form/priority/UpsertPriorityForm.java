package com.frizzer.swing.gui.view.form.priority;

import com.frizzer.swing.domain.Priority;
import com.frizzer.swing.gui.event.sender.EventSender;
import com.frizzer.swing.gui.model.priority.PriorityModel;
import com.frizzer.swing.gui.view.UpsertComponent;
import com.frizzer.swing.logic.repository.PriorityRepository;
import com.frizzer.swing.logic.tasks.priority.UpsertPriorityTask;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Getter
@Component
@RequiredArgsConstructor
public class UpsertPriorityForm extends UpsertComponent {

    private final PriorityRepository priorityRepository;
    private final EventSender eventSender;
    private final PriorityModel priorityModel;

    private JTextField nameField;
    private JTextField weightField;
    private JPanel namePanel;
    private JPanel weightPanel;
    private Priority selectedPriority;

    public void setSelectedPriority(Priority priority) {
        this.selectedPriority = priority;
        nameField.setText(priority.getName());
        weightField.setText(String.valueOf(priority.getWeight()));
    }

    @Override
    protected void initComponents() {
        setTitle("Modify priority");
        namePanel = createNamePanel();
        weightPanel = createWeightPanel();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    protected void initButtons() {
        super.initButtons();
        saveButton.addActionListener(this::upsertPriority);
    }


    @Override
    protected void placeComponents() {
        mainPanel.add(namePanel);
        mainPanel.add(weightPanel);

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
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
    protected JButton createSaveButton() {
        return new JButton("Save");
    }

    @Override
    protected JButton createCancelButton() {
        return new JButton("Cancel");
    }

    private JPanel createNamePanel() {
        JPanel panel = new JPanel();

        JLabel label = new JLabel("Priority name:");
        nameField = new JTextField(20);

        panel.add(label);
        panel.add(nameField);

        return panel;
    }

    private JPanel createWeightPanel() {
        JPanel panel = new JPanel();

        JLabel label = new JLabel("Priority weight:");
        weightField = new JTextField(20);

        panel.add(label);
        panel.add(weightField);

        return panel;
    }

    private void upsertPriority(ActionEvent e) {
        new UpsertPriorityTask(priorityRepository,
                buildPriority(),
                priority -> SwingUtilities.invokeLater(() -> priorityModel.upsert(priority))).execute();
        dispose();
    }

    private Priority buildPriority() {
        return new Priority(selectedPriority == null ? null : selectedPriority.getId(),
                Long.parseLong(weightField.getText()),
                nameField.getText());
    }
}
