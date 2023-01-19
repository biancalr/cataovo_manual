/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.controllers;

import cataovo.exceptions.DirectoryNotValidException;
import cataovo.exceptions.ImageNotValidException;
import java.awt.Component;
import java.io.FileNotFoundException;

/**
 * Controls the file selection from outside of the aplication to deal with it.
 *
 * @author Bianca Leopoldo Ramos.
 */
public interface FileSelectionController {

    /**
     * Selects an event and an action based on the parameters.
     *
     * @param actionCommand comand that defines a dialog showing actions. 
     * @param parent the component
     * @param isADirectoryOnly <code>True</code> if the selection mode is a
     * <code>DIRECTORY_ONLY</code> or <code>False</code> if the selection mode
     * is a <code>FILES_AND_DIRECTORIES</code>
     * @return the selected file
     * @throws cataovo.exceptions.DirectoryNotValidException
     * @throws cataovo.exceptions.ImageNotValidException
     * @throws java.io.FileNotFoundException
     * @see cataovo.constants.Constants
     */
    public boolean fileSelectionEvent(String actionCommand, Component parent, boolean isADirectoryOnly) throws DirectoryNotValidException, ImageNotValidException, FileNotFoundException;

    /**
     *
     * @return the final relatory's path.
     */
    public String getFinalRelatoryDestination();

}
