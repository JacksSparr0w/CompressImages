package com.katsubo.mrz.network.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
@Data
public class Image {
    private BufferedImage image;

    private int width;

    private int height;

    private BufferedImage[][] subImages;

    private int widthStep;

    private int heightStep;

    private boolean isCrossed;

    public BufferedImage getSubImage(int row, int col) {
        return subImages[row][col];
    }

}
