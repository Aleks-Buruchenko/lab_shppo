package patterns;

import components.Component;

public interface CompatibilityStrategy {
    boolean isCompatible(Component a, Component b);
}