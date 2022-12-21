/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.automation.threads.opencvlib.automaticImageProcess;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

/**
 *
 * @author bianc
 */
public class AutomaticImageProcessImplements implements AutomaticImageProcess{

    @Override
    public Mat blurImage(Mat imageMatToBlur) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Mat applyMorphOnImage(int shapeOfStructuringElement, int structuringElementSize) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Mat morphologyEx(Mat imageInitialState, Mat imageFinalState, int morphologyType, Mat structuringElement) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<MatOfPoint> findContours(Mat imageToFindContours, List<MatOfPoint> outputContours, Mat hierarchyType, int contourRetrivalMode, int contourApproximationMethod) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double contourArea(Mat contourToCalculateArea) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Mat drawContours(Mat resultImage, List<MatOfPoint> foundContours, int contourIdx, Scalar contourColor, int contourThickness, int contourLineTipe, Mat contourHierarchyType, int contourMaxLevelToDraw, Point optionalContourShift) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
