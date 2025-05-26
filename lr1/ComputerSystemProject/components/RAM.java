package components;

import java.util.List;
import patterns.ComponentVisitor;

public class RAM extends BaseComponent {
    public RAM(String id, String name, String manufacturer, List<String> compatibleManufacturers) {
        super(id, "RAM", name, manufacturer, compatibleManufacturers);
    }

    @Override
    public boolean isCompatibleWith(Component other) {
        if (other instanceof RAM) {
            return false; // Нельзя иметь два RAM
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