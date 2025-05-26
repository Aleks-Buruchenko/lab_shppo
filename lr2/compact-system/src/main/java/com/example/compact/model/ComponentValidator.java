package com.example.compact.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class ComponentValidator {
    public void validateComponents(List<SystemComponent> components) {
        if (components == null || components.isEmpty()) {
            throw new IllegalArgumentException("Components list cannot be null or empty");
        }

        Set<String> componentIds = components.stream()
                .map(SystemComponent::getId)
                .collect(Collectors.toSet());

        // Проверка на дубликаты ID
        if (componentIds.size() != components.size()) {
            throw new IllegalArgumentException("Duplicate component IDs detected");
        }

        for (SystemComponent component : components) {
            if (component.getId() == null || component.getId().isEmpty()) {
                throw new IllegalArgumentException("Component ID cannot be null or empty");
            }
            for (String compatibleId : component.getCompatibleIds()) {
                if (!componentIds.contains(compatibleId)) {
                    throw new IllegalArgumentException("Invalid compatible ID: " + compatibleId + " for component " + component.getId());
                }
            }
        }
    }
}