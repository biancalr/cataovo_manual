/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.opencvlib.imageFrameUtils;

import java.io.IOException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bibil
 */
public class ImageUtilsImplements implements ImageUtils{

    private static final Logger LOG = Logger.getLogger(ImageUtilsImplements.class.getName());
    private static volatile ImageUtilsImplements IMAGE_UTILS;
    
    /**
     * Paint the dot clicked in the image.
     *
     * @param point axis to paint
     * @param imagePointed the image to draw a circule point
     * @return the image pointed
     */
    @Override
    public Mat circle(Point point, Mat imagePointed) {
        //draw the circle
        LOG.log(Level.INFO, "Draw the circle...");
        Imgproc.circle(imagePointed, 
                point,
                2,
                new Scalar(255, 0, 255),
                Core.FILLED);
        return imagePointed;
    }
    
    /**
     * Mark a grid made by two dots in the image
     *
     * @param beginPoint
     * @param endPoint
     * @param imageGrid
     * @return the clicked Grid Image
     */
    @Override
    public Mat rectangle(Point beginPoint, Point endPoint, Mat imageGrid) {
        LOG.log(Level.INFO, "Draw the rectangle...");
        Imgproc.rectangle(imageGrid,
                beginPoint,
                endPoint,
                new Scalar(0, 255, 0));
        return imageGrid;
    }

    /**
     * Capture the Rect of the grid for identification. 
     * Allows to capture the rect so it can be possible to indentify which 
     * grid has a certain egg inside.
     *
     * @param beginGrid
     * @param endGrid
     * @return the area Rect of the clicked Grid
     * @throws IOException
     */
    @Override
    public Rect captureGridMat(Point beginGrid, Point endGrid) throws IOException {
        LOG.log(Level.INFO, "Capture the Region...");
        Rect grid = new Rect();
        grid.x = (int) beginGrid.x;
        grid.y = (int) beginGrid.y;
        grid.width = (int) (endGrid.x - beginGrid.x);
        grid.height = (int) (endGrid.y - beginGrid.y);
        return grid;

    }

}
