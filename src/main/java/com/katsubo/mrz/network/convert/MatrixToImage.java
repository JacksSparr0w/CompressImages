package com.katsubo.mrz.network.convert;

import com.katsubo.mrz.network.entity.Image;
import com.katsubo.mrz.network.entity.Training;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;

@Component
public class MatrixToImage implements Converter<Training, Image> {
    private final static Integer MAX_VALUE = 255;
    private final static Integer MIN_VALUE = 0;
    private final static Integer COLORS = 3;
    private final static Integer RED = 0;
    private final static Integer GREEN = 1;
    private final static Integer BLUE = 2;

    @Override
    public Image convert(Training training) {
        Image image = training.getImage();
        int height = image.getSubImages().length;
        int width = image.getSubImages()[0].length;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                convertSubImage(image.getSubImage(row, col), training.getWeights().get(col + row*width).getAsArray());
            }
        }
        return training.getImage();
    }

    private void convertSubImage(BufferedImage image, double[][] weights) {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int position = COLORS * (j + i * image.getHeight());
                int red = normalize(weights[0][RED + position]);
                int green = normalize(weights[0][GREEN + position]);
                int blue = normalize(weights[0][BLUE + position]);
                image.setRGB(i, j, new Color(red, green, blue).getRGB());
            }
        }
    }

    private int normalize(double color) {
        int result = (int) (MAX_VALUE * (color + 1) / 2);
        return Math.min(Math.max(result, MIN_VALUE), MAX_VALUE);
    }
}
