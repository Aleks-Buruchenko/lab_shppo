package com.example.compact.strategy;

import com.example.compact.model.SystemComponent;

public interface CompatibilityStrategy {
    boolean checkCompatibility(SystemComponent c1, SystemComponent c2);
}