package com.katsubo.mrz.network.io;

import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
@Component
@FunctionalInterface
public interface ImageLoader {
    BufferedImage load(String path);
}
