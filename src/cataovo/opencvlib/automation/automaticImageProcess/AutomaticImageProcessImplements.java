/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.opencvlib.automation.automaticImageProcess;

import java.awt.image.BufferedImage;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Bianca Leopoldo Ramos
 */
public class AutomaticImageProcessImplements implements AutomaticImageProcess {

    private AutomaticFrameArchiveProcess frameArchiveProcess;

    public AutomaticImageProcessImplements() {

    }

    @Override
    public Mat applyBlurOnImage(String savingPath, Mat imageMatToBlur, int ksize_width, int ksize_height) {
        Mat dstn = new Mat(imageMatToBlur.size(), imageMatToBlur.type());
        Imgproc.blur(imageMatToBlur.clone(), dstn, new Size(ksize_width, ksize_height));
        boolean success = Imgcodecs.imwrite(savingPath, dstn);
        if (success) {
            return dstn;
        }
        return null;
    }

    @Override
    public Mat applyBinaryOnImage(String savingPath, BufferedImage buffImgToBinary) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Mat applyMorphOnImage(String savingPath, int structuringElementSize, Mat imageToMorph) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Mat drawContoursOnImage(String savingPath, Mat imageToDraw) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AutomaticFrameArchiveProcess generateArchiveContent() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
