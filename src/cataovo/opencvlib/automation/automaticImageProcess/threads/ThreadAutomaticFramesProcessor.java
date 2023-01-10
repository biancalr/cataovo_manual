/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.opencvlib.automation.automaticImageProcess.threads;

import cataovo.entities.Frame;
import cataovo.opencvlib.automation.automaticImageProcess.AutomaticImageProcess;
import cataovo.opencvlib.automation.automaticImageProcess.AutomaticImageProcessImplements;
import cataovo.opencvlib.converters.Converter;
import cataovo.opencvlib.wrappers.MatWrapper;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import org.opencv.core.MatOfPoint;

/**
 *
 * @author bianc
 */
public class ThreadAutomaticFramesProcessor implements Callable<StringBuffer> {

    private static final Logger LOG = Logger.getLogger(ThreadAutomaticFramesProcessor.class.getName());
    private int quantityOfEggs = 0;
    private static final double[] WHITE = {255, 255, 255};
    private static final double[] BLACK = {0, 0, 0};
    private List<MatOfPoint> eggsContours;
    private final Frame frame;
    private final String relatoryFileExtension;
    private final String destination;
    private AutomaticImageProcess imageProcess;

    public ThreadAutomaticFramesProcessor(Frame frame, String destination, String fileExtension) {
        this.frame = frame;
        this.destination = destination;
        this.relatoryFileExtension = fileExtension;
        this.imageProcess = new AutomaticImageProcessImplements();
    }

    @Override
    public StringBuffer call() throws Exception {
        return startSequence();
    }

    private StringBuffer startSequence() {
         MatWrapper current = new MatWrapper(frame);
        
        // blur
        current.setOpencvMat(imageProcess.applyBlurOnImage(destination + "/blur.png", current.getOpencvMat(), 5, 5));
        
        // binary
        current.setOpencvMat(imageProcess.applyBinaryOnImage(destination + "/binary.png", 
                Converter.getInstance().convertMatToPng(current).get()));
        
        // morphology
        current.setOpencvMat(imageProcess.applyMorphOnImage(destination + "/morph.png", 11, 25, 2, current.getOpencvMat()));
        current.setOpencvMat(imageProcess.applyMorphOnImage(destination + "/morph.png", 25, 11, 2, current.getOpencvMat()));
        
        // contours
        current.setOpencvMat(imageProcess.drawContoursOnImage(destination + "/contours.png", new MatWrapper(frame).getOpencvMat(), current.getOpencvMat(), 780, 4800));
        
        return new StringBuffer();
    }
    

    
   
    
}
