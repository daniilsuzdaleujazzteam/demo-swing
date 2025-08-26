package com.frizzer.swing.config.registry;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class ListRegistry<T> implements Serializable {
    private final Map<String, ListModel<T>> itemList = new ConcurrentHashMap<>();

    public void register(String id, ListModel<T> item) {
        itemList.put(id, item);
    }

    public ListModel<T> get(String id) {
        return itemList.get(id) != null ? itemList.get(id) : null;
    }
}
