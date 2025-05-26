package factory;

import components.*;

import java.util.List;

public class ComponentFactory {
    public static Component createComponent(String type, String id, String name, String manufacturer, List<String> compatibleManufacturers) {
        switch (type.toUpperCase()) {
            case "CPU":
                return new CPU(id, name, manufacturer, compatibleManufacturers);
            case "RAM":
                return new RAM(id, name, manufacturer, compatibleManufacturers);
            case "GPU":
                return new GPU(id, name, manufacturer, compatibleManufacturers);
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}