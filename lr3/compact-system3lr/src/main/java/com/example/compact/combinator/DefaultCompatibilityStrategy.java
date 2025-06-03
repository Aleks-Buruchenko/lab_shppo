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
        if (c1 instanceof CPU && (c2 instanceof RAM || c2 instanceof GPU)) {
            CPU cpu = (CPU) c1;
            SystemComponent other = c2;
            boolean isCompatible = other.getCompatibleIds().contains(cpu.getId());
            System.out.println("Checking compatibility: CPU=" + cpu.getId() + ", " + 
                              (c2 instanceof RAM ? "RAM" : "GPU") + "=" + other.getId() + 
                              ", compatibleIds=" + other.getCompatibleIds() + 
                              ", result=" + isCompatible);
            return isCompatible;
        }
        return false;
    }
}