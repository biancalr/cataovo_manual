/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.controller;

import cataovo.constants.Constants;
import cataovo.entities.Frame;
import cataovo.entities.Palette;
import cataovo.entities.Point;
import cataovo.exceptions.DirectoryNotValidException;
import cataovo.exceptions.ImageNotValidException;
import cataovo.fileChooserHandler.MyFileChooserUI;
import cataovo.fileHandler.FileExtension;
import cataovo.opencvlib.converters.Converter;
import cataovo.opencvlib.frameUtil.ImageFrameUtil;
import cataovo.opencvlib.wrappers.MatWrapper;
import cataovo.resources.MainPageResources;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

/**
 *
 * @author bibil
 */
public class MainPageController {

    private static final Logger LOG = Logger.getLogger(MainPageController.class.getName());
    private Palette palette;
    private Frame currentFrame;
    private cataovo.entities.Point beginPoint = null;

    private MatWrapper matWrapper;

    private final MyFileChooserUI fileChooser;
    private final Converter converter = new Converter();

    public MainPageController() throws DirectoryNotValidException {
        fileChooser = MainPageResources.getInstance().getFileChooserUI();
    }

    /**
     * Selects an event and an action based on the parameters.
     *
     * @param actionCommand command that defines a dialog showing actions
     * @param parent the parent component to be setted
     * @param isADirectoryOnly <code>True</code> if the selection mode is a
     * <code>DIRECTORY_ONLY</code> or <code>False</code> if the selection mode
     * is a <code>FILES_AND_DIRECTORIES</code>
     * @throws cataovo.exceptions.DirectoryNotValidException
     * @throws cataovo.exceptions.ImageNotValidException
     * @throws java.io.FileNotFoundException
     */
    public void fileSelectionEvent(String actionCommand, Component parent, boolean isADirectoryOnly) throws DirectoryNotValidException, ImageNotValidException, FileNotFoundException {
        Optional<File> optional;
        switch (actionCommand) {
            case Constants.ACTION_COMMAND_ABRIR_PASTA:
                optional = Optional.ofNullable(fileChooser.dialogs(JFileChooser.OPEN_DIALOG, isADirectoryOnly, parent));
                if (optional.isPresent()) {
                    // Set the palette which represents the folder where the frames are contained
                    palette = setNewPalette(optional.get());
                }
                break;
            case Constants.ACTION_COMMAND_SELECIONAR_PASTA_DESTINO:
                LOG.log(Level.INFO, "Setting a new saving Folder.");
                optional = Optional.ofNullable(fileChooser.dialogs(JFileChooser.OPEN_DIALOG, isADirectoryOnly, parent));
                if (optional.isPresent()) {
                    // Set the folder where the result will be saved.
                    MainPageResources.getInstance().setSavingFolder(optional.get());
                }
                LOG.log(Level.INFO, "A new saving Folder {}", optional.get());
                break;
            case Constants.ACTION_COMMAND_SALVAR_ARQUIVO_FINAL:
                saveFinalFile();
                break;
            default:
                System.err.println("Not Suppoted Yet.");
                break;
        }

    }

    public void controlFilesOnScreen(JLabel parentName, JLabel parent) throws ImageNotValidException {
        currentFrame = palette.getFrames().poll();
        showFrameOnScreen(parentName, parent, currentFrame);
        parent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        parent.setVisible(true);
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
            } else {
                Frame frame = new Frame(selectedFile.getAbsolutePath());
                Queue queue = new LinkedList<>();
                queue.offer(frame);
                pal = new Palette(MainPageResources.getInstance().getCurrent(), queue);
                pal.setFrames(queue);
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
        Frame frame = null;
        for (File file1 : colection) {
            frame = new Frame(file1.getPath());
            frames.add(frame);
            LOG.log(Level.INFO, "Adding following frame: {0}", frame.getName());
        }
        return frames;
    }

    /**
     *
     * @param parentName
     * @param parent
     * @param frame
     */
    public void showFrameOnScreen(JLabel parentName, JLabel parent, Frame frame) {
        LOG.log(Level.INFO, "Presenting the image {0} on screen...", frame.getName());
        parent.setText(null);
        File f = (File) frame.getPaletteFrame();
        parent.setIcon(showImageFile(f));
        parentName.setText(frame.getName());

    }

    public void showFrameOnScreen(JLabel parent, MatWrapper currentMat) throws ImageNotValidException {
        parent.setIcon(showImageMat(currentMat));
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
     * Receives a mat to show its image at jLabelImage.
     *
     * @param mat the Mat image
     * @throws IOException
     */
    private ImageIcon showImageMat(MatWrapper mat) throws ImageNotValidException {
        Optional<Image> convertMatToImg = converter.convertMatToImg(mat);
        if (convertMatToImg.isPresent()) {
            return new ImageIcon(convertMatToImg.get());
        }
        throw new ImageNotValidException("Erro ao converter imagem de OpenCV.Mat para " + FileExtension.JPG);
    }

    private void saveFinalFile() {
        LOG.log(Level.INFO, "Final file save: start");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public MatWrapper prepareFramePainting(int numberOfClicks, Point point) throws ImageNotValidException, CloneNotSupportedException {
        MatWrapper mw = null;
        switch (numberOfClicks) {
            case 1:
                break;
            case 2:
                break;
            default:
                LOG.log(Level.SEVERE, "It is only valid 2 clicks to draw a region");
                break;
        }
        return mw;
    }

    public Palette getPalette() {
        return palette;
    }

    public void setPalette(Palette palette) {
        this.palette = palette;
    }

    public MyFileChooserUI getFileChooser() {
        return fileChooser;
    }

    public void controlFilesOnScreen(JLabel jLabel2, MatWrapper matImage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
