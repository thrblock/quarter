package com.thrblock.game.quarter.comp;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.thrblock.poolable.ArrayPool;

@Configuration
public class QuarterCircleFactory {

    private ArrayPool<QuarterCircle> circles;

    @PostConstruct
    void init() {
        circles = new ArrayPool<>(60 * 60, this::build);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public QuarterCircle build() {
        return new QuarterCircle(this);
    }

    public void place(float x, float y, float w) {
        circles.getAvailable().activeAt(x, y, w);
    }

    public void clear() {
        circles.interruptAll();
    }

    public void expend() {
        circles.snapShot().stream()
                .filter(Predicate.not(QuarterCircle::isAvailable))
                .collect(Collectors.toList())
                .forEach(QuarterCircle::expend);
    }
}