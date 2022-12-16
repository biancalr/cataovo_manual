/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.opencvlib.wrappers;

import cataovo.entities.Frame;
import cataovo.entities.Point;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author bibil
 */
public class MatWrapper{

    private static final int DEPTH = CvType.CV_8U;
    private final Point finalFramePoint = new Point(640, 480);
    private Mat mat;
    private String location;
    
    public MatWrapper() {
        location = null;
        mat = new Mat(new Size(finalFramePoint.getX(), finalFramePoint.getY()), DEPTH);
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
