package com.katsubo.mrz.network.processing.impl;

import com.katsubo.mrz.network.entity.Image;
import com.katsubo.mrz.network.processing.ImageCollector;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Log4j2
@Component
public class ImageCollectorImpl implements ImageCollector {
    @Override
    public BufferedImage collect(Image image) {
        int stepX = image.getWidthStep();
        int stepY = image.getHeightStep();
        BufferedImage wholeImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < image.getSubImages().length; i++) {
            for (int j = 0; j < image.getSubImages()[i].length; j++) {
                BufferedImage subImage = image.getSubImage(i, j);
                wholeImage.getGraphics().drawImage(subImage, j * subImage.getWidth(), i * subImage.getHeight(), null);
            }
        }
        return wholeImage;
    }
}
