/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.automation.threads;

import cataovo.opencvlib.automation.automaticImageProcess.AutomaticFrameArchiveProcess;
import cataovo.opencvlib.automation.automaticImageProcess.AutomaticImageProcess;
import cataovo.opencvlib.automation.automaticImageProcess.AutomaticImageProcessImplements;
import cataovo.entities.Frame;
import cataovo.entities.Palette;
import cataovo.filechooser.handler.FileExtension;
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

    private String createArchiveContent(AutomaticFrameArchiveProcess archiveContent, Frame frame) {
        StringBuffer writer = new StringBuffer("|");
        writer.append(frame.getName());
        writer.append(",");
        writer.append(archiveContent.getTotalOfEggs());
        writer.append(archiveContent.getMainPointsInEgg());
        return writer.toString();
    }

    private AutomaticFrameArchiveProcess createImagesContent(String destination, Frame frame) {
        MatWrapper matWrapper = new MatWrapper(frame);
        matWrapper.setOpencvMat(imageProcess.applyBlurOnImage(destination + "/blur.png", matWrapper.getOpencvMat(), 5, 5));
        
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
            writeContentFinalFile.append(createArchiveContent(createImagesContent(imagesDestination.toString(), frame), frame));
        }
        return writeContentFinalFile;
    }

}