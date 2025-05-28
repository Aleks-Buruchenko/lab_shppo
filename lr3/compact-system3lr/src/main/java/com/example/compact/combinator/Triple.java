package com.example.compact.combinator;

import com.example.compact.components.SystemComponent;

public class Triple {
    private final SystemComponent cpu;
    private final SystemComponent ram;
    private final SystemComponent gpu;

    public Triple(SystemComponent cpu, SystemComponent ram, SystemComponent gpu) {
        this.cpu = cpu;
        this.ram = ram;
        this.gpu = gpu;
    }

    public SystemComponent getCpu() {
        return cpu;
    }

    public SystemComponent getRam() {
        return ram;
    }

    public SystemComponent getGpu() {
        return gpu;
    }
}