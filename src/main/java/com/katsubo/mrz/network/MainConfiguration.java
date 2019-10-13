package com.katsubo.mrz.network;

import com.katsubo.mrz.network.entity.ImageConfiguration;
import com.katsubo.mrz.network.entity.UserInputConfig;
import com.katsubo.mrz.network.io.ImageLoader;
import com.katsubo.mrz.network.io.ImageSaver;
import com.katsubo.mrz.network.processing.ImageCompress;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
@Configuration
@ComponentScan(value = "com.katsubo.mrz.network")
@Data
public class MainConfiguration {
    /*
    private ApplicationContext context = new AnnotationConfigApplicationContext();
    private final ImageConfiguration imageConfiguration = context.getBean("imageConfiguration", ImageConfiguration.class);
    private final UserInputConfig userInputConfig = context.getBean("userInputConfig", UserInputConfig.class);
    private final ImageCompress imageCompressImpl = context.getBean("imageCompressImpl", ImageCompress.class);
    private final ImageSaver imageSaverImpl = context.getBean("imageSaverImpl", ImageSaver.class);
    private final ImageLoader imageLoaderImpl = context.getBean("imageLoaderImpl", ImageLoader.class);
    */


    @Autowired
    private ImageCompress imageCompressImpl;
    @Autowired
    private ImageSaver imageSaverImpl;
    @Autowired
    private ImageLoader imageLoaderImpl;
    @Autowired
    private ImageConfiguration imageConfiguration;
    @Autowired
    private UserInputConfig userInputConfig;

    public void run() {
        BufferedImage source = imageLoaderImpl.load(imageConfiguration.getSource());
        BufferedImage result = imageCompressImpl.compress(source, userInputConfig);
        imageSaverImpl.save(result, imageConfiguration.getTarget(), imageConfiguration.getFormat());
    }

}
