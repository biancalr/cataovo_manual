/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.automation.threads.callable;

import cataovo.entities.Frame;
import cataovo.entities.Palette;
import cataovo.filechooser.handler.FileExtension;
import cataovo.opencvlib.automation.automaticImageProcess.threads.ThreadAutomaticFramesProcessor;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bianc
 */
public class NewThreadAutomationAutomaticProcess extends NewThreadAutomation {

    private ThreadAutomaticFramesProcessor framesProcessor;
    private final StringBuffer imagesDestination;
    private final String dateTime;

    /**
     * 
     * @param palette
     * @param savingDirectory
     * @param extension
     * @param parentName 
     */
    public NewThreadAutomationAutomaticProcess(Palette palette, String savingDirectory, FileExtension extension, String parentName) {
        super(palette, savingDirectory, extension, parentName);
        this.dateTime = super.getDateTime("dd-MM-yyyy_HH-mm");
        this.imagesDestination = new StringBuffer(savingDirectory).append("/cataovo/").append(palette.getDirectory().getName()).append("/auto/").append(dateTime);
    }

    @Override
    protected StringBuffer createContent() {
        StringBuffer result = new StringBuffer();
        palette.getFrames().forEach(frame -> {
            String destination = imagesDestination.toString() + "/" + frame.getName();
            try {
                if (createImagesFolders(destination)) {
                    synchronized (destination) {
                        result.append(processFrameImages(frame, destination));
                    }
                }
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(NewThreadAutomationAutomaticProcess.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return result;
    }

    /**
     *
     * @param frame
     * @param executorService
     * @param result
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private synchronized StringBuffer processFrameImages(Frame frame, String destination) throws ExecutionException, InterruptedException {
        Future<StringBuffer> task;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        StringBuffer result = new StringBuffer();
        framesProcessor = new ThreadAutomaticFramesProcessor(frame, destination, getFileExtension().toString().toLowerCase());
        task = executorService.submit(framesProcessor);
        result.append(task.get());
        executorService.awaitTermination(5, TimeUnit.MILLISECONDS);
        executorService.shutdown();
        notify();
        return result;
    }

    /**
     *
     * @param actualFrame
     * @param paletteDirectory
     * @return
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

}
