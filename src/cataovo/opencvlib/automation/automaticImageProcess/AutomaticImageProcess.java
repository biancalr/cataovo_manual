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
     * @param structuringElementWidth
     * @param structuringElementHeight
     * @param morphologicalOperation <ul><li><code>MORPH_ERODE = 0,</code></li><li><code>MORPH_DILATE = 1,</code></li> <li><code>MORPH_OPEN = 2,</code></li><li><code>MORPH_CLOSE = 3,</code></li><li><code>MORPH_GRADIENT = 4,</code></li><li><code>MORPH_TOPHAT = 5,</code></li><li><code>MORPH_BLACKHAT = 6,</code></li><li><code>MORPH_HITMISS = 7;</code></li></ul>
     * @param imageToMorph
     * @return
     */
    public Mat applyMorphOnImage(String savingPath, int structuringElementWidth, int structuringElementHeight, int morphologicalOperation, Mat imageToMorph);

    /**
     *
     * @param savingPath
     * @param imageToDraw
     * @param imgToFindContours
     * @param minSizeArea
     * @param maxSizeArea
     * @return
     */
    public Mat drawContoursOnImage(String savingPath, Mat imageToDraw, Mat imgToFindContours, int minSizeArea, int maxSizeArea);
    
    /**
     *
     * @return
     */
    public AutomaticFrameArchiveProcess generateArchiveContent();
    
}
