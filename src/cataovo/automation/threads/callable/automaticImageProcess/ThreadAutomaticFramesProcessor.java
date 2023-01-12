/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.automation.threads.callable.automaticImageProcess;

import cataovo.entities.Frame;
import cataovo.opencvlib.automation.automaticImageProcess.AutomaticImageProcess;
import cataovo.opencvlib.automation.automaticImageProcess.AutomaticImageProcessImplements;
import cataovo.opencvlib.converters.Converter;
import cataovo.opencvlib.wrappers.MatWrapper;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This thread is responsible for batch processing a frame. This processment is
 * responsible for find each egg present in the frame.
 *
 * @author bianc
 */
public class ThreadAutomaticFramesProcessor implements Callable<StringBuffer> {

    private static final Logger LOG = Logger.getLogger(ThreadAutomaticFramesProcessor.class.getName());
    private final Frame frame;
    private final String destination;
    private final AutomaticImageProcess imageProcess;

    /**
     * Instanciates the thread responsable for the automatic counting.
     *
     * @param frame
     * @param destination
     */
    public ThreadAutomaticFramesProcessor(Frame frame, String destination) {
        this.frame = frame;
        this.destination = destination;
        this.imageProcess = new AutomaticImageProcessImplements();
    }

    /**
     * Initiates the sequence of steps stablished to fing the desired objects in
     * a frame. The serired objects in this case are the eggs of Aedes aegypti.
     *
     * @return the folder where the images and the relatory ware saved.
     * @throws Exception
     */
    @Override
    public StringBuffer call() throws Exception {
        LOG.log(Level.INFO, "Starting Sequence...");
        return startSequence();
    }

    /**
     * Sequence that processes each frame of a Palette.
     *
     * @return a text containing the quanity of eggs of Aedes found in the
     * frame, and a List of some of the points that make part of the eggs
     * contours
     */
    private StringBuffer startSequence() {
        MatWrapper current = new MatWrapper(frame);

        // blur
        current.setOpencvMat(imageProcess.applyBlurOnImage(destination + "/blur.png", current.getOpencvMat(), 5, 5));

        // binary
        current.setOpencvMat(imageProcess.applyBinaryOnImage(destination + "/binary.png",
                Converter.getInstance().convertMatToPng(current).get()));

        // morphology
        current.setOpencvMat(imageProcess.applyMorphOnImage(destination + "/morph.png", 11, 25, 2, current.getOpencvMat()));
//        current.setOpencvMat(imageProcess.applyMorphOnImage(destination + "/morph.png", 25, 11, 2, current.getOpencvMat()));

        // contours
        current.setOpencvMat(imageProcess.drawContoursOnImage(destination + "/contours.png", new MatWrapper(frame).getOpencvMat(), current.getOpencvMat(), 780, 4800));

        return imageProcess.generateAutomaticProcessmentRelatory();
    }
    
    

}
