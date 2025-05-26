package patterns;

import components.Component;

public interface ComponentVisitor {
    void visit(Component a, Component b);
    boolean isCompatible();
}