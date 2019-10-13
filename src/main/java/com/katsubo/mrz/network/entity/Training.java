package com.katsubo.mrz.network.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
public class Training {
    private Image image;

    private List<Matrix> weights;
}
