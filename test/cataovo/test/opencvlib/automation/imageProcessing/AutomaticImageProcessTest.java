/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.test.opencvlib.automation.imageProcessing;

import cataovo.externals.libs.opencvlib.automation.imageProcessing.AutomaticImageProcess;
import cataovo.externals.libs.opencvlib.automation.imageProcessing.AutomaticImageProcessImplements;
import cataovo.externals.libs.opencvlib.converters.Converter;
import cataovo.externals.libs.opencvlib.wrappers.MatWrapper;
import java.io.File;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author Bianca Leopoldo Ramos
 */
public class AutomaticImageProcessTest {
    
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        final String orign = "C:\\Users\\bianc\\OneDrive\\Documentos\\Paletas\\Placa\\original.png";
        String dstn = "C:/Users/bianc/OneDrive/Documentos/Paletas/teste";
        final String frameName = "/frame_" + orign.substring(orign.lastIndexOf("\\") + 1, orign.lastIndexOf("."));
        File file = new File(dstn);
        int sizeTag = file.listFiles().length + 1;
        
        dstn = dstn + "/teste_" + sizeTag;
        file = new File(dstn);
        if (!file.exists()) {
            file.mkdir();
        }
        
        AutomaticImageProcess imageProcess = new AutomaticImageProcessImplements();
        
        Mat img = Imgcodecs.imread(orign);
        
        Core.copyMakeBorder(img, img, 4, 4, 4, 4, Core.BORDER_CONSTANT, new Scalar(255.0, 255.0, 255.0));
        
        Imgcodecs.imwrite(dstn + frameName + "_1_original.png", img);
        
        
        Mat finalImage = img.clone();
        
        /*
         *  Reduzir o brilho
        */
        img.convertTo(img, -1, 1, -15);
        Imgcodecs.imwrite(dstn + frameName + "_2_brightness.png", img);
        
        /*
         * Aplicar Abertura 
         */
        img = imageProcess.applyMorphOnImage(dstn + frameName + "_3_morph_init.png", 3, 3, 2, img);
        
        
        img = imageProcess.applyBlurOnImage(dstn + frameName+ "_4_blur.png", img, 5, 5);
        
        img = imageProcess.applyBinaryOnImage(dstn + frameName + "_5_binary.png", Converter.getInstance().convertMatToPng(new MatWrapper(img, orign)).get());
        
        img = imageProcess.applyMorphOnImage(dstn + frameName + "_6_morph_1.png", 17, 40, 2, img);
        img = imageProcess.applyMorphOnImage(dstn + frameName+ "_7_morph_2.png", 40, 17, 2, img);
//        img = imageProcess.applyMorphOnImage(dstn + "_morph_3.png", 17, 35, 2, img);
//        img = imageProcess.applyMorphOnImage(dstn + "_morph_4.png", 40, 17, 2, img);
        

        Core.copyMakeBorder(img, img, 1, 1, 1, 1, Core.BORDER_CONSTANT, new Scalar(255.0, 255.0, 255.0));
        
        finalImage = imageProcess.drawContoursOnImage(dstn + frameName + "_8_contours.png", finalImage, img, 800, 5500);
        
        System.out.println(finalImage);
    }
    
}
