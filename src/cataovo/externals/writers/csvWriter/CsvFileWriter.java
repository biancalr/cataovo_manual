/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.externals.writers.csvWriter;

import cataovo.constants.Constants;
import cataovo.entities.Palette;
import cataovo.enums.ProcessingMode;
import cataovo.exceptions.AutomationExecutionException;
import cataovo.enums.FileExtension;
import cataovo.exceptions.FileCsvWriterException;
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
public abstract class CsvFileWriter implements Callable<String> {

    /**
     * Logging for CsvFileWriter
     */
    private static final Logger LOG = Logger.getLogger(CsvFileWriter.class.getName());
    /**
     * The palette to be processed.
     */
    protected final Palette palette;
    /**
     * The directory where the results will be saved.
     */
    protected final String savingDirectory;
    /**
     * Referes to the relatory's file extension where the text data will be
     * saved.
     */
    protected final FileExtension fileExtension;
    /**
     * Relates the tabName to the type of processing of a palette: Manual or
     * Automatic. Also helps to create folders of each processing type.
     */
    private final String parentTabName;
    /**
     * date and time captured when the process begin to create a relatory folder
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
    public CsvFileWriter(Palette palette, final String savingDirectory, final FileExtension fileExtension, final String parentTabName, final String dateTime) {
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
     * Responsible for creating the contents of each report needed to save the
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
        String processingMode = (this.parentTabName == null ? Constants.TAB_NAME_MANUAL_PT_BR == null : this.parentTabName.equals(Constants.TAB_NAME_MANUAL_PT_BR)) ? ProcessingMode.MANUAL.getProcessingMode() : (this.parentTabName == null ? Constants.TAB_NAME_AUTOMATIC_PT_BR == null : this.parentTabName.equals(Constants.TAB_NAME_AUTOMATIC_PT_BR)) ? ProcessingMode.AUTOMATIC.getProcessingMode() : ProcessingMode.EVALUATION.getProcessingMode();
        String dstn = palette.getDirectory().getName() + "/" + processingMode + "/" + this.dateTime;
        File directory = new File(savingDirectory + Constants.APPLICATION_FOLDER + palette.getDirectory().getName() + "/" + processingMode + "/" + this.dateTime);
        final String createdFile = savingDirectory + Constants.APPLICATION_FOLDER + dstn + "/" + Constants.REPORT_FILE_NAME + "." + this.fileExtension.getExtension();
        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (processingMode.equals(ProcessingMode.AUTOMATIC.getProcessingMode())) {
            sb.append(verifyFileAreadyExistent(createdFile));
        }

        try (FileWriter csvWriter = new FileWriter(createdFile); PrintWriter csvPrinter = new PrintWriter(csvWriter);) {

            sb.append(createContent());
            csvPrinter.print(sb);
            LOG.log(Level.INFO, "The file will be saved under the name: {0}", savingDirectory + "/cataovo/" + dstn);
            return directory.getPath();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            throw new FileCsvWriterException(e.getMessage());
        }
        
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

    private synchronized StringBuffer readFile(final File createdFile) throws AutomationExecutionException {
        LOG.info("Starting to read the file");
        try (InputStreamReader in = new InputStreamReader(new FileInputStream(createdFile)); 
                BufferedReader csvReader = new BufferedReader(in);) {
            return readContent(csvReader);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
            throw new AutomationExecutionException("Error while reading an existing file.");
        }
    }

    private StringBuffer readContent(final BufferedReader csvReader) {
        StringBuffer sb = new StringBuffer();
        csvReader.lines().filter(l -> !l.contains(palette.getPathName()) && l.length() > 4)
                .forEachOrdered(l -> sb.append(Constants.QUEBRA_LINHA).append(l));
        sb.append(Constants.QUEBRA_LINHA);
        return sb;
    }

    /**
     *
     * @return palette
     */
    public Palette getPalette() {
        return palette;
    }
}
