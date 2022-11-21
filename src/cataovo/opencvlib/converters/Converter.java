/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.opencvlib.converters;

import cataovo.entities.Frame;
import cataovo.fileHandler.FileExtension;
import cataovo.opencvlib.wrappers.MatOfBytesWrapper;
import cataovo.opencvlib.wrappers.MatWrapper;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author bibil
 */
public class Converter {

    private Frame frame;
    private MatWrapper mat;
    private static final Logger LOG = Logger.getLogger(Converter.class.getName());

    public Converter() {
        this.frame = new Frame();
        this.mat = new MatWrapper();
    }

    public Converter(Frame frame) {
        this.frame = frame;
        this.mat = new MatWrapper();
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }
    
    /**
     * Converts an image Frame to an OpenCV Mat.
     * 
     * @param current
     * @return 
     */
    public MatWrapper convertImageFrameToMat(Frame current){
        this.frame = current;
        Mat m = null;
        Optional<Mat> optional = Optional.ofNullable(Imgcodecs.imread(frame.getPaletteFrame().getAbsolutePath()).clone());
        optional.ifPresent((t) -> t.copyTo(m));
        MatWrapper wrapper = new MatWrapper(m.rows(), m.cols(), m.type(), m.clone());
        return wrapper;
        
    }

    /**
     * Converts the current frame to a PNG file.
     *
     * @param current the curent frame.
     * @return an Optional of the BufferedImage as ".jpg"
     */
    public Optional<Image> convertMatToImg(MatWrapper current) {
        return Optional.ofNullable(matToBuffedImageConvert(current, FileExtension.JPG));
    }

    /**
     * Converts the current frame to a PNG file.
     *
     * @param current the current frame as Opencv.Mat
     * @return an Optional of the BufferedImage as ".png"
     */
    public Optional<BufferedImage> convertMatToPng(MatWrapper current) {
        return Optional.ofNullable(matToBuffedImageConvert(current, FileExtension.PNG));
    }
    
    /**
     * Converts the current frame to a image of given extension.
     * 
     * @param current the current frame as Opencv.Mat
     * @param extension the type of desired extension for the frame.
     * @return the image as given extension.
     */
    private BufferedImage matToBuffedImageConvert(MatWrapper current, FileExtension extension){
        MatOfBytesWrapper ofBytesWrapper = new MatOfBytesWrapper();
        boolean codeOk = Imgcodecs.imencode("." + extension.toString().toLowerCase(), current.getMat(), ofBytesWrapper);
        BufferedImage output = null;
        if (codeOk) {
            byte[] byteArray = ofBytesWrapper.toArray();
            InputStream in = new ByteArrayInputStream(byteArray);
            try {
                output = ImageIO.read(in);
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, "Error while converting a MAT to: " + extension.toString(), ex);
            }
            
        } 
        return output;
        
    }
    

}
