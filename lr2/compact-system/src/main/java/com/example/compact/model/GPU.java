package com.example.compact.model;

import java.util.List;

public class GPU extends SystemComponent {
    public GPU(String id, String name, String manufacturer, List<String> compatibleIds) {
        super(id, "GPU", name, manufacturer, compatibleIds);
    }
}