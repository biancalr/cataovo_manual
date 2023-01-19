/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.controllers.implement;

import cataovo.controllers.automation.threads.runnable.ThreadAutomation;
import cataovo.controllers.automation.threads.runnable.ThreadAutomationAutomaticProcess;
import cataovo.controllers.automation.threads.callable.NewThreadAutomation;
import cataovo.controllers.automation.threads.callable.NewThreadAutomationAutomaticProcess;
import cataovo.controllers.AutomaticProcessorController;
import cataovo.exceptions.DirectoryNotValidException;
import cataovo.resources.fileChooser.handler.FileExtension;
import cataovo.resources.MainResources;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 * Implements the automatic processment controller
 *
 * @author Bianca Leopoldo Ramos
 */
public class AutomaticProcessorControllerImplements implements AutomaticProcessorController {

    private static final Logger LOG = Logger.getLogger(AutomaticProcessorControllerImplements.class.getName());
    
    @Override
    public boolean onAutoProcessPalette(JLabel jLabelImageName, JLabel jLabelImageFrame) {
        try {
            ThreadAutomation automation = 
                    new ThreadAutomationAutomaticProcess(
                            MainResources.getInstance().getPalette(), 
                            MainResources.getInstance().getSavingFolder().getPath(), 
                            FileExtension.CSV, 
                            MainResources.getInstance().getPanelTabHelper().getTabName());
            automation.start();
            automation.join();
            return true;
        } catch (DirectoryNotValidException | InterruptedException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public String onNewAutoProcessPalette(JLabel jLabelImageName, JLabel jLabelImageFrame) {
        try { 
            NewThreadAutomation automation;
            Future<String> task;
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            String result;
            
            automation = new NewThreadAutomationAutomaticProcess(
                    MainResources.getInstance().getPalette(),
                    MainResources.getInstance().getSavingFolder().getPath(),
                    FileExtension.CSV,
                    MainResources.getInstance().getPanelTabHelper().getTabName());
            task = executorService.submit(automation);
            result = task.get();
            executorService.awaitTermination(1, TimeUnit.MILLISECONDS);
            executorService.shutdown();
            return result;
        } catch (DirectoryNotValidException | InterruptedException | ExecutionException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
}