package com.katsubo.mrz.network.processing.impl;

import com.katsubo.mrz.network.entity.Matrix;
import com.katsubo.mrz.network.processing.WeightsGenerator;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Component;

import java.util.Random;

@Log4j2
@Component
public class WeightsGeneratorImpl implements WeightsGenerator {

    private static final String NUMBER_OF_ROWS_OR_COLUMNS_LESS_THAN_ZERO = "number of rows or columns less than zero";

    @Override
    public Matrix generate(int rows, int columns) {
        if (rows < 0 || columns < 0) {
            log.log(Level.ERROR, NUMBER_OF_ROWS_OR_COLUMNS_LESS_THAN_ZERO);
            throw new IllegalArgumentException(NUMBER_OF_ROWS_OR_COLUMNS_LESS_THAN_ZERO);
        }
        Random random = new Random();
        double[][] matrix = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                //matrix[i][j] = (double)(random.nextInt(101) - 50)/50;
                matrix[i][j] = random.nextDouble() * 2 - 1;
            }
        }
        log.log(Level.INFO, "new matrix created");
        return new Matrix(matrix);
    }
}
