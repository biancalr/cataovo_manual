/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.opencvlib.converters;

import cataovo.entities.Frame;
import cataovo.filechooser.handler.FileExtension;
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
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author bibil
 */
public final class Converter {

    private static final Logger LOG = Logger.getLogger(Converter.class.getName());
    private static volatile Converter CONVERTER;
    
    public static Converter getInstance() {
        Converter FORMAT_CONVERTER = Converter.CONVERTER;
        if (FORMAT_CONVERTER == null) {
            synchronized (Converter.class){
                FORMAT_CONVERTER = Converter.CONVERTER;
                if (FORMAT_CONVERTER == null) {
                    Converter.CONVERTER = FORMAT_CONVERTER = new Converter();
                }
            }
        }
        return FORMAT_CONVERTER;
    }
    
    /**
     * Converts an image Frame to an OpenCV Mat.
     * 
     * @param current
     * @return 
     */
    public MatWrapper convertImageFrameToMat(Frame current){
        Mat m = new Mat();
        Optional<Mat> optional = Optional.ofNullable(Imgcodecs.imread(current.getPaletteFrame().getPath()));
        optional.ifPresent((t) -> t.copyTo(m));
        MatWrapper wrapper = new MatWrapper(m.rows(), m.cols(), m.type(), m.clone(), current.getPaletteFrame().getAbsolutePath());
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
        LOG.log(Level.INFO, "Error while converting a MAT to: {0}", extension.name());
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
