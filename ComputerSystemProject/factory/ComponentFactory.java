package factory;

import components.*;

public class ComponentFactory {
    public static Component createComponent(String type, String id, String name, String manufacturer) {
        switch (type) {
            case "CPU" -> {
                return new CPU(id, name, manufacturer);
            }
            case "RAM" -> {
                return new RAM(id, name, manufacturer);
            }
            case "GPU" -> {
                return new GPU(id, name, manufacturer);
            }
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}
