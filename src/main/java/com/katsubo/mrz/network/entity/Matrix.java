package com.katsubo.mrz.network.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ojalgo.matrix.PrimitiveMatrix;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Data
@NoArgsConstructor
public class Matrix {
    private PrimitiveMatrix matrix;

    public Matrix(double[][] matrix) {
        this.matrix = PrimitiveMatrix.FACTORY.rows(matrix);
    }

    private Matrix(PrimitiveMatrix matrix) {
        this.matrix = matrix;
    }

    public Matrix multiply(Double arg) {
        return new Matrix(matrix.multiply(arg.doubleValue()));
    }

    public Matrix multiply(Matrix arg) {
        return new Matrix(matrix.multiply(arg.matrix));
    }

    public Matrix transpose() {
        return new Matrix(matrix.transpose());
    }

    public Matrix minus(Matrix arg) {
        return new Matrix(matrix.subtract(arg.matrix));
    }

    public double[][] getAsArray() {
        return matrix.toRawCopy2D();
    }

    public double determinant(){
        return Math.abs(matrix.getDeterminant().doubleValue());
    }


    public Matrix invert() {
        return new Matrix(matrix.invert());
    }

    public Matrix update(Matrix matrix) {
        this.matrix = matrix.matrix;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix1 = (Matrix) o;
        return Objects.equals(matrix, matrix1.matrix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matrix);
    }
}
