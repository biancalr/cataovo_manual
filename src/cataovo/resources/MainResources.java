/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.resources;

import cataovo.entities.Frame;
import cataovo.entities.Palette;
import cataovo.exceptions.DirectoryNotValidException;
import cataovo.resources.fileChooser.UI.MyFileChooserUI;
import cataovo.resources.fileChooser.handler.MyFileListHandler;
import java.io.File;
import java.util.logging.Level;
import javax.swing.filechooser.FileSystemView;
import java.util.logging.Logger;
import javax.swing.JTabbedPane;

/**
 * Resources used in the applications as a whole.
 *
 * @author Bianca Leopoldo Ramos
 */
public class MainResources {

    private static final Logger LOG = Logger.getLogger(MainResources.class.getName());
    private final MyFileChooserUI fileChooserUI;
    private MyFileListHandler fileListHandler;
    private String current;
    private Palette palette;
    private Palette paletteToSave;
    private Frame currentFrame;
    private File savingFolder;
    private static volatile MainResources MAIN_PAGE_RESOURCES;
    private final PanelTabHelper panelTabHelper;
    // Fixar ordem dos relatórios: file[0] deve ser o relatório de contagem manual, file[1] deve ser o relatório de contagem automática
    private String[] reports;

    public MainResources() throws DirectoryNotValidException {
        current = FileSystemView.getFileSystemView().getHomeDirectory().getPath();
        savingFolder = getFileFolder(new File(current));
        fileChooserUI = new MyFileChooserUI(new File(current));
        panelTabHelper = new PanelTabHelper(false, 0, "Manual");
    }

    public static MainResources getInstance() throws DirectoryNotValidException {
        MainResources PAGE_RESOURCES = MainResources.MAIN_PAGE_RESOURCES;
        if (PAGE_RESOURCES == null) {
            synchronized (MainResources.class) {
                PAGE_RESOURCES = MainResources.MAIN_PAGE_RESOURCES;
                if (PAGE_RESOURCES == null) {
                    MainResources.MAIN_PAGE_RESOURCES = PAGE_RESOURCES = new MainResources();
                }
            }
        }
        return PAGE_RESOURCES;
    }

    public File getSavingFolder() throws DirectoryNotValidException {
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
        if (this.fileListHandler == null) {
            this.fileListHandler = new MyFileListHandler();
        }
        return fileListHandler;
    }

    public void setFileListHandler(MyFileListHandler fileListHandler) {
        if (this.fileListHandler == null) {
            this.fileListHandler = new MyFileListHandler();
        }
        this.fileListHandler = fileListHandler;
    }

    public Palette getPalette() {
        if (this.palette == null) {
            this.palette = new Palette();
        }
        return palette;
    }

    public void setPalette(Palette palette) {
        if (this.palette == null) {
            this.palette = new Palette();
        }
        this.palette = palette;
    }

    public Palette getPaletteToSave() {
        if (this.paletteToSave == null) {
            this.paletteToSave = new Palette();
        }
        return paletteToSave;
    }

    public void setPaletteToSave(Palette paletteToSave) {
        if (this.paletteToSave == null) {
            this.paletteToSave = new Palette();
        }
        this.paletteToSave = paletteToSave;
    }

    public Frame getCurrentFrame() {
        if (this.currentFrame == null) {
            this.currentFrame = new Frame();
        }
        return currentFrame;
    }

    public void setCurrentFrame(Frame currentFrame) {
        if (this.currentFrame == null) {
            this.currentFrame = new Frame();
        }
        this.currentFrame = currentFrame;
    }

    public PanelTabHelper getPanelTabHelper() {
        return panelTabHelper;
    }

    public String[] getReports() {
        if (this.reports == null) {
            this.reports = new String[2];
        }
        return reports;
    }

    public void setReports(String[] reports) {
        if (this.reports == null) {
            this.reports = new String[2];
        }
        this.reports = reports;
    }

    public void addReport(String report, int position) throws ArrayIndexOutOfBoundsException {
        if (this.reports == null) {
            this.reports = new String[2];
        }
        if (position < reports.length) {
            if (reports[position] == null) {
                reports[position] = report;
            } else {
                throw new ArrayIndexOutOfBoundsException("Posição ocupada");
            }
        } else {
            throw new ArrayIndexOutOfBoundsException("Relatórios já foram selecionados.");
        }
    }

    public void adjustPanelTab(JTabbedPane pane, boolean isActualTabProcessing) {
        panelTabHelper.setIsActualTabProcessing(isActualTabProcessing);
        panelTabHelper.setTabIndex(pane.getSelectedIndex());
        panelTabHelper.setTabName(pane.getTitleAt(pane.getSelectedIndex()));
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
