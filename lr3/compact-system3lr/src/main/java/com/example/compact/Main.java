package com.example.compact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.compact.combinator.ConfigurationGenerator;
import com.example.compact.components.CPU;
import com.example.compact.components.ComputingSystem;
import com.example.compact.components.GPU;
import com.example.compact.components.RAM;
import com.example.compact.components.SystemComponent;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example.compact");
        ConfigurationGenerator generator = context.getBean(ConfigurationGenerator.class);

        List<SystemComponent> components = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите компоненты (тип, ID, название, производитель, совместимые ID). Для завершения введите 'done':");
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
            System.out.print("Совместимые ID (через запятую, например, CPU001,CPU002): ");
            String compatibleInput = scanner.nextLine().trim();
            List<String> compatibleIds = compatibleInput.isEmpty() ? 
                new ArrayList<>() : Arrays.asList(compatibleInput.split("\\s*,\\s*"));

            SystemComponent component;
            switch (type.toUpperCase()) {
                case "CPU":
                    component = new CPU(id, name, manufacturer, compatibleIds);
                    break;
                case "RAM":
                    component = new RAM(id, name, manufacturer, compatibleIds);
                    break;
                case "GPU":
                    component = new GPU(id, name, manufacturer, compatibleIds);
                    break;
                default:
                    continue;
            }
            components.add(component);
        }
        scanner.close();

        List<ComputingSystem> systems = generator.generateConfigurations(components);
        generator.printConfigurations(systems);
    }
}