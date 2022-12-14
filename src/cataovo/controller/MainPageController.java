/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.controller;

import cataovo.entities.Frame;
import cataovo.entities.Point;
import cataovo.exceptions.DirectoryNotValidException;
import cataovo.exceptions.ImageNotValidException;
import cataovo.opencvlib.imageFrameUtils.FrameUtils;
import cataovo.opencvlib.wrappers.PointWrapper;
import cataovo.resources.MainPageResources;
import java.awt.Color;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.Icon;

/**
 * Controls the actions with the Frames
 *
 * @author bibil
 */
public class MainPageController {

    private static final Logger LOG = Logger.getLogger(MainPageController.class.getName());
    private FrameUtils frameUtils = null;

    /**
     * Set to the next frame
     *
     * @param parentName
     * @param parent
     * @throws ImageNotValidException
     * @throws DirectoryNotValidException
     */
    public void toNextFrame(JLabel parentName, JLabel parent) throws ImageNotValidException, DirectoryNotValidException {
        Frame frame = MainPageResources.getInstance().getPalette().getFrames().poll();
        MainPageResources.getInstance().setCurrentFrame(frame);
        showFramesOnScreen(parentName, parent, frame);
    }
    
    /**
     * 
     * @param jLabel1
     * @param jLabel2
     * @param frame
     * @throws ImageNotValidException 
     */
    public void showFramesOnScreen(JLabel jLabel1, JLabel jLabel2, Icon frame) throws ImageNotValidException {
        showFrameOnScreen(jLabel1, jLabel2, frame);
    }

    /**
     *
     * @param parentName
     * @param parent
     * @param frame
     * @throws cataovo.exceptions.ImageNotValidException
     */
    public void showFramesOnScreen(JLabel parentName, JLabel parent, Frame frame) throws ImageNotValidException {
        LOG.log(Level.INFO, "Presenting the image {0} on screen...", frame.getName());
        frameUtils = new FrameUtils(frame);
        if (showFrameOnScreen(parentName, parent, frame)) {
            parent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            parent.setVisible(true);
        }
    }

    /**
     * Setts the icons related to each JLabel (image or name)
     *
     * @param parentName
     * @param parent
     * @param frame
     * @return
     */
    private boolean showFrameOnScreen(JLabel parentName, JLabel parent, Object frame) throws ImageNotValidException {
        parent.setText(null);
        if (frame instanceof Frame) {
            Frame fr = (Frame) frame;
            if (fr.getPaletteFrame() != null && fr.getPaletteFrame().exists()) {
                File f = (File) fr.getPaletteFrame();
                showImageFile(f);
                parent.setIcon(showImageFile(f));
                parentName.setText(fr.getName());
                return true;
            }
        } else if (frame instanceof Icon) {
            Icon im = (Icon) frame;
            parent.setIcon(im);
        } else {
            throw new ImageNotValidException("Image type not supported yet. Please try again under another format");
        }
        return false;
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
     * When a Frame was finished its analysis, go to next Frame on Queue.
     *
     * @param jLabel1
     * @param jLabel2
     * @throws cataovo.exceptions.ImageNotValidException
     * @throws cataovo.exceptions.DirectoryNotValidException
     */
    public void onFrameFinished(JLabel jLabel1, JLabel jLabel2) throws ImageNotValidException, DirectoryNotValidException {
        LOG.log(Level.INFO, "The frame was analysed. Charging next...");
        if (MainPageResources.getInstance().getPalette().getFrames().iterator().hasNext()) {
            toNextFrame(jLabel1, jLabel2);
        } else {
            LOG.info("You've reached the end of the Palette.");
        }

    }

    /**
     *
     * @param point
     * @return
     * @throws DirectoryNotValidException
     * @throws CloneNotSupportedException
     */
    public Icon paintDotOnFrame(Point point) throws DirectoryNotValidException, CloneNotSupportedException {
        Frame f = MainPageResources.getInstance().getCurrentFrame().clone();
        PointWrapper pw = new PointWrapper(point);
        return frameUtils.drawCircle(pw, f);
    }

}
