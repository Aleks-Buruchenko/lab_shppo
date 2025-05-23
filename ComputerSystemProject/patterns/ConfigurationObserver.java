package patterns;

import composite.ComponentGroup;

public interface ConfigurationObserver {
    void onConfigurationGenerated(ComponentGroup group);
}