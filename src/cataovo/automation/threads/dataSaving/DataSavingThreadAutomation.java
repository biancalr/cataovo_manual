/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.automation.threads.dataSaving;

import cataovo.constants.Constants;
import cataovo.entities.Palette;
import cataovo.exceptions.AutomationExecutionException;
import cataovo.resources.fileChooser.handler.FileExtension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
public abstract class DataSavingThreadAutomation implements Callable<String> {

    /**
     * Logging for DataSavingThreadAutomation
     */
    private static final Logger LOG = Logger.getLogger(DataSavingThreadAutomation.class.getName());
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
     *
     */
    protected final String dateTime;

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
     * @param dateTime
     */
    public DataSavingThreadAutomation(Palette palette, String savingDirectory, FileExtension fileExtension, String parentTabName, String dateTime) {
        this.palette = palette;
        this.savingDirectory = savingDirectory;
        this.fileExtension = fileExtension;
        this.parentTabName = parentTabName;
        this.dateTime = dateTime;
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
     * @throws cataovo.exceptions.AutomationExecutionException
     * @see
     * cataovo.automation.threads.dataSaving.NewThreadAutomationAutomaticProcess
     * @see
     * cataovo.automation.threads.dataSaving.NewThreadAutomationManualProcess
     */
    protected abstract StringBuffer createContent() throws AutomationExecutionException;

    /**
     * Creates the relatory wich saves the data of each type of processment.
     *
     * @return the filepath's relatory.
     * @see #createContent()
     */
    private synchronized String createFile() throws Exception {
        StringBuffer sb = new StringBuffer();
        String processingMode = (this.parentTabName == null ? Constants.TAB_NAME_MANUAL_PT_BR == null : this.parentTabName.equals(Constants.TAB_NAME_MANUAL_PT_BR)) ? Constants.NAME_MANUAL : (this.parentTabName == null ? Constants.TAB_NAME_AUTOMATIC_PT_BR == null : this.parentTabName.equals(Constants.TAB_NAME_AUTOMATIC_PT_BR)) ? Constants.NAME_AUTOMATICO : Constants.NAME_AVALIACAO;
        String dstn = palette.getDirectory().getName() + "/" + processingMode + "/" + this.dateTime;
        File directory = new File(savingDirectory + "/cataovo/" + palette.getDirectory().getName() + "/" + processingMode + "/" + this.dateTime);
        final String createdFile = savingDirectory + "/cataovo/" + dstn + "/Relatory." + this.fileExtension.getExtension();
        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (processingMode.equals(Constants.NAME_AUTOMATICO)) {
            sb.append(verifyFileAreadyExistent(createdFile));
        }

        try (FileWriter csvWriter = new FileWriter(createdFile); PrintWriter csvPrinter = new PrintWriter(csvWriter);) {

            sb.append(createContent());
            csvPrinter.print(sb);
            LOG.log(Level.INFO, "The file will be saved under the name: {0}", savingDirectory + "/cataovo/" + dstn);
            return directory.getPath();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return "";
    }

    private StringBuffer verifyFileAreadyExistent(final String createdFile) throws AutomationExecutionException {
        StringBuffer sb = new StringBuffer();
        File f = new File(createdFile);
        LOG.info("Verifying if the file already exists.");
        if (f.exists() && f.isFile()) {
            LOG.info("Reading the existing file to append its previous");
            sb.append(palette.getPathName()).append(Constants.QUEBRA_LINHA);
            sb.append(readFile(f));
        }
        return sb;
    }

    private synchronized StringBuffer readFile(File createdFile) throws AutomationExecutionException {
        StringBuffer sb = new StringBuffer();
        LOG.info("Starting to read the file");
        try (InputStreamReader in = new InputStreamReader(new FileInputStream(createdFile)); BufferedReader csvReader = new BufferedReader(in);) {

            csvReader.lines().filter(l -> !l.contains(palette.getPathName()) && l.length() > 4)
                    .forEachOrdered(l -> sb.append(Constants.QUEBRA_LINHA).append(l));
            sb.append(Constants.QUEBRA_LINHA);

        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
            throw new AutomationExecutionException("Error while reading an existing file.");
        }
        return sb;
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

    /**
     *
     * @return the given dateTime
     */
    public String getDateTime() {
        return dateTime;
    }
}
