package com.katsubo.mrz.network.processing.impl;

import com.katsubo.mrz.network.convert.ImageToMatrix;
import com.katsubo.mrz.network.convert.MatrixToImage;
import com.katsubo.mrz.network.entity.Image;
import com.katsubo.mrz.network.entity.Matrix;
import com.katsubo.mrz.network.entity.Training;
import com.katsubo.mrz.network.entity.UserInputConfig;
import com.katsubo.mrz.network.processing.ImageCollector;
import com.katsubo.mrz.network.processing.ImageCompress;
import com.katsubo.mrz.network.processing.ImageDivider;
import com.katsubo.mrz.network.processing.WeightsGenerator;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class ImageCompressImpl implements ImageCompress {
    public static final int COLORS = 3;
    private final ImageDivider imageDivider;
    private final ImageToMatrix imageToMatrix;
    private final MatrixToImage matrixToImage;
    private final WeightsGenerator weightsGenerator;
    private final ImageCollector collector;

    @Autowired
    public ImageCompressImpl(ImageDivider imageDividerImpl, ImageToMatrix imageToMatrix, MatrixToImage matrixToImage, WeightsGenerator weightsGenerator, ImageCollector imageCollectorImpl) {
        this.imageDivider = imageDividerImpl;
        this.imageToMatrix = imageToMatrix;
        this.matrixToImage = matrixToImage;
        this.weightsGenerator = weightsGenerator;
        this.collector = imageCollectorImpl;
    }

    @Override
    public BufferedImage compress(BufferedImage source, UserInputConfig config) {
        Image image = imageDivider.divide(source, config.getN(), config.getM());
        int n = image.getHeightStep() * image.getWidthStep() * COLORS;
        List<Matrix> vectorList = imageToMatrix.convert(image);
        Matrix firstLayer = weightsGenerator.generate(n, config.getNeurons());
        Matrix secondLayer = firstLayer.transpose();
        Long timeBefore = System.currentTimeMillis();
        double compressCoefficient = (double)(n * vectorList.size())/((double)(n + vectorList.size())*config.getNeurons() + 2);
        log.log(Level.INFO, "coefficient of compress = " + compressCoefficient);
        List<Matrix> finalWeights = train(firstLayer, secondLayer, vectorList, config.getMaxError());

        Long timeAfter = System.currentTimeMillis();
        log.log(Level.INFO, String.format("time spend for training = %d", timeAfter - timeBefore));
        return collector.collect(matrixToImage.convert(new Training(image, finalWeights)));

    }

    private List<Matrix> train(Matrix firstLayer, Matrix secondLayer, List<Matrix> vectors, Integer e) {
        List<Matrix> training = new ArrayList<>();
        double wholeError;
        int iteration = 0;
        do {
            for (Matrix vector : vectors) {
                Matrix y = vector.multiply(firstLayer);
                Matrix result = y.multiply(secondLayer);
                Matrix delta = result.minus(vector);
                double firstStep = 1d / ((vector.multiply(vector.transpose()).getAsArray()[0][0]) + 10);

                //double firstStep = 0.005d;
                firstLayer.update(firstLayer
                        .minus(vector.transpose()
                        .multiply(delta)
                        .multiply(secondLayer.transpose()
                                .multiply(firstStep))));
                double secondStep = 1d / ((y.multiply(y.transpose()).getAsArray()[0][0]) + 10);

                secondLayer.update(secondLayer.minus(
                        y
                                .transpose()
                                .multiply(delta)
                                .multiply(secondStep)));
            }
            wholeError = 0d;
            for (Matrix vector : vectors) {
                Matrix resultVector = vector.multiply(firstLayer).multiply(secondLayer);
                wholeError += calcError(resultVector.minus(vector));
            }
            iteration++;
            log.log(Level.INFO, String.format("sum error = %f in %d iteration", wholeError, iteration));
        } while (wholeError > e);
        for (Matrix vector : vectors){
            Matrix result = vector.multiply(firstLayer).multiply(secondLayer);
            training.add(result);
        }
        log.log(Level.INFO, String.format("final sum error = %f in %d iteration", wholeError, iteration));
        return training;

    }

    private double calcError(Matrix delta) {
        double error = 0d;
        for (double value : delta.getAsArray()[0]) {
            error += value * value;
        }
        return error;
    }
}
