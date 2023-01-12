/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.automation.threads.runnable;

import cataovo.opencvlib.automation.automaticImageProcess.AutomaticImageProcess;
import cataovo.opencvlib.automation.automaticImageProcess.AutomaticImageProcessImplements;
import cataovo.entities.Frame;
import cataovo.entities.Palette;
import cataovo.filechooser.handler.FileExtension;
import cataovo.opencvlib.converters.Converter;
import cataovo.opencvlib.wrappers.MatWrapper;
import java.io.File;

/**
 *
 * @author bianc
 */
public class ThreadAutomationAutomaticProcess extends ThreadAutomation {

    private final AutomaticImageProcess imageProcess;

    public ThreadAutomationAutomaticProcess(Palette palette, String savingDirectory, FileExtension finalFileExtension, String parent) {
        super(palette, savingDirectory, finalFileExtension, parent);
        imageProcess = new AutomaticImageProcessImplements();
    }

//    private String createArchiveContent(AutomaticFrameArchiveProcess archiveContent, Frame frame) {
//        StringBuffer writer = new StringBuffer("|");
//        writer.append(frame.getName());
//        writer.append(",");
//        writer.append(archiveContent.getTotalOfEggs());
//        writer.append(archiveContent.getMainPointsInEgg());
//        return writer.toString();
//    }

    private StringBuffer createImagesContent(String destination, Frame frame) {
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
        
        return imageProcess.generateAutomaticProcessmentRelatory();
    }

    @Override
    protected StringBuffer createContent() {
        StringBuffer imagesDestination;
        StringBuffer writeContentFinalFile = new StringBuffer(palette.getDirectory().getName());
        File fileImagesDestination;
        for (Frame frame : palette.getFrames()) {
            imagesDestination = new StringBuffer(savingDirectory).append("/cataovo/").append(palette.getDirectory().getName()).append("/auto/").append(getDateTime("dd-MM-yyyy_HH-mm"));
            imagesDestination.append("/");
            imagesDestination.append(frame.getName());
            fileImagesDestination = new File(imagesDestination.toString());
            if (!fileImagesDestination.exists()) {
                fileImagesDestination.mkdirs();
            }
            // wait(100)
            // Lançar as outras threads
            writeContentFinalFile.append(createImagesContent(imagesDestination.toString(), frame));
        }
        return writeContentFinalFile;
    }
    
    

}