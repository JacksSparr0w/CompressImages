package com.katsubo.mrz.network;

import com.katsubo.mrz.network.entity.ImageConfiguration;
import com.katsubo.mrz.network.entity.UserInputConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({UserInputConfig.class, ImageConfiguration.class})
public class NetworkApplication {
    private final MainConfiguration mainConfiguration;

    @Autowired
    public NetworkApplication(MainConfiguration mainConfiguration) {
        this.mainConfiguration = mainConfiguration;
        mainConfiguration.run();
    }

    public static void main(String[] args) {
        SpringApplication.run(NetworkApplication.class, args);
    }

}
