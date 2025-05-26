package com.example.compact.model;

import java.util.List;

public class CPU extends SystemComponent {
    public CPU(String id, String name, String manufacturer, List<String> compatibleIds) {
        super(id, "CPU", name, manufacturer, compatibleIds);
    }
}