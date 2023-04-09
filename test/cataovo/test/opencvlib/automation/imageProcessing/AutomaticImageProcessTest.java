/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.test.opencvlib.automation.imageProcessing;

import cataovo.opencvlib.automation.imageProcessing.AutomaticImageProcess;
import cataovo.opencvlib.automation.imageProcessing.AutomaticImageProcessImplements;
import cataovo.opencvlib.converters.Converter;
import cataovo.opencvlib.wrappers.MatWrapper;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author Bianca Leopoldo Ramos
 */
public class AutomaticImageProcessTest {
    
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        String orgn = "";
        String dstn = "";
        
        AutomaticImageProcess imageProcess = new AutomaticImageProcessImplements();
        
        Mat img = Imgcodecs.imread(orgn);
        Mat finalImage = img.clone();
        
        img = imageProcess.applyBlurOnImage(dstn + "_blur.png", img, 5, 5);
        
        img = imageProcess.applyBinaryOnImage(dstn + "_binary.png", Converter.getInstance().convertMatToPng(new MatWrapper(img, orgn)).get());
        
        img = imageProcess.applyMorphOnImage(dstn + "_morph_1.png", 17, 35, 2, img);
        img = imageProcess.applyMorphOnImage(dstn + "_morph_2.png", 40, 17, 2, img);
//        img = imageProcess.applyMorphOnImage(dstn + "_morph_3.png", 17, 35, 2, img);
//        img = imageProcess.applyMorphOnImage(dstn + "_morph_4.png", 40, 17, 2, img);
        
        finalImage = imageProcess.drawContoursOnImage(dstn + "_contours.png", finalImage, img, 800, 5000);
        
    }
    
}
