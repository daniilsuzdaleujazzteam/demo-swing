package com.frizzer.swing.config.registry;

import org.springframework.stereotype.Component;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TableRegistry {

    private final Map<String, TableModel> taskMap = new ConcurrentHashMap<>();

    public void register(String id, TableModel model) {
        taskMap.put(id, model);
    }

    public TableModel get(String id) {
        return taskMap.get(id) != null ? taskMap.get(id) : new DefaultTableModel();
    }

}
