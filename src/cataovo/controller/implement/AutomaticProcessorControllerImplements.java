/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.controller.implement;

import cataovo.automation.threads.ThreadAutomation;
import cataovo.automation.threads.ThreadAutomationAutomaticProcess;
import cataovo.controller.AutomaticProcessorController;
import cataovo.exceptions.DirectoryNotValidException;
import cataovo.filechooser.handler.FileExtension;
import cataovo.resources.MainResources;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author bianc
 */
public class AutomaticProcessorControllerImplements implements AutomaticProcessorController {

    private static final Logger LOG = Logger.getLogger(AutomaticProcessorControllerImplements.class.getName());
    
    @Override
    public boolean onAutoProcessPalette(JLabel jLabel4, JLabel jLabel3) {
        try {
            ThreadAutomation automation = new ThreadAutomationAutomaticProcess(MainResources.getInstance().getPalette(), MainResources.getInstance().getSavingFolder().getPath(), FileExtension.CSV, MainResources.getInstance().getPanelTabHelper().getTabName());
            automation.start();
            automation.join();
            return true;
        } catch (DirectoryNotValidException | InterruptedException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
