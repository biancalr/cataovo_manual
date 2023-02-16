/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cataovo.controllers;

import cataovo.entities.Palette;
import cataovo.opencvlib.wrappers.PointWrapper;
import cataovo.opencvlib.wrappers.RectWrapper;
import java.io.File;
import java.util.List;

/**
 *
 * @author bianc
 */
public interface FileReaderController {

    /**
     *
     * @param frameName
     * @param report
     * @return
     */
    public List<RectWrapper> getRegionsInFrameFile(String frameName, String report);

    /**
     * 
     * @param frameName
     * @param report
     * @return 
     */
    public List<PointWrapper> getPointsInFrameFile(String frameName, String report);

    /**
     * 
     * @param report
     * @return 
     */
    public StringBuilder readFullFileContent(String report);
    
        
}
