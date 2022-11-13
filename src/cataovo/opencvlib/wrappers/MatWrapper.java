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
public class MatWrapper {
    
    private Mat mat;

    public Mat getMat() {
        return mat;
    }

    public void setMat(Mat mat) {
        this.mat = mat;
    }

    public MatWrapper(Mat mat) {
        this.mat = mat;
    }

    public MatWrapper() {
        this.mat = new Mat();
    }
    
}
