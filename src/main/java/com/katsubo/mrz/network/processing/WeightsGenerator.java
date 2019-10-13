package com.katsubo.mrz.network.processing;

import com.katsubo.mrz.network.entity.Matrix;
import org.springframework.stereotype.Component;

@Component
@FunctionalInterface
public interface WeightsGenerator {
    Matrix generate(int rows, int columns);
}
