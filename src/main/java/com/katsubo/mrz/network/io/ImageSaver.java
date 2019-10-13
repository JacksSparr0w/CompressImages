package com.katsubo.mrz.network.io;

import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
@Component
@FunctionalInterface
public interface ImageSaver {
    void save(BufferedImage image, String path, String format);
}
