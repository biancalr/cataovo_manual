
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.controller.implement;

import cataovo.automation.threads.ThreadAutomation;
import cataovo.automation.threads.ThreadAutomationAutomaticProcess;
import cataovo.controller.MainPageController;
import cataovo.entities.Frame;
import cataovo.exceptions.DirectoryNotValidException;
import cataovo.exceptions.ImageNotValidException;
import cataovo.filechooser.handler.FileExtension;
import cataovo.resources.MainResources;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.Icon;
import javax.swing.JTabbedPane;

/**
 * Controls the actions with the Frames
 *
 * @author bibil
 */
public class MainPageControllerImplements implements MainPageController {

    private static final Logger LOG = Logger.getLogger(MainPageControllerImplements.class.getName());

    public MainPageControllerImplements() {

    }

    /**
     * Set to the next frame
     *
     * @param parentName
     * @param parent
     * @throws ImageNotValidException
     * @throws DirectoryNotValidException
     */
    @Override
    public void toNextFrame(JLabel parentName, JLabel parent) throws ImageNotValidException, DirectoryNotValidException {
        Frame frame = MainResources.getInstance().getPalette().getFrames().poll();
        MainResources.getInstance().setCurrentFrame(frame);
        showFrameOnScreen(parentName, parent, frame);
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
        parent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        parent.setVisible(true);
        if (frame instanceof Frame fr) {
                LOG.log(Level.INFO, "Presenting the image {0} on screen...", fr.getName());
                if (fr.getPaletteFrame() != null && fr.getPaletteFrame().exists()) {
                    File f = (File) fr.getPaletteFrame();
                    showImageFile(f);
                    parent.setIcon(showImageFile(f));
                    parentName.setText(fr.getName());
                    return true;
                }
        } else if (frame instanceof Icon icon) {
                parent.setIcon(icon);
                return true;
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
    @Override
    public void onFrameFinished(JLabel jLabel1, JLabel jLabel2) throws ImageNotValidException, DirectoryNotValidException {
        LOG.log(Level.INFO, "The frame was analysed. Charging next...");
        MainResources.getInstance().getPaletteToSave().getFrames()
                .offer(MainResources.getInstance().getCurrentFrame());
        toNextFrame(jLabel1, jLabel2);
    }

    /**
     * From a given tab, it shows the frames selected
     * @param tabComponent
     * @param parentNameLabel
     * @param parentLabel
     * @throws ImageNotValidException
     * @throws DirectoryNotValidException
     */
    @Override
    public void showFramesOnSelectedTabScreen(Component tabComponent, JLabel parentNameLabel, JLabel parentLabel, Object frame) throws ImageNotValidException, DirectoryNotValidException {
        if (tabComponent instanceof JTabbedPane jTabbedPane) { // if the component is another type of component just add another conditional
            switch (jTabbedPane.getSelectedIndex()) {
                case 0 -> showFrameOnScreen(parentNameLabel, parentLabel, frame);
                case 1 -> showFrameOnScreen(parentNameLabel, parentLabel, frame);
                default -> throw new AssertionError();
            }
        }
    }

    /**
     *
     * @param jLabel4
     * @param jLabel3
     * @return
     */
    @Override
    public boolean onAutoProcessPalette(JLabel jLabel4, JLabel jLabel3) {
//        ThreadAutomation automation = new ThreadAutomationAutomaticProcess(MainResources.getInstance().getPalette(), MainResources.getInstance().getSavingFolder().getPath(), FileExtension.CSV, MainResources.getInstance().getPanelTabHelper().getTabName());
        return false;
        
    }

}
