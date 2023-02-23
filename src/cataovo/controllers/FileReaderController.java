/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cataovo.controllers;

import cataovo.opencvlib.wrappers.PointWrapper;
import cataovo.opencvlib.wrappers.RectWrapper;
import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author Bianca Leopoldo Ramos
 */
public interface FileReaderController {

    /**
     *
     * @param frameName
     * @param report
     * @return
     * @throws java.io.FileNotFoundException
     * @throws NumberFormatException
     */
    public List<RectWrapper> getRegionsInFrameFile(String frameName, String report) throws FileNotFoundException, NumberFormatException;

    /**
     *
     * @param frameName
     * @param report
     * @return
     * @throws java.io.FileNotFoundException
     * @throws NumberFormatException
     */
    public List<PointWrapper> getPointsInFrameFile(String frameName, String report) throws FileNotFoundException, NumberFormatException;

    /**
     *
     * @param report
     * @return
     * @throws java.io.FileNotFoundException
     */
    public StringBuilder readFullFileContent(String report) throws FileNotFoundException;

}
