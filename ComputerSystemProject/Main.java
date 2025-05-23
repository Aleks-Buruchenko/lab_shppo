import components.Component;
import factory.ComponentFactory;
import composite.ComponentGroup;
import builder.SystemBuilder;
import patterns.ConfigurationObserver;
import patterns.ConsoleConfigurationObserver;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Component> components = List.of(
            ComponentFactory.createComponent("CPU", "1", "Intel i5", "Intel"),
            ComponentFactory.createComponent("CPU", "2", "AMD Ryzen 5", "AMD"),
            ComponentFactory.createComponent("RAM", "3", "Kingston 8GB", "Kingston"),
            ComponentFactory.createComponent("RAM", "4", "Corsair 8GB", "Corsair"),
            ComponentFactory.createComponent("GPU", "5", "NVIDIA GTX 1650", "NVIDIA")
        );

        ConfigurationObserver observer = new ConsoleConfigurationObserver();

        int configId = 1;
        for (Component c1 : components) {
            for (Component c2 : components) {
                if (c1 == c2) continue;
                for (Component c3 : components) {
                    if (c3 == c1 || c3 == c2) continue;
                    SystemBuilder builder = new SystemBuilder();
                    builder.addComponent(c1);
                    builder.addComponent(c2);
                    builder.addComponent(c3);
                    ComponentGroup group = builder.build("CFG-" + configId++);
                    observer.onConfigurationGenerated(group);
                }
            }
        }
    }
}
