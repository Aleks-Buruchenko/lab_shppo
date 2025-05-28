package com.example.compact.combinator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

    public List<ComputingSystem> generateConfigurations(List<SystemComponent> components) {
        validator.validateComponents(components);

        List<SystemComponent> cpus = new ArrayList<>();
        List<SystemComponent> rams = new ArrayList<>();
        List<SystemComponent> gpus = new ArrayList<>();

        for (SystemComponent c : components) {
            if (c.getType().equals("CPU")) cpus.add(c);
            else if (c.getType().equals("RAM")) rams.add(c);
            else if (c.getType().equals("GPU")) gpus.add(c);
        }

        int queueCapacity = 100;
        BlockingQueue<Triple> queue = new ArrayBlockingQueue<>(queueCapacity);

        List<ComputingSystem> validConfigurations = Collections.synchronizedList(new ArrayList<>());

        AtomicInteger processedCombinations = new AtomicInteger(0);
        int totalCombinations = cpus.size() * rams.size() * gpus.size();

        int consumerCount = Math.min(totalCombinations, Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(1 + consumerCount);

        try {
            executor.submit(() -> {
                try {
                    for (SystemComponent cpu : cpus) {
                        for (SystemComponent ram : rams) {
                            for (SystemComponent gpu : gpus) {
                                Triple triple = new Triple(cpu, ram, gpu);
                                queue.put(triple);
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    for (int i = 0; i < consumerCount; i++) {
                        try {
                            queue.put(new Triple(null, null, null));
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            });

            for (int i = 0; i < consumerCount; i++) {
                executor.submit(() -> {
                    try {
                        while (true) {
                            Triple triple = queue.take();
                            if (triple.getCpu() == null) {
                                break;
                            }

                            int processed = processedCombinations.incrementAndGet();
                            if (processed % 10 == 0 || processed == totalCombinations) {
                                System.out.println("Processed " + processed + "/" + totalCombinations + " combinations");
                            }

                            if (isCompatibleTriple(triple.getCpu(), triple.getRam(), triple.getGpu())) {
                                ComputingSystem system = context.getBean(ComputingSystem.class);
                                system.addComponent(triple.getCpu());
                                system.addComponent(triple.getRam());
                                system.addComponent(triple.getGpu());
                                validConfigurations.add(system);
                            }
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while generating configurations", e);
        }

        return validConfigurations;
    }

    private boolean isCompatibleTriple(SystemComponent cpu, SystemComponent ram, SystemComponent gpu) {
        return strategy.checkCompatibility(cpu, ram) &&
               strategy.checkCompatibility(cpu, gpu) &&
               strategy.checkCompatibility(ram, gpu);
    }
}