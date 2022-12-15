/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.controller;

import cataovo.controller.MainPageController;
import cataovo.entities.Frame;
import cataovo.entities.Point;
import cataovo.entities.Region;
import cataovo.exceptions.DirectoryNotValidException;
import cataovo.exceptions.ImageNotValidException;
import cataovo.opencvlib.imageFrameUtils.FramePrimaryUtils;
import cataovo.opencvlib.wrappers.PointWrapper;
import cataovo.opencvlib.wrappers.RectWrapper;
import cataovo.resources.MainPageResources;
import java.awt.Color;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.Icon;

/**
 *
 * @author bibil
 */
public interface FramePainterController {
    
    /**
     * Chooses the format to paint the frame whether is a dot or a rectangle
     * 
     * @param point
     * @return
     * @throws DirectoryNotValidException
     * @throws CloneNotSupportedException 
     */
    public Icon paintFormat(Point point) throws DirectoryNotValidException, CloneNotSupportedException;
    
}
