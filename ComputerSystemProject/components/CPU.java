package components;

public class CPU extends BaseComponent {
    public CPU(String id, String name, String manufacturer) {
        super(id, "CPU", name, manufacturer);
    }

    @Override
    public boolean isCompatibleWith(Component other) {
        return !(other instanceof CPU);
    }
}