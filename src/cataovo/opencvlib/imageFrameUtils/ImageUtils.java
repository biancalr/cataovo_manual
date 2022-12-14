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
public class ImageUtils {

    private static final Logger LOG = Logger.getLogger(ImageUtils.class.getName());
    private static volatile ImageUtils IMAGE_UTILS;
    
    public static ImageUtils getInstance() {
        ImageUtils FRAME_IMAGE_UTILS = ImageUtils.IMAGE_UTILS;
        if (FRAME_IMAGE_UTILS == null) {
            synchronized (ImageUtils.class){
                FRAME_IMAGE_UTILS = ImageUtils.IMAGE_UTILS;
                if (FRAME_IMAGE_UTILS == null) {
                    ImageUtils.IMAGE_UTILS = FRAME_IMAGE_UTILS = new ImageUtils();
                }
            }
        }
        return FRAME_IMAGE_UTILS;
    }
    
    /**
     * Paint the point clicked in the image.
     *
     * @param point axis to paint
     * @param imagePointed the image to draw a circule point
     * @return the image pointed
     */
    public Mat circle(Point point, Mat imagePointed) {
        //draw the circle
        LOG.log(Level.INFO, "Draw the circle...");
        Imgproc.circle(imagePointed, 
                new org.opencv.core.Point(point.x, point.y),
                2,
                new Scalar(255, 0, 255),
                Core.FILLED);
        return imagePointed;
    }
    
    /**
     * Mark a grid.
     *
     * @param beginPoint
     * @param endPoint
     * @param imageGrid
     * @return the clicked Grid Image
     */
    public Mat rectangle(Point beginPoint, Point endPoint, Mat imageGrid) {
        LOG.log(Level.INFO, "Draw the rectangle...");
        Imgproc.rectangle(imageGrid,
                beginPoint,
                endPoint,
                new Scalar(0, 255, 0));
        return imageGrid;
    }

    /**
     * Capture the Rect of the grid for identification. Allows to capture the
     * rect so it can be possible to indentify which grid has eggs inside.
     *
     * @param beginGrid
     * @param endGrid
     * @return the area Rect of the clicked Grid
     * @throws IOException
     */
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
