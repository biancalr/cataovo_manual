/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template directory, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.fileChooser.UI;

import cataovo.exceptions.DirectoryNotValidException;
import cataovo.filechooser.handler.FileExtension;
import cataovo.filechooser.handler.FileFilterExtensions;
import cataovo.resources.MainPageResources;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;

/**
 *
 * @author bibil
 */
public class MyFileChooserUI extends JFileChooser{

    private FileExtension extensionType;

    public MyFileChooserUI() {
        super.addChoosableFileFilter(new FileFilterExtensions(extensionType));
    }

    public MyFileChooserUI(File directory, FileExtension extensionType) {
        this.extensionType = extensionType;
        super.addChoosableFileFilter(new FileFilterExtensions(extensionType));
        super.setCurrentDirectory(directory);
    }

    public MyFileChooserUI(File currentDirectory) {
        super.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        super.setCurrentDirectory(currentDirectory);
    }
    
   /**
    * Selects a Selection Dialog to open.
    * 
    * @param dialogType describes the dialog type: <code>JFileChooser.OPEN_DIALOG</code> or <code>JFileChooser.SAVE_DIALOG</code>
    * @param isSelectionDirectory <code>True</code> if the selection mode is a <code>DIRECTORY_ONLY</code> or <code>False</code> if the selection mode is a <code>FILE_ONLY</code>
    * @param parent the parent Component
    * @return the selected file/directory or null if nothing was selected
    * @throws HeadlessException 
     * @throws cataovo.exceptions.DirectoryNotValidException 
    */
    public File dialogs(int dialogType, boolean isSelectionDirectory, Component parent) throws HeadlessException, DirectoryNotValidException{
        File f = null;
        switch(dialogType){
            case JFileChooser.OPEN_DIALOG -> f = openDialog(parent, isSelectionDirectory);
            case JFileChooser.SAVE_DIALOG -> f = saveDialog(parent, isSelectionDirectory);
            default -> {
            }
        }
        return f;
    }  
    
    /**
     * Shows a Open dialog
     * 
     * @param parent the parent Component
     * @param isSelectionDirectory <code>True</code> if the selection mode is a <code>DIRECTORY_ONLY</code> or <code>False</code> if the selection mode is a <code>FILES_AND_DIRECTORIES</code>
     * @return the selected file/directory or null if nothing was selected
     * @throws HeadlessException 
     * @throws cataovo.exceptions.DirectoryNotValidException 
     */
    public File openDialog(Component parent, boolean isSelectionDirectory) throws HeadlessException, DirectoryNotValidException {
        File f = null;
        int returnInterval;
        selectionFileMode(isSelectionDirectory);
        returnInterval = super.showOpenDialog(parent);
        switch (returnInterval) {
            case JFileChooser.APPROVE_OPTION -> {
                f = super.getSelectedFile();
                MainPageResources.getInstance().adjustPanelTab((JTabbedPane)parent, true);
            }
            case JFileChooser.CANCEL_OPTION -> {
                f = null;
                MainPageResources.getInstance().getPanelTabHelper().setIsActualTabProcessing(false);
            }
            case JFileChooser.ERROR_OPTION -> {
                MainPageResources.getInstance().getPanelTabHelper().setIsActualTabProcessing(false);
                throw new HeadlessException("Exception ocurred while openning the dialog box.");
            }
            default -> {
            }
        }
        
        return f;
    }

    /**
     * Selects if what selection mode should appear.
     * 
     * @param isSelectionDirectory <code>True</code> if the selection mode is a <code>DIRECTORY_ONLY</code> or <code>False</code> if the selection mode is a <code>FILES_AND_DIRECTORIES</code>
     */
    private void selectionFileMode(boolean isSelectionDirectory) {
        if (isSelectionDirectory) {
            super.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            super.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        }
    }

    /**
     * Shows a Save dialog
     * 
     * @param parent the parent component
     * @param isSelectionDirectory
     * @return
     * @throws HeadlessException 
     */
    public File saveDialog(Component parent, boolean isSelectionDirectory) throws HeadlessException {
        File f = null;
        int returnInterval;
        
        selectionFileMode(isSelectionDirectory);
        returnInterval = super.showSaveDialog(parent);
        switch (returnInterval) {
            case JFileChooser.APPROVE_OPTION -> f = super.getSelectedFile();
            case JFileChooser.CANCEL_OPTION -> f = null;
            case JFileChooser.ERROR_OPTION -> throw new HeadlessException("Exception ocurred while openning the dialog box.");
            default -> {
            }
        }
        
        return f;
    }

    public FileExtension getExtensionType() {
        return extensionType;
    }

    public void setExtensionType(FileExtension extensionType) {
        this.extensionType = extensionType;
    }
    
    
}
