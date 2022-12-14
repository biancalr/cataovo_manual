/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.opencvlib.wrappers;

import cataovo.entities.Frame;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author bibil
 */
public class MatWrapper{

    private int n_rows;
    private int n_cols;
    private int type;
    private Mat mat;
    private String location;
    
    public MatWrapper() {
        n_rows = 0;
        n_cols = 0;
        type = 0;
        location = null;
    }
    
    public MatWrapper(int row, int col, int type, Mat m, String location) {
        this.n_rows = row;
        this.n_cols = col;
        this.type = type;
        this.mat =  m;
        this.location = location;
    }
    
    public MatWrapper(Frame frame){
        this.location = frame.getPaletteFrame().getAbsolutePath();
        this.mat = Imgcodecs.imread(frame.getPaletteFrame().getAbsolutePath()).clone();
        this.n_rows = mat.rows();
        this.n_cols = mat.cols();
        this.type = mat.type();
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Mat getMat() {
        return mat;
    }

    public void setMat(Mat mat) {
        this.mat = mat;
    }
}
