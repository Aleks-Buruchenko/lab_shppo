package components;

import java.util.List;

public abstract class BaseComponent implements Component {
    protected String id, type, name, manufacturer;
    protected List<String> compatibleManufacturers;

    public BaseComponent(String id, String type, String name, String manufacturer, List<String> compatibleManufacturers) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.manufacturer = manufacturer;
        this.compatibleManufacturers = compatibleManufacturers;
    }

    @Override
    public String getId() { return id; }
    @Override
    public String getType() { return type; }
    @Override
    public String getName() { return name; }
    @Override
    public String getManufacturer() { return manufacturer; }
    @Override
    public List<String> getCompatibleManufacturers() { return compatibleManufacturers; }

    @Override
    public void printInfo() {
        System.out.println(type + ": " + name + " (" + manufacturer + "), Совместим с: " + compatibleManufacturers);
    }
}