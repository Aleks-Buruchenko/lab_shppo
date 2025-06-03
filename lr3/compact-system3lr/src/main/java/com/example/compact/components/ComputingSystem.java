package com.example.compact.components;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ComputingSystem {
    private final List<SystemComponent> components = new ArrayList<>();

    public void addComponent(SystemComponent component) {
        components.add(component);
    }

    public List<SystemComponent> getComponents() {
        return new ArrayList<>(components);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ComputingSystem[");
        for (int i = 0; i < components.size(); i++) {
            sb.append(components.get(i).getId());
            if (i < components.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}