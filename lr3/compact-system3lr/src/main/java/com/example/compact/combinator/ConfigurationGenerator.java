package com.example.compact.combinator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.example.compact.components.CPU;
import com.example.compact.components.ComputingSystem;
import com.example.compact.components.GPU;
import com.example.compact.components.RAM;
import com.example.compact.components.SystemComponent;
import com.example.compact.util.CustomQueue;

@Component
public class ConfigurationGenerator {
    private final CompatibilityStrategy strategy;
    private static final int THREAD_COUNT = 2;

    @Autowired
    public ConfigurationGenerator(@Qualifier("defaultCompatibilityStrategy") CompatibilityStrategy strategy) {
        this.strategy = strategy;
    }

    public List<ComputingSystem> generateConfigurations(List<SystemComponent> components) {
        // Разделяем компоненты на CPU, RAM, GPU
        List<SystemComponent> cpus = new ArrayList<>();
        List<SystemComponent> rams = new ArrayList<>();
        List<SystemComponent> gpus = new ArrayList<>();

        for (SystemComponent component : components) {
            if (component instanceof CPU) {
                cpus.add(component);
            } else if (component instanceof RAM) {
                rams.add(component);
            } else if (component instanceof GPU) {
                gpus.add(component);
            }
        }

        // Создаем очередь с достаточной емкостью
        CustomQueue<List<SystemComponent>> taskQueue = new CustomQueue<>(cpus.size() * rams.size() * gpus.size());
        List<ComputingSystem> validConfigurations = new CopyOnWriteArrayList<>();

        // Производитель
        Thread producer = new Thread(() -> {
            try {
                for (SystemComponent cpu : cpus) {
                    for (SystemComponent ram : rams) {
                        for (SystemComponent gpu : gpus) {
                            taskQueue.put(List.of(cpu, ram, gpu));
                        }
                    }
                }
                taskQueue.setProducerDone(); // Сигнализируем о завершении
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();

        // Потребители
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                try {
                    List<SystemComponent> combination;
                    while ((combination = taskQueue.take()) != null) {
                        SystemComponent cpu = combination.get(0);
                        SystemComponent ram = combination.get(1);
                        SystemComponent gpu = combination.get(2);
                        if (isCompatibleTriple(cpu, ram, gpu)) {
                            ComputingSystem system = new ComputingSystem();
                            system.addComponent(cpu);
                            system.addComponent(ram);
                            system.addComponent(gpu);
                            validConfigurations.add(system);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Ждем завершения
        try {
            producer.join();
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return validConfigurations;
    }

    private boolean isCompatibleTriple(SystemComponent cpu, SystemComponent ram, SystemComponent gpu) {
        return strategy.checkCompatibility(cpu, ram) && strategy.checkCompatibility(cpu, gpu);
    }
}