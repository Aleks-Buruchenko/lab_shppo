package composite;

import components.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import patterns.ComponentVisitor;

public class ComponentGroup implements Component, Iterable<Component> {
    private final String id;
    private final List<Component> components = new ArrayList<>();

    public ComponentGroup(String id) {
        this.id = id;
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    public int getComponentCount() {
        return components.size();
    }

    public boolean isValidConfiguration() {
        if (components.size() != 3) {
            return false;
        }
        int cpuCount = 0, ramCount = 0, gpuCount = 0;
        for (Component c : components) {
            switch (c.getType()) {
                case "CPU":
                    cpuCount++;
                    break;
                case "RAM":
                    ramCount++;
                    break;
                case "GPU":
                    gpuCount++;
                    break;
            }
        }
        return cpuCount == 1 && ramCount == 1 && gpuCount == 1;
    }

    @Override
    public String getId() { return id; }
    @Override
    public String getType() { return "Group"; }
    @Override
    public String getName() { return "Configuration"; }
    @Override
    public String getManufacturer() { return "-"; }
    @Override
    public List<String> getCompatibleManufacturers() { return List.of(); }
    @Override
    public boolean isCompatibleWith(Component other) {
        for (Component c : components) {
            if (!c.isCompatibleWith(other)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void printInfo() {
        System.out.println("\n[ Configuration: " + id + " ]");
        for (Component c : components) {
            c.printInfo();
        }
    }

    @Override
    public Iterator<Component> iterator() {
        return components.iterator();
    }

    @Override
    public void accept(ComponentVisitor visitor, Component other) {
        visitor.visit(this, other);
    }
}