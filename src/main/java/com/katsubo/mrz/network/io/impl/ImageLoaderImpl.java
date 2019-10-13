package com.katsubo.mrz.network.io.impl;

import com.katsubo.mrz.network.io.ImageLoader;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
@Log4j2
public class ImageLoaderImpl implements ImageLoader {

    private static final String PATH_IS_EMPTY = "path is empty";
    private static final String ERROR_LOADING_IMAGE = "error loading image";
    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public BufferedImage load(String path) {
        if (path.isEmpty()) {
            log.log(Level.ERROR, PATH_IS_EMPTY);
            throw new IllegalArgumentException(PATH_IS_EMPTY);
        }
        BufferedImage image = null;
        try {
            Resource resource = resourceLoader.getResource("classpath:" + path);

            File file = resource.getFile();

            image = ImageIO.read(file);
            log.log(Level.INFO, "image loaded");
        } catch (IOException e) {
            log.log(Level.ERROR, ERROR_LOADING_IMAGE);
            throw new IllegalArgumentException(ERROR_LOADING_IMAGE);
        }
        return image;
    }
}
