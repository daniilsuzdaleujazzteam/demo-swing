package com.frizzer.swing.config;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGenerator {
    public static final String INSTANCE_ID = UUID.randomUUID().toString();
    private IdGenerator() {}
}
