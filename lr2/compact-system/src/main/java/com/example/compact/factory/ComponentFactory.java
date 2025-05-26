package com.example.compact.factory;

import java.util.List;

import com.example.compact.model.SystemComponent;

public interface ComponentFactory {
    SystemComponent createComponent(String id, String name, String manufacturer, List<String> compatibleIds);
}