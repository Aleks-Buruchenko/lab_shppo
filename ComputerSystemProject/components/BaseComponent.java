package components;

public abstract class BaseComponent implements Component {
    protected String id, type, name, manufacturer;

    public BaseComponent(String id, String type, String name, String manufacturer) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.manufacturer = manufacturer;
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
    public void printInfo() {
        System.out.println(type + ": " + name + " (" + manufacturer + ")");
    }
}