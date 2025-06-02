package com.example.compact.combinator;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ConfigurationGenerator(CompatibilityStrategy strategy) {
        this.strategy = strategy;
    }

    public List<ComputingSystem> generateConfigurations(List<SystemComponent> components) {
        // Разделяем компоненты на CPU, RAM, GPU
        List<SystemComponent> cpus = components.stream().filter(c -> c instanceof CPU).collect(Collectors.toList());
        List<SystemComponent> rams = components.stream().filter(c -> c instanceof RAM).collect(Collectors.toList());
        List<SystemComponent> gpus = components.stream().filter(c -> c instanceof GPU).collect(Collectors.toList());

        // Создаем очередь с достаточной емкостью
        CustomQueue<List<SystemComponent>> taskQueue = new CustomQueue<>(cpus.size() * rams.size() * gpus.size());
        List<ComputingSystem> validConfigurations = new CopyOnWriteArrayList<>();

        // Производитель: заполняем очередь комбинациями
        Thread producer = new Thread(() -> {
            try {
                for (SystemComponent cpu : cpus) {
                    for (SystemComponent ram : rams) {
                        for (SystemComponent gpu : gpus) {
                            taskQueue.put(List.of(cpu, ram, gpu));
                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Потребители: обрабатываем комбинации
        int threadCount = Math.min(cpus.size(), Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    while (!taskQueue.isEmpty() || producer.isAlive()) {
                        List<SystemComponent> combination = null;
                        synchronized (taskQueue) {
                            if (!taskQueue.isEmpty()) {
                                combination = taskQueue.take();
                            }
                        }
                        if (combination != null) {
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
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Запускаем производителя
        producer.start();

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
        return strategy.checkCompatibility(cpu, ram) &&
               strategy.checkCompatibility(cpu, gpu) &&
               strategy.checkCompatibility(ram, gpu);
    }
}