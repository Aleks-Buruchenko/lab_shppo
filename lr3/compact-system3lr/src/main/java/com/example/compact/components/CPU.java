package com.example.compact.components;

import java.util.List;

public class CPU extends SystemComponent {
    public CPU(String id, String name, String manufacturer, List<String> compatibleIds) {
        super(id, "CPU", name, manufacturer, compatibleIds);
    }
}