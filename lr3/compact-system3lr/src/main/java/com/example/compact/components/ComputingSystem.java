package com.example.compact.components;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ComputingSystem {
    private final List<SystemComponent> components;

    public ComputingSystem() {
        this.components = new ArrayList<>();
    }

    public void addComponent(SystemComponent component) {
        components.add(component);
    }

    public void display() {
        System.out.println("System Configuration:");
        for (SystemComponent component : components) {
            System.out.println(component.getType() + ": " + component.getName() + " (" + component.getManufacturer() + ")");
        }
    }
}