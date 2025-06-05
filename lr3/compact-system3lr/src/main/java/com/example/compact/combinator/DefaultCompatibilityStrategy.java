package com.example.compact.combinator;

import org.springframework.stereotype.Component;

import com.example.compact.components.CPU;
import com.example.compact.components.GPU;
import com.example.compact.components.RAM;
import com.example.compact.components.SystemComponent;

@Component
public class DefaultCompatibilityStrategy implements CompatibilityStrategy {
    @Override
    public boolean checkCompatibility(SystemComponent c1, SystemComponent c2) {
        // CPU с RAM или GPU
        if (c1 instanceof CPU && (c2 instanceof RAM || c2 instanceof GPU)) {
            return c2.getCompatibleIds().contains(c1.getId());
        }

        // RAM с GPU
        if (c1 instanceof RAM && c2 instanceof GPU) {
            return c2.getCompatibleIds().contains(c1.getId());
        }

        // GPU с RAM (обратное направление)
        if (c1 instanceof GPU && c2 instanceof RAM) {
            return c2.getCompatibleIds().contains(c1.getId());
        }

        return false;
    }
}
