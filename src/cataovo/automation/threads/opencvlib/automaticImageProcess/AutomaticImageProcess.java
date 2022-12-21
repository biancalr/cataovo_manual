/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
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
public interface AutomaticImageProcess {

    /**
     *
     * @param imageMatToBlur
     * @return
     */
    public Mat blurImage(Mat imageMatToBlur);

    /**
     *
     * @param shapeOfStructuringElement
     * @param structuringElementSize
     * @return
     */
    public Mat applyMorphOnImage(int shapeOfStructuringElement, int structuringElementSize);

    /**
     *
     * @param imageInitialState
     * @param imageFinalState
     * @param morphologyType
     * @param structuringElement
     * @return
     */
    public Mat morphologyEx(Mat imageInitialState, Mat imageFinalState, int morphologyType, Mat structuringElement);

    /**
     *
     * @param imageToFindContours
     * @param outputContours
     * @param hierarchyType
     * @param contourRetrivalMode
     * @param contourApproximationMethod
     * @return
     */
    public List<MatOfPoint> findContours(Mat imageToFindContours, List<MatOfPoint> outputContours, Mat hierarchyType, int contourRetrivalMode, int contourApproximationMethod);

    /**
     * Calculates a contour area.
     *
     * @param contourToCalculateArea
     * @return
     */
    public double contourArea(Mat contourToCalculateArea);

    /**
     *
     * @param resultImage
     * @param foundContours
     * @param contourIdx
     * @param contourColor
     * @param contourThickness
     * @param contourLineTipe
     * @param contourHierarchyType
     * @param contourMaxLevelToDraw
     * @param optionalContourShift
     * @return
     */
    public Mat drawContours(Mat resultImage, List<MatOfPoint> foundContours, int contourIdx, Scalar contourColor, int contourThickness, int contourLineTipe, Mat contourHierarchyType, int contourMaxLevelToDraw, Point optionalContourShift);

}
