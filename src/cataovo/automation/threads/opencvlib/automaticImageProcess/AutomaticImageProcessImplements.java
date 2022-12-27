/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.automation.threads.opencvlib.automaticImageProcess;

import java.awt.image.BufferedImage;
import org.opencv.core.Mat;

/**
 *
 * @author bianc
 */
public class AutomaticImageProcessImplements implements AutomaticImageProcess{

    private AutomaticFrameArchiveProcess frameArchiveProcess;

    public AutomaticImageProcessImplements() {
        
    }
    
    @Override
    public Mat applyBlurOnImage(String savingPath, Mat imageMatToBlur, int ksize_width, int ksize_height) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
