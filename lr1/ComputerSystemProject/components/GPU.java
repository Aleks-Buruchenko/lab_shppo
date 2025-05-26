package components;

import patterns.ComponentVisitor;

import java.util.List;

public class GPU extends BaseComponent {
    public GPU(String id, String name, String manufacturer, List<String> compatibleManufacturers) {
        super(id, "GPU", name, manufacturer, compatibleManufacturers);
    }

    @Override
    public boolean isCompatibleWith(Component other) {
        if (other instanceof GPU) {
            return false; // Нельзя иметь два GPU
        }
        // Проверяем, есть ли производитель другого компонента в списке совместимости
        return compatibleManufacturers.contains(other.getManufacturer()) &&
               other.getCompatibleManufacturers().contains(this.manufacturer);
    }

    @Override
    public void accept(ComponentVisitor visitor, Component other) {
        visitor.visit(this, other);
    }
}