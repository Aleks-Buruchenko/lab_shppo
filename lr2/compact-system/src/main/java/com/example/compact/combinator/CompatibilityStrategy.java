package com.example.compact.combinator;

import com.example.compact.components.SystemComponent;

public interface CompatibilityStrategy {
    boolean checkCompatibility(SystemComponent c1, SystemComponent c2);
}