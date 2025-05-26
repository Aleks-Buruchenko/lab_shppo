package patterns;

import components.Component;

public class CompatibilityVisitor implements ComponentVisitor {
    private boolean isCompatible = true;

    @Override
    public void visit(Component a, Component b) {
        isCompatible = a.isCompatibleWith(b) && b.isCompatibleWith(a);
    }

    @Override
    public boolean isCompatible() {
        return isCompatible;
    }
}