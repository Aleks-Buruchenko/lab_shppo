package components;

public interface Component {
    String getId();
    String getType();
    String getName();
    String getManufacturer();
    boolean isCompatibleWith(Component other);
    void printInfo();
}