package com.example.compact;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.compact.combinator.ConfigurationGenerator;
import com.example.compact.components.ComponentFactory;
import com.example.compact.components.ComputingSystem;
import com.example.compact.components.SystemComponent;
import com.example.compact.config.AppConfig;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ComponentFactory cpuFactory = context.getBean("CPUFactory", ComponentFactory.class);
        ComponentFactory ramFactory = context.getBean("RAMFactory", ComponentFactory.class);
        ComponentFactory gpuFactory = context.getBean("GPUFactory", ComponentFactory.class);
        ConfigurationGenerator generator = context.getBean(ConfigurationGenerator.class);

        // Создаем компоненты
        List<SystemComponent> components = Arrays.asList(
            cpuFactory.createComponent("CPU001", "Intel i5", "Intel", Arrays.asList("RAM001", "RAM002", "GPU001")),
            cpuFactory.createComponent("CPU002", "AMD Ryzen 5", "AMD", Arrays.asList("RAM001", "GPU002")),
            ramFactory.createComponent("RAM001", "8GB DDR4", "Kingston", Arrays.asList("CPU001", "CPU002", "GPU001", "GPU002")),
            ramFactory.createComponent("RAM002", "16GB DDR4", "Corsair", Arrays.asList("CPU001", "GPU001")),
            gpuFactory.createComponent("GPU001", "NVIDIA GTX 1660", "NVIDIA", Arrays.asList("CPU001", "RAM001", "RAM002")),
            gpuFactory.createComponent("GPU002", "AMD Radeon RX 580", "AMD", Arrays.asList("CPU002", "RAM001"))
        );

        // Генерируем и выводим все конфигурации
        try {
            List<ComputingSystem> configurations = generator.generateConfigurations(components);
            if (configurations.isEmpty()) {
                System.out.println("No compatible configurations found!");
            } else {
                System.out.println("Found " + configurations.size() + " compatible configurations:");
                for (int i = 0; i < configurations.size(); i++) {
                    System.out.println("\nConfiguration " + (i + 1) + ":");
                    configurations.get(i).display();
                }
            }
        } catch (InterruptedException e) {
            System.err.println("Configuration generation interrupted: " + e.getMessage());
        }

        context.close();
    }
}