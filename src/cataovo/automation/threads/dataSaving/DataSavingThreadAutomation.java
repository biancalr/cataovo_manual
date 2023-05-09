/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.automation.threads.dataSaving;

import cataovo.entities.Palette;
import cataovo.enums.FileExtension;
import cataovo.externals.writers.csvWriter.CsvFileWriter;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 *
 * @author Bianca Leopoldo Ramos
 */
public abstract class DataSavingThreadAutomation implements Callable<String> {

    private CsvFileWriter CsvFileWriter;
    /**
     * Logging for CsvFileWriter
     */
    private static final Logger LOG = Logger.getLogger(DataSavingThreadAutomation.class.getName());
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

    public DataSavingThreadAutomation(Palette palette, String savingDirectory, FileExtension fileExtension, String parentTabName, String dateTime) {
        this.palette = palette;
        this.savingDirectory = savingDirectory;
        this.fileExtension = fileExtension;
        this.parentTabName = parentTabName;
        this.dateTime = dateTime;
    }
    
    @Override
    public String call() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}
