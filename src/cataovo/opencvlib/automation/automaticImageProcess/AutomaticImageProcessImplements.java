/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.opencvlib.automation.automaticImageProcess;

import cataovo.resources.fileChooser.handler.FileExtension;
import cataovo.opencvlib.converters.Converter;
import cataovo.opencvlib.wrappers.MatWrapper;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 * Contains the implementation of the stablished steps to detect Aedes eggs in a
 * image.
 *
 * @author Bianca Leopoldo Ramos
 */
public class AutomaticImageProcessImplements implements AutomaticImageProcess {

    /**
     * Logging for AutomaticImageProcessImplements.
     */
    private static final Logger LOG = Logger.getLogger(AutomaticImageProcessImplements.class.getName());
    /**
     * Represents white the color.
     */
    private static final double[] WHITE = {255, 255, 255};
    /**
     * Represents the black color.
     */
    private static final double[] BLACK = {0, 0, 0};
    /**
     * Quantity of eggs in a frame.
     */
    private int quantityOfEggs = 0;
    /**
     * Objects found in each image. Potencial eggs, a single egg or collection
     * of two or mor eggs.
     */
    private List<MatOfPoint> eggsContours = new ArrayList<>();

    @Override
    public Mat applyBlurOnImage(String savingPath, Mat imageMatToBlur, int ksize_width, int ksize_height) {
        saveImage(imageMatToBlur, savingPath.replace("/blur.png", "/original.png"));
        LOG.log(Level.INFO, "Applying blur...");
        Mat dstn = Mat.zeros(imageMatToBlur.size(), CvType.CV_8U);
        Imgproc.cvtColor(imageMatToBlur.clone(), dstn, Imgproc.COLOR_BGR2GRAY);
        Imgproc.blur(dstn, dstn, new Size(ksize_width, ksize_height));
        if (saveImage(dstn, savingPath)) {
            return dstn;
        }
        return null;
    }

    @Override
    public Mat applyBinaryOnImage(String savingPath, BufferedImage buffImgToBinary) {
        LOG.log(Level.INFO, "Applying binary...");
        Mat dstn = Mat.zeros(new Size(buffImgToBinary.getWidth(), buffImgToBinary.getHeight()), CvType.CV_8UC3);
        for (int i = 0; i < buffImgToBinary.getWidth(); i++) {
            for (int j = 0; j < buffImgToBinary.getHeight(); j++) {
                Color color = new Color(buffImgToBinary.getRGB(i, j));
                double blue = color.getRed();
                double green = color.getGreen();
                double red = color.getBlue();
                if ((blue > (red + 25))
                        || (red > 75 && blue > 75 && green > 75)
                        || (blue > (green + 15))) {
                    dstn.put(j, i, WHITE);
                } else {
                    dstn.put(j, i, BLACK);
                }
            }
        }
        if (saveImage(dstn, savingPath)) {
            return dstn;
        }
        return null;
    }

    @Override
    public Mat applyMorphOnImage(String savingPath, int structuringElementWidth, int structuringElementHeight, int morphologicalOperation, Mat imageToMorph) {
        LOG.log(Level.INFO, "Applying morphology...");
        Mat dstn = Mat.zeros(imageToMorph.size(), imageToMorph.type());
        Mat structuringElement = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(structuringElementWidth, structuringElementHeight));
        Imgproc.morphologyEx(imageToMorph.clone(), dstn, morphologicalOperation, structuringElement);
        if (saveImage(dstn, savingPath)) {
            return dstn;
        }
        return null;
    }

    @Override
    public Mat drawContoursOnImage(String savingPath, Mat imageToDraw, Mat imgToFindContours, int minSizeArea, int maxSizeArea) {
        LOG.log(Level.INFO, "drawing objects...");
        int numOfContours = 0;
        Mat result = imageToDraw.clone();
        List<MatOfPoint> contours = findContours(imgToFindContours.clone());
        List<MatOfPoint> foundContours = new ArrayList<>();

        for (int i = 0; i < contours.size(); i++) {
            double contourArea = Imgproc.contourArea(contours.get(i));

            if ((contourArea > minSizeArea) && (contourArea < maxSizeArea)) {
                numOfContours++;
                if (contourArea > 3000) {
                    numOfContours++;
                }
                foundContours.add(contours.get(i));

                Imgproc.drawContours(result, contours, i, new Scalar(0, 200, 0), 2, 8, new Mat(), 0, new Point(0, 0));
            }
        }
        LOG.log(Level.INFO, "Quantity of contours: {0}", numOfContours);

        this.quantityOfEggs = numOfContours;
        this.eggsContours = foundContours;

        if (saveImage(result, savingPath)) {
            return result;
        }
        return null;
    }

    /**
     * Saves the resulted image from any step.
     *
     * @param dstn
     * @param savingPath
     * @return
     */
    private boolean saveImage(Mat dstn, String savingPath) {
        try {
            BufferedImage image = Converter.getInstance().convertMatToPng(new MatWrapper(dstn, savingPath)).get();
            ImageIO.write(image, FileExtension.PNG.toString().toLowerCase(), new File(savingPath));
            return true;
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Look for objectd detecting each boundary from the image
     *
     * @param src the image to find the contours.
     * @return the list of found contours
     * @see #drawContoursOnImage(java.lang.String, org.opencv.core.Mat,
     * org.opencv.core.Mat, int, int)
     */
    public List<MatOfPoint> findContours(Mat src) {
        LOG.log(Level.INFO, "Finding objects...");
        List<MatOfPoint> contours = new ArrayList<>();
        Mat dstny = getChannelImage(src);
        Imgproc.findContours(dstny, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
        return contours;
    }

    /**
     * Transforms a three channels image to a single channel image.
     *
     * @param src the image to split the channels
     * @return a single channel result image
     */
    private Mat getChannelImage(Mat src) {
        Mat matR = Mat.zeros(src.size(), CvType.CV_8UC1);
        for (int i = 0; i < src.rows(); i++) {
            for (int j = 0; j < src.cols(); j++) {
                double temp[] = src.get(i, j);
                matR.put(i, j, temp[2]);
            }
        }
        return matR;
    }

    /**
     * <p>
     * Generates the content of the automatic counting.</p>
     *
     * @return A text containg the total of the eggs and a list of a certain
     * quantity of coodinates that forms each object.
     */
    @Override
    public StringBuffer generateAutomaticProcessmentRelatory() {
        StringBuffer builder = new StringBuffer(Integer.toString(this.quantityOfEggs));
        List<Point> auxlist;
        List<Point> mainPoints = new ArrayList<>();
        for (int i = 0; i < this.eggsContours.size(); i++) {
            MatOfPoint get = this.eggsContours.get(i);

            auxlist = get.toList();
            for (int j = 0; j < auxlist.size(); j++) {

                // Saves a point each fifty steps
                if ((j % 50) == 0) {
                    mainPoints.add(auxlist.get(j));
                }

            }

        }

        mainPoints.stream().forEach((p) -> {
            builder.append(",");
            builder.append(p.x);
            builder.append(",");
            builder.append(p.y);
        });

        return builder;
    }

}
