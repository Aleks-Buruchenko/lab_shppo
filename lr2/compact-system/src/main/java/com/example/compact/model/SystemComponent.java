package com.example.compact.model;

import java.util.List;

public abstract class SystemComponent {
    private String id, type, name, manufacturer;
    private List<String> compatibleIds;

    public SystemComponent(String id, String type, String name, String manufacturer, List<String> compatibleIds) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.manufacturer = manufacturer;
        this.compatibleIds = compatibleIds;
    }

    public String getId() { return id; }
    public String getType() { return type; }
    public String getName() { return name; }
    public String getManufacturer() { return manufacturer; }
    public List<String> getCompatibleIds() { return compatibleIds; }
}