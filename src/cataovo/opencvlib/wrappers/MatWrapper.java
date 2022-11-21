/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.opencvlib.wrappers;

import org.opencv.core.Mat;

/**
 *
 * @author bibil
 */
public class MatWrapper{

    private int n_rows;
    private int n_cols;
    private byte[] data;
    private int type;
    private Mat mat;
    
    public MatWrapper() {
        n_rows = 0;
        n_cols = 0;
        data = new byte[0];
        type = 0;
        mat = new Mat();
    }

    public MatWrapper(int row, int col, byte[] data) {
        this.n_rows = row;
        this.n_cols = col;
        this.data = data;
        type = 0;
        this.mat = new Mat();
    }
    
    public MatWrapper(int row, int col, int type, Mat m) {
        this.n_rows = row;
        this.n_cols = col;
        this.type = type;
        this.mat =  m;
    }

    public int getN_rows() {
        return n_rows;
    }

    public void setN_rows(int n_rows) {
        this.n_rows = n_rows;
    }

    public int getN_cols() {
        return n_cols;
    }

    public void setN_cols(int n_cols) {
        this.n_cols = n_cols;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Mat getMat() {
        return mat;
    }

    public void setMat(Mat mat) {
        this.mat = mat;
    }
}
