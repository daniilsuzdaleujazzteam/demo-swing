package com.frizzer.swing.view.form;

import com.frizzer.swing.entity.Priority;
import com.frizzer.swing.registry.ListRegistry;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Getter
@Component
public class PriorityForm extends JFrame {

    private final AddPriorityForm addPriorityForm;
    private JPanel mainPanel;
    private JList<Priority> priorityList;
    private JButton addPriorityButton;
    private final DefaultListModel<Priority> priorityModel;
    private final ListRegistry<Priority> priorityRegistry;

    public PriorityForm(AddPriorityForm addPriorityForm,
                        DefaultListModel<Priority> priorityModel,
                        ListRegistry<Priority> priorityRegistry) {
        this.addPriorityForm = addPriorityForm;
        this.priorityModel = priorityModel;
        this.priorityRegistry = priorityRegistry;

        setTitle("Priority Form");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
        add(mainPanel);
        pack();
    }

    public void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));
        mainPanel.add(addPriorityButton());
        mainPanel.add(refreshPrioritiesList());
    }

    public JScrollPane refreshPrioritiesList() {
        priorityList = new JList<>(priorityModel);

        JScrollPane scrollPane = new JScrollPane(priorityList);
        scrollPane.setPreferredSize(new Dimension(200, 300));

        return scrollPane;
    }

    public void addToPriorityList(List<Priority> priorities) {
        SwingUtilities.invokeLater(() -> {
            ((DefaultListModel<Priority>) priorityList.getModel()).addAll(priorities);
            refreshPrioritiesList();
        });
    }

    private JButton addPriorityButton() {
        addPriorityButton = new JButton("Add Priority");
        return addPriorityButton;
    }

}
