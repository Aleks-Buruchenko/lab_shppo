package com.example.compact.combinator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.compact.components.ComponentValidator;
import com.example.compact.components.ComputingSystem;
import com.example.compact.components.SystemComponent;

@Component
@Scope("singleton")
public class ConfigurationGenerator {
    private final CompatibilityStrategy strategy;
    private final ComponentValidator validator;
    private final ApplicationContext context;

    @Autowired
    public ConfigurationGenerator(CompatibilityStrategy strategy, ComponentValidator validator, ApplicationContext context) {
        this.strategy = strategy;
        this.validator = validator;
        this.context = context;
    }

    public List<ComputingSystem> generateConfigurations(List<SystemComponent> components) throws InterruptedException {
        // Проверяем корректность компонентов
        validator.validateComponents(components);

        List<ComputingSystem> validConfigurations = new CopyOnWriteArrayList<>();
        List<SystemComponent> cpus = new ArrayList<>();
        List<SystemComponent> rams = new ArrayList<>();
        List<SystemComponent> gpus = new ArrayList<>();

        // Разделяем компоненты по типам
        for (SystemComponent c : components) {
            if (c.getType().equals("CPU")) cpus.add(c);
            else if (c.getType().equals("RAM")) rams.add(c);
            else if (c.getType().equals("GPU")) gpus.add(c);
        }

        // Создаем пул потоков
        int threadCount = Math.min(cpus.size(), Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        // Разделяем CPU по потокам
        int chunkSize = Math.max(1, cpus.size() / threadCount);
        for (int i = 0; i < cpus.size(); i += chunkSize) {
            List<SystemComponent> cpuChunk = cpus.subList(i, Math.min(i + chunkSize, cpus.size()));
            executor.submit(() -> {
                for (SystemComponent cpu : cpuChunk) {
                    for (SystemComponent ram : rams) {
                        for (SystemComponent gpu : gpus) {
                            if (isCompatibleTriple(cpu, ram, gpu)) {
                                // Получаем новый экземпляр ComputingSystem из контекста
                                ComputingSystem system = context.getBean(ComputingSystem.class);
                                system.addComponent(cpu);
                                system.addComponent(ram);
                                system.addComponent(gpu);
                                validConfigurations.add(system);
                            }
                        }
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        return validConfigurations;
    }

    private boolean isCompatibleTriple(SystemComponent cpu, SystemComponent ram, SystemComponent gpu) {
        return strategy.checkCompatibility(cpu, ram) &&
               strategy.checkCompatibility(cpu, gpu) &&
               strategy.checkCompatibility(ram, gpu);
    }
}