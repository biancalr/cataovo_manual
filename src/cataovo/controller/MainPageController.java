/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.controller;

import cataovo.constantes.Constants;
import cataovo.fileChooserHandler.MyFileChooserUI;
import cataovo.resources.MainPageResources;
import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author bibil
 */
public class MainPageController {
    
    MyFileChooserUI fileChooser;
    
    /**
     * Selects an event and an action based on the parameters.
     * 
     * @param actionCommand comand that defines a dialog showing actions
     * @param parent
     * @param isADirectoryOnly <code>True</code> if the selection mode is a <code>DIRECTORY_ONLY</code> or <code>False</code> if the selection mode is a <code>FILE_ONLY</code>
     */
    public void directoryEvent(String actionCommand, Component parent, boolean isADirectoryOnly) {
        System.out.println(actionCommand);
        fileChooser = MainPageResources.getInstance().getFileChooserUI();        
        File file;
        switch (actionCommand){
            case Constants.ACTION_COMMAND_ABRIR_PASTA:
                file = fileChooser.dialogs(JFileChooser.OPEN_DIALOG, isADirectoryOnly, parent);
                MainPageResources.getInstance().setCurrent(file);
                showFilesOnScreen(file);
                break;
            case Constants.ACTION_COMMAND_SELECIONAR_PASTA_DESTINO:
                file = fileChooser.dialogs(JFileChooser.SAVE_DIALOG, isADirectoryOnly, parent);
                MainPageResources.getInstance().setDestinyFolder(file);
                break;
            default:
                System.err.println("Not Suppoted Yet.");
                break;
        }
        
    }

    private void showFilesOnScreen(File file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
