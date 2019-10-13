package com.katsubo.mrz.network.processing.impl;

import com.katsubo.mrz.network.entity.Image;
import com.katsubo.mrz.network.processing.ImageDivider;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
@Log4j2
public class ImageDividerImpl implements ImageDivider {
    @Override
    public Image divide(BufferedImage source, Integer n, Integer m) {
        Image image = new Image();
        image.setImage(source);
        image.setHeight(source.getHeight());
        image.setWidth(source.getWidth());
        validate(n, m);

        int heightStep = findDelimeter(source.getHeight(), n);
        int heightCount = source.getHeight() / heightStep;
        /*
        if (source.getHeight() % n == 0) {

            heightStep = n;
            heightCount = source.getHeight() / n;
        } else {
            heightStep = (source.getHeight() - n) / (source.getHeight() / n);
            heightCount = source.getHeight() / n + 1;
        }
        */
        int widthStep = findDelimeter(source.getWidth(), m);
        int widthCount = source.getWidth() / widthStep;
        /*
        if (source.getWidth() % m == 0) {
            widthStep = m;
            widthCount = source.getWidth() / m;
        } else {
            widthStep = (source.getWidth() - m) / (source.getWidth() / m);
            widthCount = source.getWidth() / m + 1;
        }*/
        BufferedImage[][] subImages = new BufferedImage[heightCount][widthCount];
        for (int i = 0; i < heightCount; i++) {
            for (int j = 0; j < widthCount; j++) {
                subImages[i][j] = source.getSubimage(j * widthStep, i * heightStep, widthStep, heightStep);
            }
        }
        image.setSubImages(subImages);
        image.setWidthStep(widthStep);
        image.setHeightStep(heightStep);
        return image;
    }

    private void validate(Integer n, Integer m) {
        if (n < 0 || m < 0) {
            log.log(Level.ERROR, "invalid input n or m");
            throw new IllegalArgumentException("invalid input n or m");
        }
    }

    private int findDelimeter(int big, int little) {
        while (big % little != 0) {
            little--;
        }
        return little;
    }
}
