/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.controller.automation.threads.callable;

import cataovo.constants.Constants;
import cataovo.entities.Palette;
import cataovo.resources.fileChooser.handler.FileExtension;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * The thread starts the processing of a palette according to the type of
 * processing.
 *
 * @author Bianca Leopoldo Ramos
 */
public abstract class NewThreadAutomation implements Callable<String> {

    /**
     * Logging for NewThreadAutomation
     */
    private static final Logger LOG = Logger.getLogger(NewThreadAutomation.class.getName());
    /**
     * The palette to be processed.
     */
    protected Palette palette;
    /**
     * The directory where the results will be saved.
     */
    protected String savingDirectory;
    /**
     * Referes to the relatory's file extension where the text data will be
     * saved.
     */
    protected FileExtension fileExtension;
    /**
     * Relates the tabName to the type of processing of a palette: Manual or
     * Automatic. Also helps to create folders of each processing type.
     */
    private final String parentTabName;

    /**
     * <p>
     * The thread starts the processing of each frame of a palette.</p>
     *
     * @param palette the palette to be processed
     * @param savingDirectory the directory where the results will be saved.
     * @param fileExtension referes to the relatory's file extension where the
     * text data will be saved.
     * @param parentTabName relates the tabName to the type of processing of a
     * palette: Manual or Automatic.
     */
    public NewThreadAutomation(Palette palette, String savingDirectory, FileExtension fileExtension, String parentTabName) {
        this.palette = palette;
        this.savingDirectory = savingDirectory;
        this.fileExtension = fileExtension;
        this.parentTabName = parentTabName;
    }

    @Override
    public String call() throws Exception {
        return createFile();
    }

    /**
     * Responsible for creating the contents of each folder needed to save the
     * resulted products
     *
     * @return the content of the file;
     * @see cataovo.controller.automation.threads.callable.NewThreadAutomationAutomaticProcess
     * @see cataovo.controller.automation.threads.callable.NewThreadAutomationManualProcess
     */
    protected abstract StringBuffer createContent();

    /**
     * Creates the relatory wich saves the data of each type of processment.
     *
     * @return the filepath's relatory.
     * @see #createContent()
     */
    private synchronized String createFile() {
        StringBuffer sb = new StringBuffer();
        String processingMode = (this.parentTabName == null ? Constants.TAB_NAME_MANUAL == null : this.parentTabName.equals(Constants.TAB_NAME_MANUAL)) ? "manual" : (this.parentTabName == null ? Constants.TAB_NAME_AUTOMATICO == null : this.parentTabName.equals(Constants.TAB_NAME_AUTOMATICO)) ? "auto" : "result";
        String dstn = palette.getDirectory().getName() + "/" + processingMode + "/" + getDateTime("dd-MM-yyyy_HH-mm") + "/" + "Relatory";
        File directory = new File(savingDirectory + "/cataovo/" + palette.getDirectory().getName() + "/" + processingMode + "/" + getDateTime("dd-MM-yyyy_HH-mm"));
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try ( FileWriter csvWriter = new FileWriter(savingDirectory + "/cataovo/" + dstn + "." + this.fileExtension.toString().toLowerCase());  PrintWriter csvPrinter = new PrintWriter(csvWriter);) {

            sb.append(createContent());
            csvPrinter.print(sb);
            LOG.log(Level.INFO, "The file will be saved under the name: {0}", savingDirectory + "/cataovo/" + dstn + "." + this.fileExtension.toString().toLowerCase());
            return directory.getPath();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return "";
    }

    /**
     * Calculates the date and the time.
     *
     * @param datePattern the pattern to return the date
     * @return date and time according to the the datePattern
     */
    protected String getDateTime(String datePattern) {
        DateFormat dateFormat = new SimpleDateFormat(datePattern);
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     *
     * @return palette
     */
    public Palette getPalette() {
        return palette;
    }

    /**
     *
     * @param palette the palette to set
     */
    public void setPalette(Palette palette) {
        this.palette = palette;
    }

    /**
     *
     * @return savingDirectory
     */
    public String getSavingDirectory() {
        return savingDirectory;
    }

    /**
     *
     * @param savingDirectory the savingDirectory to set.
     */
    public void setSavingDirectory(String savingDirectory) {
        this.savingDirectory = savingDirectory;
    }

    /**
     *
     * @return fileExtension
     */
    public FileExtension getFileExtension() {
        return fileExtension;
    }

    /**
     *
     * @param fileExtension the fileExtension to set.
     */
    public void setFileExtension(FileExtension fileExtension) {
        this.fileExtension = fileExtension;
    }

}
