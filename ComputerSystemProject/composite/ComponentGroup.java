package composite;

import components.Component;
import java.util.ArrayList;
import java.util.List;

public class ComponentGroup implements Component {
    private final String id;
    private final List<Component> components = new ArrayList<>();

    public ComponentGroup(String id) {
        this.id = id;
    }

    public void addComponent(Component component) {
        components.add(component);
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
    public boolean isCompatibleWith(Component other) {
        for (Component c : components)
            if (!c.isCompatibleWith(other)) return false;
        return true;
    }
    @Override
    public void printInfo() {
        System.out.println("\n[ Configuration: " + id + " ]");
        components.forEach(Component::printInfo);
    }
}