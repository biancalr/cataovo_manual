/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.controllers.automation.threads.callable.automaticImageProcess;

import cataovo.constants.Constants;
import cataovo.entities.Frame;
import cataovo.opencvlib.automation.automaticImageProcess.AutomaticImageProcess;
import cataovo.opencvlib.automation.automaticImageProcess.AutomaticImageProcessImplements;
import cataovo.opencvlib.converters.Converter;
import cataovo.opencvlib.wrappers.MatWrapper;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This thread is responsible for batch processing a
 * {@link cataovo.entities.Frame}. This processment is responsible for find each
 * egg present in the frame.
 *
 * @author Bianca Leopoldo Ramos.
 */
public class ThreadAutomaticFramesProcessor implements Callable<StringBuffer> {

    /**
     * Logging for ThreadAutomaticFramesProcessor.
     */
    private static final Logger LOG = Logger.getLogger(ThreadAutomaticFramesProcessor.class.getName());
    /**
     * the {@link cataovo.entities.Frame} to be processed.
     */
    private final Frame frame;
    /**
     * The a slot of the total frames in a palette to be processed.
     */
    private Queue<Frame> frames;
    /**
     * Where the frames wll be saved.
     */
    private final String destination;
    /**
     * Contains the methods to process a frame.
     */
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
     * The thread responsable for the automatic processing.
     *
     * @param frames The a slot of the total {@link cataovo.entities.Frame}s in
     * a palette to be processed.
     * @param destination Where the frames wll be saved.
     */
    public ThreadAutomaticFramesProcessor(Queue<Frame> frames, String destination) {
        this.frame = new Frame();
        this.frames = frames;
        this.destination = destination;
        this.imageProcess = new AutomaticImageProcessImplements();
    }

    /**
     * Initiates the sequence of steps stablished to fing the desired objects in
     * a {@link cataovo.entities.Frame} . The serired objects in this case are
     * the eggs of Aedes aegypti.
     *
     * @return the folder where the images and the relatory ware saved.
     * @throws Exception
     * @see
     * cataovo.controllers.automation.threads.callable.NewThreadAutomationAutomaticProcess
     */
    @Override
    public StringBuffer call() throws Exception {
        LOG.log(Level.INFO, "Starting Sequence...");
        StringBuffer result = new StringBuffer();
        for (Frame f : frames) {
            result.append(Constants.QUEBRA_LINHA).append(f.getName()).append(",");
            result.append(startSequence(f));
        }
        return result;
    }

    /**
     * <p>
     * Sequence that processes each {@link cataovo.entities.Frame} of a
     * {@link cataovo.entities.Palette}.</p>
     *
     * @return a text containing the quanity of eggs of Aedes found in the
     * frame, and a List of some of the points that make part of the eggs
     * contours.
     * @see
     * cataovo.opencvlib.automation.automaticImageProcess.AutomaticImageProcess
     */
    private StringBuffer startSequence(Frame frame) {
        MatWrapper current = new MatWrapper(frame);
        String dstny = destination + "/" + frame.getName();
        // blur
        current.setOpencvMat(imageProcess.applyBlurOnImage(dstny + "/blur.png",
                current.getOpencvMat(), 5, 5));

        // binary
        current.setOpencvMat(imageProcess.applyBinaryOnImage(dstny + "/binary.png",
                Converter.getInstance().convertMatToPng(current).get()));

        // morphology
        current.setOpencvMat(imageProcess.applyMorphOnImage(dstny + "/morph.png",
                17, 30, 2, current.getOpencvMat()));
        current.setOpencvMat(imageProcess.applyMorphOnImage(dstny + "/morph.png",
                30, 17, 2, current.getOpencvMat()));

        // contours
        current.setOpencvMat(imageProcess.drawContoursOnImage(dstny + "/contours.png",
                new MatWrapper(frame).getOpencvMat(), current.getOpencvMat(), 780, 4800));

        return imageProcess.generateAutomaticRelatory();
    }

}
