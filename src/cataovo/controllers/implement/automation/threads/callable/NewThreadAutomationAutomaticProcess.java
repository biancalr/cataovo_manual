/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.controllers.implement.automation.threads.callable;

import cataovo.constants.Constants;
import cataovo.entities.Frame;
import cataovo.entities.Palette;
import cataovo.resources.fileChooser.handler.FileExtension;
import cataovo.controllers.implement.automation.threads.callable.automaticImageProcess.ThreadAutomaticFramesProcessor;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Bianca Leopoldo Ramos
 */
public class NewThreadAutomationAutomaticProcess extends NewThreadAutomation {

    /**
     * Thread that actually rules the processment of the files.
     */
    private ThreadAutomaticFramesProcessor framesProcessor;
    /**
     * Defines the directory where the resultant images of each transformation
     * will be placed.
     */
    private final StringBuffer imagesDestination;
    /**
     * Datetime will be used to create new folder of each processment.
     */
    private final String dateTime;
    /**
     * .
     * Controls the range of each subquerie related to the main querie.
     */
    private int slotRangeControl = 0;

    /**
     * <p>
     * The thread starts the processing of each frame of a palette.</p>
     *
     * @param palette the palette to be processed
     * @param savingDirectory the directory where the results will be saved.
     * @param extension referes to the relatory's file extension where the text
     * data will be saved.
     * @param parentName relates the tabName to the type of processing of a
     * palette: Manual or Automatic. Also helps to create folders of each
     * processing type.
     */
    public NewThreadAutomationAutomaticProcess(Palette palette, String savingDirectory, FileExtension extension, String parentName) {
        super(palette, savingDirectory, extension, parentName);
        this.dateTime = super.getDateTime("dd-MM-yyyy_HH-mm");
        this.imagesDestination = new StringBuffer(savingDirectory).append("/cataovo/").append(palette.getDirectory().getName()).append("/auto/").append(dateTime);
    }

    @Override
    protected StringBuffer createContent() {
        StringBuffer result = new StringBuffer(palette.getPathName()).append(Constants.QUEBRA_LINHA);
        Queue<Frame> splitted;
        String destination = imagesDestination.toString();
        do {
            splitted = split(palette.getFrames(), 4);
            try {
                for (Frame frame : splitted) {
                    destination = imagesDestination.toString() + "/" + frame.getName();
                    createImagesFolders(destination);

                }

                synchronized (destination) {
                    result.append(processFrameImages(splitted, imagesDestination.toString()));
                }
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(NewThreadAutomationAutomaticProcess.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }

        } while (slotRangeControl != palette.getPaletteSize());
        return result;
    }

    /**
     * <p>
     * Calls the thread wich is going to processesescha frame.</p>
     *
     * @param frames collection of frames to be analysed.
     * @param destination folder to save the resulted images after the
     * transformations
     * @return a string containing the of quantity of eggs in a frame and a
     * percentage of the total points for later checks
     * @throws ExecutionException
     * @throws InterruptedException
     * @see #createContent()
     * @see #split(java.util.Queue, int)
     * @see #framesProcessor
     */
    private synchronized StringBuffer processFrameImages(Queue<Frame> frames, String destination) throws ExecutionException, InterruptedException {
        Future<StringBuffer> task;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        StringBuffer result = new StringBuffer();
        framesProcessor = new ThreadAutomaticFramesProcessor(frames, destination);
        task = executorService.submit(framesProcessor);
        result.append(task.get());
        executorService.awaitTermination(1, TimeUnit.MILLISECONDS);
        executorService.shutdown();
        notify();
        return result;
    }

    /**
     * <p>
     * Creates a folder corresponding to each frame in order to save their
     * resulted images transformations.</p>
     *
     * @param destination where the folders will be addressed
     * @return <code>True</code> if the folders were created successfully,
     * <code>False</code> otherwise.
     * @throws InterruptedException
     * @see #createContent()
     */
    private synchronized boolean createImagesFolders(String destination) throws InterruptedException {
        File fileImagesDestination;
        fileImagesDestination = new File(destination);
        if (!fileImagesDestination.exists()) {
            fileImagesDestination.mkdirs();
            return true;
        }
        return false;
    }

    /**
     * <p>
     * Splits the main queue into subqueue according to the range.</p>
     *
     * @param toSplit the queue to split.
     * @param range the range of values in each subqueue to be created.
     * @return a subqueue based on the range.
     * @see #createContent()
     */
    private Queue split(Queue toSplit, int range) {

        Queue<Frame> subQueue = new LinkedList<>();
        Object[] auxToSplit = toSplit.toArray();
        int limit = (slotRangeControl + range) <= auxToSplit.length ? (slotRangeControl + range) : auxToSplit.length;
        if (slotRangeControl != limit) {
            for (int i = this.slotRangeControl; i < limit; i++) {
                Object object = auxToSplit[i];
                subQueue.offer((Frame) object);
            }
        }
        slotRangeControl = (slotRangeControl + range) <= auxToSplit.length ? (slotRangeControl + range) : auxToSplit.length;
        return subQueue;
    }

}
