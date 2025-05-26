package components;

import java.util.List;
import patterns.ComponentVisitor;

public class CPU extends BaseComponent {
    public CPU(String id, String name, String manufacturer, List<String> compatibleManufacturers) {
        super(id, "CPU", name, manufacturer, compatibleManufacturers);
    }

    @Override
    public boolean isCompatibleWith(Component other) {
        if (other instanceof CPU) {
            return false; // Нельзя иметь два CPU
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