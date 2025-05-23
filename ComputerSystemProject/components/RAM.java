package components;

public class RAM extends BaseComponent {
    public RAM(String id, String name, String manufacturer) {
        super(id, "RAM", name, manufacturer);
    }

    @Override
    public boolean isCompatibleWith(Component other) {
        return !(other instanceof RAM);
    }
}