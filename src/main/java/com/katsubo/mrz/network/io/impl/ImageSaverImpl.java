package com.katsubo.mrz.network.io.impl;

import com.katsubo.mrz.network.io.ImageSaver;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
@Log4j2
public class ImageSaverImpl implements ImageSaver {

    private static final String PATH_OR_FORMAT_IS_EMPTY = "path or format is empty";
    private static final String ERROR_SAVING_IMAGE = "error saving image";

    @Override
    public void save(BufferedImage image, String path, String format) {
        if (path.isEmpty() || format.isEmpty()) {
            log.log(Level.ERROR, PATH_OR_FORMAT_IS_EMPTY);
            throw new IllegalArgumentException(PATH_OR_FORMAT_IS_EMPTY);
        }
        try {
            ImageIO.write(image, format, new File(path));
            log.log(Level.INFO, "image saved");
        } catch (IOException e) {
            log.log(Level.ERROR, ERROR_SAVING_IMAGE);
            throw new IllegalArgumentException(ERROR_SAVING_IMAGE);
        }
    }
}
