package com.example.compact.model;

import java.util.List;

public class RAM extends SystemComponent {
    public RAM(String id, String name, String manufacturer, List<String> compatibleIds) {
        super(id, "RAM", name, manufacturer, compatibleIds);
    }
}