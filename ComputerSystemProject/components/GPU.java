package components;

public class GPU extends BaseComponent {
    public GPU(String id, String name, String manufacturer) {
        super(id, "GPU", name, manufacturer);
    }
    @Override
    public boolean isCompatibleWith(Component other) {
        return !(other instanceof GPU);
    }
}