package com.katsubo.mrz.network.convert;

import com.katsubo.mrz.network.entity.Image;
import com.katsubo.mrz.network.entity.Matrix;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImageToMatrix implements Converter<Image, List<Matrix>> {
    private final static Integer MAX_VALUE = 255;
    private final static Integer COLORS = 3;
    private final static Integer RED = 0;
    private final static Integer GREEN = 1;
    private final static Integer BLUE = 2;

    @Override
    public List<Matrix> convert(Image source) {
        List<Matrix> matrixList = new ArrayList<>();
        for (BufferedImage[] imageRow : source.getSubImages()) {
            for (BufferedImage image : imageRow) {
                matrixList.add(convertSubImage(image));
            }
        }
        return matrixList;
    }

    private Matrix convertSubImage(BufferedImage image) {
        int size = image.getHeight() * image.getWidth() * COLORS;
        double[][] matrix = new double[1][size];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color color = new Color(image.getRGB(i, j));
                int position = COLORS * (j + i * image.getHeight());
                matrix[0][RED + position] = normalize(color.getRed());
                matrix[0][GREEN + position] = normalize(color.getGreen());
                matrix[0][BLUE + position] = normalize(color.getBlue());
            }
        }
        return new Matrix(matrix);
    }

    private double normalize(double color) {
        return (2 * color / MAX_VALUE) - 1;
    }
}
