package com.example.compact.components;

import java.util.List;

public interface ComponentFactory {
    SystemComponent createComponent(String id, String name, String manufacturer, List<String> compatibleIds);
}