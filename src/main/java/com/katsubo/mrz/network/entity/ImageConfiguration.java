package com.katsubo.mrz.network.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:imageProps.properties")
@ConfigurationProperties(prefix = "image")
@Data
public class ImageConfiguration {
    private String source;
    private String target;
    private String format;
}
