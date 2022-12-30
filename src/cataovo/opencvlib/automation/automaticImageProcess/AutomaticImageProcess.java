/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cataovo.opencvlib.automation.automaticImageProcess;

import java.awt.image.BufferedImage;
import org.opencv.core.Mat;

/**
 *
 * @author bianc
 */
public interface AutomaticImageProcess {

    /**
     *
     * @param imageMatToBlur
     * @param savingPath
     * @param ksize_width
     * @param ksize_height
     * @return
     */
    public Mat applyBlurOnImage(String savingPath, Mat imageMatToBlur, int ksize_width, int ksize_height);
    
    /**
     * 
     * @param savingPath
     * @param buffImgToBinary
     * @return 
     */
    public Mat applyBinaryOnImage(String savingPath, BufferedImage buffImgToBinary);

    /**
     *
     * @param savingPath
     * @param structuringElementSize
     * @param imageToMorph
     * @return
     */
    public Mat applyMorphOnImage(String savingPath, int structuringElementSize, Mat imageToMorph);

    /**
     *
     * @param savingPath
     * @param imageToDraw
     * @return
     */
    public Mat drawContoursOnImage(String savingPath, Mat imageToDraw);
    
    /**
     *
     * @return
     */
    public AutomaticFrameArchiveProcess generateArchiveContent();
    
}
