/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.controllers.implement;

import cataovo.controllers.automation.threads.callable.NewThreadAutomation;
import cataovo.controllers.automation.threads.callable.NewThreadAutomationManualProcess;
import cataovo.constants.Constants;
import cataovo.controllers.FileSelectionController;
import cataovo.entities.Frame;
import cataovo.entities.Palette;
import cataovo.exceptions.DirectoryNotValidException;
import cataovo.exceptions.ImageNotValidException;
import cataovo.resources.fileChooser.UI.MyFileChooserUI;
import cataovo.resources.fileChooser.handler.FileExtension;
import cataovo.resources.MainResources;
import cataovo.controllers.automation.threads.runnable.ThreadAutomation;
import cataovo.controllers.automation.threads.runnable.ThreadAutomationManualProcess;
import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;

/**
 * Controls the interactions with the files outside of the Application.
 *
 * @author bibil
 */
public class FileSelectionControllerImplement implements FileSelectionController {

    private static final Logger LOG = Logger.getLogger(FileSelectionControllerImplement.class.getName());
    private final MyFileChooserUI fileChooser;
    private ThreadAutomation createRelatories;
    private NewThreadAutomation newCreateRelatories;
    private String finalRelatoryDestination;

    public FileSelectionControllerImplement() throws DirectoryNotValidException {
        fileChooser = MainResources.getInstance().getFileChooserUI();
        finalRelatoryDestination = "";
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
        if (!MainResources.getInstance().getPanelTabHelper().isIsActualTabProcessing()) {
            switch (actionCommand) {
                case Constants.ITEM_ACTION_COMMAND_ABRIR_PASTA -> {
                    return actionCommandOpenFolder(isADirectoryOnly, parent);
                }
                case Constants.ITEM_ACTION_COMMAND_SELECIONAR_PASTA_DESTINO -> {
                    return actionCommandSetSavingFolder(isADirectoryOnly, parent);
                }
                case Constants.ITEM_ACTION_COMMAND_SALVAR_ARQUIVO_FINAL -> {
                    return actionCommandNewSaveFinalFile(MainResources.getInstance().getPanelTabHelper().getTabName());
                }
                default -> {
                    LOG.log(Level.WARNING, "Not implemented yet {0}", actionCommand);
                    return false;
                }
            }

        }
        return false;
    }

    /**
     * The behavior for the action ACTION_COMMAND_ABRIR_PASTA. Sets a Palette to
     * work with.
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

            MainResources.getInstance().setPalette(setNewPalette(file));
            MainResources.getInstance().setPaletteToSave(new Palette());
            MainResources.getInstance().getPaletteToSave().setDirectory(MainResources.getInstance().getPalette().getDirectory());
            if (parent instanceof JTabbedPane jTabbedPane) {
                if (jTabbedPane.getSelectedIndex() == 0) {
                    MainResources.getInstance().getPalette().getFrames().poll();
                }
                MainResources.getInstance().adjustPanelTab(jTabbedPane, true);
            }
            return true;
        }
        return false;
    }

    /**
     * The behavior for the action ACTION_COMMAND_SELECIONAR_PASTA_DESTINO. Sets
     * a folder where the final report will be saved.
     *
     * @param isADirectoryOnly
     * @param parent
     * @return
     * @throws DirectoryNotValidException
     */
    private boolean actionCommandSetSavingFolder(boolean isADirectoryOnly, Component parent) throws DirectoryNotValidException {
        LOG.log(Level.INFO, "Setting a new saving Folder.");
        File file = fileChooser.dialogs(JFileChooser.OPEN_DIALOG, isADirectoryOnly, parent);
        if (file != null && file.exists()) {
            // Set the folder where the result will be saved.
            MainResources.getInstance().setSavingFolder(file);
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
            MainResources.getInstance().setCurrent(selectedFile.getAbsolutePath());
            if (selectedFile.isDirectory()) {
                pal = new Palette(selectedFile);
                pal.setFrames(setPaletteFrames(selectedFile.listFiles()));
                MainResources.getInstance().setCurrentFrame(pal.getFrames().peek());
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
        Collection<File> colection = MainResources.getInstance().getFileListHandler().normalizeFilesOnAList(listFiles, FileExtension.PNG);
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
     * @return <code>True</code> in case of success. <code> False </code>
     * otherwise.
     */
    private boolean actionCommandSaveFinalFile(String parent) throws DirectoryNotValidException {
        try {
            LOG.log(Level.INFO, "Final file save: start");
            this.createRelatories = new ThreadAutomationManualProcess(
                    MainResources.getInstance().getPaletteToSave(),
                    MainResources.getInstance().getSavingFolder().getPath(),
                    FileExtension.CSV, parent);
            createRelatories.start();
            createRelatories.join();
            return true;
        } catch (InterruptedException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Save the final relatories in the computer.
     *
     * @return <code>True</code> in case of success. <code> False </code>
     * otherwise.
     */
    private boolean actionCommandNewSaveFinalFile(String parent) {
        LOG.log(Level.INFO, "Final file save: start");
        try {
            Future<String> task;
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            this.newCreateRelatories = new NewThreadAutomationManualProcess(
                    MainResources.getInstance().getPaletteToSave(),
                    MainResources.getInstance().getSavingFolder().getPath(),
                    FileExtension.CSV,
                    parent);
            task = executorService.submit(newCreateRelatories);
            this.finalRelatoryDestination = task.get();
            executorService.awaitTermination(5, TimeUnit.MILLISECONDS);
            executorService.shutdown();
            return true;
        } catch (DirectoryNotValidException | InterruptedException | ExecutionException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return false;
        }

    }

    /**
     * 
     * @return 
     */
    public MyFileChooserUI getFileChooser() {
        return fileChooser;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String getFinalRelatoryDestination() {
        return finalRelatoryDestination;
    }

}
