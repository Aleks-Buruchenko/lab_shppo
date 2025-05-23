package patterns;

import components.Component;

public class DefaultCompatibilityStrategy implements CompatibilityStrategy {
    public boolean isCompatible(Component a, Component b) {
        return a.isCompatibleWith(b) && b.isCompatibleWith(a);
    }
}