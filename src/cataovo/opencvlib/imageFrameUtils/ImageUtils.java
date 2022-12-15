/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.opencvlib.imageFrameUtils;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;

/**
 *
 * @author bibil
 */
public interface ImageUtils {

    /**
     * Paint the dot clicked in the image.
     *
     * @param point axis to paint
     * @param imagePointed the image to draw a circule point
     * @return the image pointed
     */
    public Mat circle(Point point, Mat imagePointed);

    /**
     * Mark a grid made by two dots in the image
     *
     * @param beginPoint
     * @param endPoint
     * @param imageGrid
     * @return the clicked Grid Image
     */
    public Mat rectangle(Point beginPoint, Point endPoint, Mat imageGrid);

    /**
     * Capture the Rect of the grid for identification.Allows to capture the
     * rect so it can be possible to indentify which grid has a certain egg
     * inside.
     *
     * @param beginGrid
     * @param endGrid
     * @return the area Rect of the clicked Grid
     */
    public Rect captureGridMat(Point beginGrid, Point endGrid);
}
