package com.katsubo.mrz.network.processing;

import com.katsubo.mrz.network.entity.UserInputConfig;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
@Component
@FunctionalInterface
public interface ImageCompress {
    BufferedImage compress(BufferedImage source, UserInputConfig config);
}
