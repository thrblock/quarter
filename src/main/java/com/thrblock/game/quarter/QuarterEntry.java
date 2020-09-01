package com.thrblock.game.quarter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;

import com.thrblock.game.quarter.comp.QuarterComponent;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
public class QuarterEntry implements CommandLineRunner {
    
    @Autowired
    private QuarterComponent comp;
    
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");
        new SpringApplicationBuilder(QuarterEntry.class)
                .web(WebApplicationType.NONE)
                .headless(false)
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        comp.activited();
    }
}
