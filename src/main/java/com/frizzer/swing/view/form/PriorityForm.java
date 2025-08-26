package com.frizzer.swing.view.form;

import com.frizzer.swing.entity.Priority;
import com.frizzer.swing.registry.ListRegistry;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

@Getter
@Component
public class PriorityForm extends JFrame {

    private final AddPriorityForm addPriorityForm;
    private JPanel mainPanel;
    private JList<String> priorityList;
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
        mainPanel.add(updatePriorityList());
    }

    public JScrollPane updatePriorityList() {

        List<String> names = Collections.list(priorityModel.elements()).stream().map(Priority::getName).toList();
        DefaultListModel<String> model = new DefaultListModel<>();
        model.addAll(names);
        priorityList = new JList<>(model);

        JScrollPane scrollPane = new JScrollPane(priorityList);
        scrollPane.setPreferredSize(new Dimension(200, 300));

        return scrollPane;
    }

    public void addToPriorityList(List<String> names) {
        SwingUtilities.invokeLater(() -> ((DefaultListModel<String>) priorityList.getModel()).addAll(names));
    }

    private JButton addPriorityButton() {
        addPriorityButton = new JButton("Add Priority");
        return addPriorityButton;
    }

}
