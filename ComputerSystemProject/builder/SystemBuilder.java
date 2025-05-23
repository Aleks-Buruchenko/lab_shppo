package builder;

import components.Component;
import composite.ComponentGroup;
import patterns.CompatibilityStrategy;
import patterns.DefaultCompatibilityStrategy;

import java.util.ArrayList;
import java.util.List;

public class SystemBuilder {
    private final List<Component> components = new ArrayList<>();
    private final CompatibilityStrategy strategy = new DefaultCompatibilityStrategy();

    public void addComponent(Component component) {
        for (Component existing : components) {
            if (!strategy.isCompatible(existing, component)) return;
        }
        components.add(component);
    }

    public ComponentGroup build(String id) {
        ComponentGroup group = new ComponentGroup(id);
        components.forEach(group::addComponent);
        return group;
    }
}