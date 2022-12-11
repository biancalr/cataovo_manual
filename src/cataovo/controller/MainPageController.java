/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.controller;

import cataovo.entities.Frame;
import cataovo.exceptions.DirectoryNotValidException;
import cataovo.exceptions.ImageNotValidException;
import cataovo.resources.MainPageResources;
import java.awt.Color;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author bibil
 */
public class MainPageController {

    private static final Logger LOG = Logger.getLogger(MainPageController.class.getName());

    public void showFramesOnScreen(JLabel parentName, JLabel parent) throws ImageNotValidException, DirectoryNotValidException {
        Frame frame = MainPageResources.getInstance().getPalette().getFrames().poll();
        MainPageResources.getInstance().setCurrentFrame(frame);
        showFrameOnScreen(parentName, parent, frame);
        parent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        parent.setVisible(true);
    }

    /**
     *
     * @param parentName
     * @param parent
     * @param frame
     */
    public void showFrameOnScreen(JLabel parentName, JLabel parent, Frame frame) {
        LOG.log(Level.INFO, "Presenting the image {0} on screen...", frame.getName());
        parent.setText(null);
        if (frame.getPaletteFrame() instanceof File) {
            File f = (File) frame.getPaletteFrame();
            showImageFile(f);
            parent.setIcon(showImageFile(f));
            parentName.setText(frame.getName());
        }

    }

    /**
     * Receives a file and shows its image at jLabelImage.
     *
     * @param par the File image
     */
    private ImageIcon showImageFile(File file) {
        return new ImageIcon(file.getPath());
    }

    /**
     *
     * @param jLabel1
     * @param jLabel2
     * @throws cataovo.exceptions.ImageNotValidException
     * @throws cataovo.exceptions.DirectoryNotValidException
     */
    public void onFrameFinished(JLabel jLabel1, JLabel jLabel2) throws ImageNotValidException, DirectoryNotValidException {
        LOG.log(Level.INFO, "The frame was analysed. Charging next...");
        if (MainPageResources.getInstance().getPalette().getFrames().iterator().hasNext()) {
            showFramesOnScreen(jLabel1, jLabel2);
        } else {
            LOG.info("You've reached the end of the Queue");
        }

    }

}
