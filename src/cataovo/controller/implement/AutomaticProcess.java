/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.controller.implement;

import cataovo.exceptions.DirectoryNotValidException;
import cataovo.filechooser.handler.FileExtension;
import cataovo.resources.MainPageResources;
import cataovo.automation.threads.ThreadAutomationAutomaticProcess;
import cataovo.automation.threads.ThreadAutomation;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import cataovo.controller.AutomaticProcessController;
import javax.swing.JOptionPane;

/**
 *
 * @author bianc
 */
public class AutomaticProcess implements AutomaticProcessController{

    private static final Logger LOG = Logger.getLogger(AutomaticProcess.class.getName());
    private final ThreadAutomation threadAutomaticProcess;
    

    public AutomaticProcess() throws DirectoryNotValidException {
        this.threadAutomaticProcess = new ThreadAutomationAutomaticProcess(MainPageResources.getInstance().getPaletteToSave(),
                    MainPageResources.getInstance().getSavingFolder().getPath(),
                    FileExtension.CSV);
    }
    
    @Override
    public boolean isOnProcessPalette(JLabel labelName, JLabel labelImage) {
        boolean isProcessing = true;
        try {
            this.threadAutomaticProcess.start();
            this.threadAutomaticProcess.join();
            isProcessing = false;
        } catch (InterruptedException ex) {
            LOG.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
            isProcessing = false;
        }
        return isProcessing;
    }
    
}
