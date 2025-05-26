package builder;

import components.Component;
import composite.ComponentGroup;
import java.util.ArrayList;
import java.util.List;
import patterns.CompatibilityVisitor;
import proxy.ComponentProxy;

public class SystemBuilder {
    private final List<Component> components = new ArrayList<>();
    private final CompatibilityVisitor compatibilityVisitor = new CompatibilityVisitor();

    public void addComponent(Component component) {
        Component proxy = new ComponentProxy(component);
        boolean isCompatible = true;

        for (Component existing : components) {
            proxy.accept(compatibilityVisitor, existing);
            if (!compatibilityVisitor.isCompatible()) {
                System.out.println("Component " + proxy.getName() + " not compatible with " + existing.getName());
                isCompatible = false;
                break;
            }
        }

        if (isCompatible) {
            System.out.println("Adding component: " + proxy.getName());
            components.add(proxy);
        }
    }

    public ComponentGroup build(String id) {
        ComponentGroup group = new ComponentGroup(id);
        components.forEach(group::addComponent);
        components.clear();
        return group;
    }
}