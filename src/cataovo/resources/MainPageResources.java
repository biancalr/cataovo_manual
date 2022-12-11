/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.resources;

import cataovo.entities.Frame;
import cataovo.entities.Palette;
import cataovo.exceptions.DirectoryNotValidException;
import cataovo.fileChooserHandler.MyFileChooserUI;
import cataovo.fileHandler.MyFileListHandler;
import java.io.File;
import java.util.logging.Level;
import javax.swing.filechooser.FileSystemView;
import java.util.logging.Logger;

/**
 *
 * @author bibil
 */
public class MainPageResources {
    
    private static final Logger LOG = Logger.getLogger(MainPageResources.class.getName());
    private final MyFileChooserUI fileChooserUI;
    private MyFileListHandler fileListHandler;
    private String current;
    private Palette palette;
    private Frame currentFrame;
    private File savingFolder;
    private static volatile MainPageResources MAIN_PAGE_RESOURCES;

    public MainPageResources() throws DirectoryNotValidException {
        current = FileSystemView.getFileSystemView().getHomeDirectory().getPath();
        fileChooserUI = new MyFileChooserUI(new File(current));
        savingFolder = getFileFolder(new File(current));
        fileListHandler = new MyFileListHandler();
        palette = new Palette();
    }
    
    public static MainPageResources getInstance() throws DirectoryNotValidException{
        MainPageResources PAGE_RESOURCES = MainPageResources.MAIN_PAGE_RESOURCES;
        if (PAGE_RESOURCES == null) {
            synchronized (MainPageResources.class){
                PAGE_RESOURCES = MainPageResources.MAIN_PAGE_RESOURCES;
                if (PAGE_RESOURCES == null) {
                    MainPageResources.MAIN_PAGE_RESOURCES = PAGE_RESOURCES = new MainPageResources();
                }
            }
        }
        return PAGE_RESOURCES;
    }

    public File getSavingFolder() {
        return savingFolder;
    }

    public void setSavingFolder(File savingFolder) throws DirectoryNotValidException {
        this.savingFolder = getFileFolder(savingFolder);
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public MyFileChooserUI getFileChooserUI() {
        return fileChooserUI;
    }

    public MyFileListHandler getFileListHandler() {
        return fileListHandler;
    }

    public void setFileListHandler(MyFileListHandler fileListHandler) {
        this.fileListHandler = fileListHandler;
    }

    public Palette getPalette() {
        return palette;
    }

    public void setPalette(Palette palette) {
        this.palette = palette;
    }

    public Frame getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(Frame currentFrame) {
        this.currentFrame = currentFrame;
    }
    
    public final File getFileFolder(File file) throws DirectoryNotValidException {
        if (!file.exists()) {
            LOG.log(Level.SEVERE, "This method needs an existing file. The parameter cannot be null or inexistent");
            throw new DirectoryNotValidException("This method needs an existing file. The parameter cannot be null or inexistent");
        } else {
            if (file.isDirectory()) {
                LOG.log(Level.INFO, "Selecting a folder: {0}", file);
                return file;
            } else {
                LOG.log(Level.INFO, "Selecting the folder: {}", file);
                String selected = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("\\") - 1);
                return new File(selected);
            }
        }
    }
    
}
