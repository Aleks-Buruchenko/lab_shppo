
import builder.SystemBuilder;
import components.Component;
import composite.ComponentGroup;
import factory.ComponentFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Component> components = new ArrayList<>();
        Set<String> uniqueConfigs = new HashSet<>();

        System.out.println("Введите компоненты (тип, ID, название, производитель, совместимые производители). Для завершения введите 'done':");
        while (true) {
            System.out.print("Тип (CPU/RAM/GPU) или 'done': ");
            String type = scanner.nextLine().trim();
            if (type.equalsIgnoreCase("done")) {
                break;
            }
            System.out.print("ID: ");
            String id = scanner.nextLine().trim();
            System.out.print("Название: ");
            String name = scanner.nextLine().trim();
            System.out.print("Производитель: ");
            String manufacturer = scanner.nextLine().trim();
            System.out.print("Совместимые производители (через запятую, например, Intel,Kingston): ");
            String compatibleInput = scanner.nextLine().trim();
            List<String> compatibleManufacturers = compatibleInput.isEmpty() ?
                List.of() : Arrays.stream(compatibleInput.split(","))
                                 .map(String::trim)
                                 .collect(Collectors.toList());

            try {
                Component component = ComponentFactory.createComponent(type, id, name, manufacturer, compatibleManufacturers);
                components.add(component);
                System.out.println("Добавлен компонент: " + name + ", Совместим с: " + compatibleManufacturers);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        int configId = 1;
        List<ComponentGroup> configurations = new ArrayList<>();
        for (Component c1 : components) {
            for (Component c2 : components) {
                if (c1 == c2) continue;
                for (Component c3 : components) {
                    if (c3 == c1 || c3 == c2) continue;
                    Component[][] permutations = {
                        {c1, c2, c3},
                        {c1, c3, c2},
                        {c2, c1, c3},
                        {c2, c3, c1},
                        {c3, c1, c2},
                        {c3, c2, c1}
                    };
                    for (Component[] perm : permutations) {
                        System.out.println("Попытка создать конфигурацию: " +
                            perm[0].getName() + ", " + perm[1].getName() + ", " + perm[2].getName());
                        SystemBuilder builder = new SystemBuilder();
                        builder.addComponent(perm[0]);
                        builder.addComponent(perm[1]);
                        builder.addComponent(perm[2]);
                        ComponentGroup group = builder.build("CFG-" + configId++);
                        if (!group.isValidConfiguration()) {
                            System.out.println("Конфигурация отклонена: не валидная (не 1 CPU, 1 RAM, 1 GPU)");
                            continue;
                        }
                        String configKey = generateConfigKey(group);
                        if (uniqueConfigs.add(configKey)) {
                            configurations.add(group);
                        }
                    }
                }
            }
        }

        System.out.println("\n--- Все сгенерированные конфигурации ---");
        if (configurations.isEmpty()) {
            System.out.println("Нет валидных конфигураций.");
        } else {
            for (ComponentGroup group : configurations) {
                group.printInfo();
            }
        }

        scanner.close();
    }

    private static String generateConfigKey(ComponentGroup group) {
        List<String> ids = new ArrayList<>();
        for (Component c : group) {
            ids.add(c.getId());
        }
        ids.sort(String::compareTo);
        return String.join(",", ids);
    }
}