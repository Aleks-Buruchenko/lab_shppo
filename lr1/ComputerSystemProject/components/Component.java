package components;

import java.util.List;
import patterns.ComponentVisitor;

public interface Component {
    String getId();
    String getType();
    String getName();
    String getManufacturer();
    List<String> getCompatibleManufacturers();
    boolean isCompatibleWith(Component other);
    void printInfo();
    void accept(ComponentVisitor visitor, Component other);
}