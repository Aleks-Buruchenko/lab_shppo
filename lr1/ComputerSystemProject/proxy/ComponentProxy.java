package proxy;

import components.Component;
import patterns.ComponentVisitor;

import java.util.List;

public class ComponentProxy implements Component {
    private final Component component;

    public ComponentProxy(Component component) {
        if (component.getId() == null || component.getId().isEmpty()) {
            throw new IllegalArgumentException("Component ID cannot be empty");
        }
        if (component.getCompatibleManufacturers().isEmpty()) {
            throw new IllegalArgumentException("Component must have at least one compatible manufacturer");
        }
        this.component = component;
        System.out.println("Proxy: Adding component " + component.getType() + " (" + component.getName() + ")");
    }

    @Override
    public String getId() {
        return component.getId();
    }

    @Override
    public String getType() {
        return component.getType();
    }

    @Override
    public String getName() {
        return component.getName();
    }

    @Override
    public String getManufacturer() {
        return component.getManufacturer();
    }

    @Override
    public List<String> getCompatibleManufacturers() {
        return component.getCompatibleManufacturers();
    }

    @Override
    public boolean isCompatibleWith(Component other) {
        return component.isCompatibleWith(other);
    }

    @Override
    public void printInfo() {
        component.printInfo();
    }

    @Override
    public void accept(ComponentVisitor visitor, Component other) {
        component.accept(visitor, other);
    }
}