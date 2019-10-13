package com.katsubo.mrz.network.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:userInput.properties")
@ConfigurationProperties(prefix = "input")
@Data
public class UserInputConfig {
    private Integer n;
    private Integer m;
    private Integer neurons;
    private Integer maxError;
}
