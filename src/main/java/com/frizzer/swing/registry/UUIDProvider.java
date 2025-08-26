package com.frizzer.swing.registry;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Getter
public class UUIDProvider {
    private final String id = UUID.randomUUID().toString();
}
