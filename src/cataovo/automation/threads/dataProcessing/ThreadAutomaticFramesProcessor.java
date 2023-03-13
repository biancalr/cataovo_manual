/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.automation.threads.dataProcessing;

import cataovo.constants.Constants;
import cataovo.entities.Frame;
import cataovo.opencvlib.converters.Converter;
import cataovo.opencvlib.wrappers.MatWrapper;
import java.util.Queue;
import java.util.logging.Logger;

/**
 * This thread is responsible for batch processing a
 * {@link cataovo.entities.Frame}. This processment is responsible for find each
 * egg present in the frame.
 *
 * @author Bianca Leopoldo Ramos.
 */
public class ThreadAutomaticFramesProcessor extends DataProcessingThreadAutomation {

    /**
     * Logging for ThreadAutomaticFramesProcessor.
     */
    private static final Logger LOG = Logger.getLogger(ThreadAutomaticFramesProcessor.class.getName());

    public ThreadAutomaticFramesProcessor(Queue<Frame> frames, String destination) {
        super(frames, destination);
    }

    /**
     * <p>
     * Sequence that processes each {@link cataovo.entities.Frame} of a
     * {@link cataovo.entities.Palette}.</p>
     *
     * <p>
     * Explicação das operações a serem aplicadas. </p>
     *
     * @param frame the current frame to process
     * @return a text containing the quanity of eggs of Aedes found in the
     * frame, and a List of some of the points that make part of the eggs
     * contours.
     * @see cataovo.opencvlib.automation.imageProcessing.AutomaticImageProcess
     */
    @Override
    public StringBuffer startSequence(Frame frame) {
        MatWrapper current = new MatWrapper(frame);
        String dstny = destination + "/" + frame.getName();
        // blur
        current.setOpencvMat(imageProcess.applyBlurOnImage(dstny + Constants.BLUR_PNG,
                current.getOpencvMat(), 5, 5));

        // binary
        current.setOpencvMat(imageProcess.applyBinaryOnImage(dstny + Constants.BINARY_PNG,
                Converter.getInstance().convertMatToPng(current).get()));

        // morphology
        current.setOpencvMat(imageProcess.applyMorphOnImage(dstny + Constants.MORPH_PNG,
                17, 30, 2, current.getOpencvMat()));
        current.setOpencvMat(imageProcess.applyMorphOnImage(dstny + Constants.MORPH_PNG,
                30, 17, 2, current.getOpencvMat()));
        current.setOpencvMat(imageProcess.applyMorphOnImage(dstny + Constants.MORPH_PNG,
                17, 30, 2, current.getOpencvMat()));
        current.setOpencvMat(imageProcess.applyMorphOnImage(dstny + Constants.MORPH_PNG,
                30, 17, 2, current.getOpencvMat()));

        // contours
        current.setOpencvMat(imageProcess.drawContoursOnImage(dstny + Constants.CONTOURS_PNG,
                new MatWrapper(frame).getOpencvMat(), current.getOpencvMat(), 780, 4800));

        return imageProcess.generateAutomaticRelatory();
    }

}
