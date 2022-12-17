/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.controller.implement;

import cataovo.constants.Constants;
import cataovo.controller.FileSelectionController;
import cataovo.entities.Frame;
import cataovo.entities.Palette;
import cataovo.exceptions.DirectoryNotValidException;
import cataovo.exceptions.ImageNotValidException;
import cataovo.fileChooser.UI.MyFileChooserUI;
import cataovo.filechooser.handler.FileExtension;
import cataovo.resources.MainPageResources;
import cataovo.threads.ThreadCreateRelatoryArchive;
import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.JFileChooser;

/**
 * Controls the interactions with the files outside of the Application.
 *
 * @author bibil
 */
public class FileSelectionControllerImplement implements FileSelectionController{

    private static final Logger LOG = Logger.getLogger(FileSelectionControllerImplement.class.getName());
    private final MyFileChooserUI fileChooser;
    private ThreadCreateRelatoryArchive createRelatories;

    public FileSelectionControllerImplement() throws DirectoryNotValidException {
        fileChooser = MainPageResources.getInstance().getFileChooserUI();
    }

    /**
     * Selects an event and an action based on the parameters.
     *
     * @param actionCommand comand that defines a dialog showing actions
     * @param parent
     * @param isADirectoryOnly <code>True</code> if the selection mode is a
     * <code>DIRECTORY_ONLY</code> or <code>False</code> if the selection mode
     * is a <code>FILES_AND_DIRECTORIES</code>
     * @return
     * @throws cataovo.exceptions.DirectoryNotValidException
     * @throws cataovo.exceptions.ImageNotValidException
     * @throws java.io.FileNotFoundException
     */
    @Override
    public boolean fileSelectionEvent(String actionCommand, Component parent, boolean isADirectoryOnly) throws DirectoryNotValidException, ImageNotValidException, FileNotFoundException {
        switch (actionCommand) {
            case Constants.ACTION_COMMAND_ABRIR_PASTA:
                return actionCommandOpenFolder(isADirectoryOnly, parent);
            case Constants.ACTION_COMMAND_SELECIONAR_PASTA_DESTINO:
                return actionCommandSetSavingFolder(isADirectoryOnly, parent);
            case Constants.ACTION_COMMAND_SALVAR_ARQUIVO_FINAL:
                return actionCommandSaveFinalFile();
            default:
                LOG.log(Level.WARNING, "Not implemented yet {0}", actionCommand);
                return false;
        }

    }

    /**
     * The behavior for the action ACTION_COMMAND_ABRIR_PASTA.
     * Sets a Palette to work with.
     * 
     * @param isADirectoryOnly
     * @param parent
     * @return
     * @throws FileNotFoundException
     * @throws DirectoryNotValidException
     * @throws ImageNotValidException 
     */
    private boolean actionCommandOpenFolder(boolean isADirectoryOnly, Component parent) throws FileNotFoundException, DirectoryNotValidException, ImageNotValidException {
        File file = fileChooser.dialogs(JFileChooser.OPEN_DIALOG, isADirectoryOnly, parent);
        if (file != null && file.exists()) {
            // Set the palette which represents the folder where the frames are contained
            MainPageResources.getInstance().setPalette(setNewPalette(file));
            MainPageResources.getInstance().setPaletteToSave(new Palette());
            MainPageResources.getInstance().getPaletteToSave().setDirectory(MainPageResources.getInstance().getPalette().getDirectory());
            MainPageResources.getInstance().getPalette().getFrames().poll();
            return true;
        } else {
            return false;
        }
    }

    /**
     * The behavior for the action ACTION_COMMAND_SELECIONAR_PASTA_DESTINO.
     * Sets a folder where the final report will be saved.
     * 
     * @param isADirectoryOnly
     * @param parent
     * @return
     * @throws DirectoryNotValidException 
     */
    private boolean actionCommandSetSavingFolder(boolean isADirectoryOnly, Component parent) throws DirectoryNotValidException{
        LOG.log(Level.INFO, "Setting a new saving Folder.");
        File file = fileChooser.dialogs(JFileChooser.OPEN_DIALOG, isADirectoryOnly, parent);
        if (file != null && file.exists()) {
            // Set the folder where the result will be saved.
            MainPageResources.getInstance().setSavingFolder(file);
            LOG.log(Level.INFO, "A new saving Folder {0}", file);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Set the file to a Palette. Verify if the file is a valid one.
     *
     * @param selectedFile
     * @return a new Palette to start the analisys
     * @throws DirectoryNotValidException
     */
    private Palette setNewPalette(File selectedFile) throws DirectoryNotValidException, ImageNotValidException, FileNotFoundException {
        LOG.log(Level.INFO, "Setting a new Palette...");
        Palette pal = null;
        if (selectedFile.exists()) {
            MainPageResources.getInstance().setCurrent(selectedFile.getAbsolutePath());
            if (selectedFile.isDirectory()) {
                pal = new Palette(selectedFile);
                pal.setFrames(setPaletteFrames(selectedFile.listFiles()));
                MainPageResources.getInstance().setCurrentFrame(pal.getFrames().peek());
            } else {
                throw new DirectoryNotValidException("The selected file is not a directory. Please, choose a directory.");
            }
            LOG.log(Level.INFO, "A new Palette was created with the amount of frames: {0}", pal.getFrames().size());
        } else {
            LOG.log(Level.WARNING, "The selected file doesn't exist. Please, select an existing file.");
            throw new FileNotFoundException("The selected file doesn't exist. Please, select an existing file.");
        }
        return pal;
    }

    /**
     * Set the Frames in a Palette. When a Palette is chosen, their frames must
     * be presented as a Queue. If the chosen file is a directory, there might
     * be nested directories. So these images must be normalized to a sigle
     * queue.
     *
     *
     * @param listFiles
     * @return
     */
    private Queue<Frame> setPaletteFrames(File[] listFiles) throws DirectoryNotValidException, ImageNotValidException {
        Queue<Frame> frames = new LinkedList<>();
        Collection<File> colection = MainPageResources.getInstance().getFileListHandler().normalizeFilesOnAList(listFiles, FileExtension.PNG);
        Frame frame;
        for (File file1 : colection) {
            frame = new Frame(file1.getPath());
            frames.add(frame);
            LOG.log(Level.INFO, "Adding following frame: {0}", frame.getName());
        }
        return frames;
    }

    /**
     * Save the final relatories in the computer.
     * 
     * @return <code>True</code> in case of success. <code> False </code> otherwise.
     */
    private boolean actionCommandSaveFinalFile() throws DirectoryNotValidException {
        try {
            LOG.log(Level.INFO, "Final file save: start");
            this.createRelatories = new ThreadCreateRelatoryArchive(
                    MainPageResources.getInstance().getPaletteToSave(),
                    MainPageResources.getInstance().getSavingFolder().getPath(),
                    FileExtension.CSV);
            createRelatories.start();
            createRelatories.join();
            return true;
        } catch (InterruptedException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public MyFileChooserUI getFileChooser() {
        return fileChooser;
    }

}
