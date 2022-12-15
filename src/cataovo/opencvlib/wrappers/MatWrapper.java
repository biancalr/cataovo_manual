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

    private Mat mat;
    private String location;
    
    public MatWrapper() {
        location = null;
    }
    
    public MatWrapper(Mat m, String location) {
        this.mat =  m;
        this.location = location;
    }
    
    public MatWrapper(Frame frame){
        this.location = frame.getPaletteFrame().getAbsolutePath();
        this.mat = Imgcodecs.imread(frame.getPaletteFrame().getAbsolutePath()).clone();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Mat getOpencvMat() {
        return mat;
    }

    public void setOpencvMat(Mat mat) {
        this.mat = mat;
    }
}
