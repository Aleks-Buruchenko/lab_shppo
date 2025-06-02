package com.example.compact.config;

import com.example.compact.combinator.CompatibilityStrategy;
import com.example.compact.components.CPU;
import com.example.compact.components.ComponentFactory;
import com.example.compact.components.GPU;
import com.example.compact.components.RAM;
import com.example.compact.components.SystemComponent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;

import java.util.List;

@Configuration
@ComponentScan(basePackages = "com.example.compact")
@EnableAspectJAutoProxy
public class AppConfig {

    @Bean(name = "CPUFactory")
    public ComponentFactory cpuFactory() {
        return new ComponentFactory() {
            @Override
            public SystemComponent createComponent(String id, String name, String manufacturer, List<String> compatibleIds) {
                return new CPU(id, name, manufacturer, compatibleIds);
            }
        };
    }

    @Bean(name = "RAMFactory")
    public ComponentFactory ramFactory() {
        return new ComponentFactory() {
            @Override
            public SystemComponent createComponent(String id, String name, String manufacturer, List<String> compatibleIds) {
                return new RAM(id, name, manufacturer, compatibleIds);
            }
        };
    }

    @Bean(name = "GPUFactory")
    public ComponentFactory gpuFactory() {
        return new ComponentFactory() {
            @Override
            public SystemComponent createComponent(String id, String name, String manufacturer, List<String> compatibleIds) {
                return new GPU(id, name, manufacturer, compatibleIds);
            }
        };
    }

    @Bean
    @Scope("singleton")
    public CompatibilityStrategy compatibilityStrategy() {
        return new CompatibilityStrategy() {
            @Override
            public boolean checkCompatibility(SystemComponent c1, SystemComponent c2) {
                return c1.getCompatibleIds().contains(c2.getId()) && c2.getCompatibleIds().contains(c1.getId());
            }
        };
    }
}