package com.katsubo.mrz.network.processing;

import com.katsubo.mrz.network.entity.Image;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
@Component
@FunctionalInterface
public interface ImageDivider {
    Image divide(BufferedImage image, Integer n, Integer m);
}
