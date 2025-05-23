package patterns;

import composite.ComponentGroup;

public class ConsoleConfigurationObserver implements ConfigurationObserver {
    public void onConfigurationGenerated(ComponentGroup group) {
        System.out.println("\n--- New configuration generated ---");
        group.printInfo();
    }
}