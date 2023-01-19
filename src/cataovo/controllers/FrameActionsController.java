/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.controllers;

import cataovo.entities.Point;
import cataovo.exceptions.DirectoryNotValidException;
import javax.swing.Icon;

/**
 * Controls the actions in a {@link cataovo.entities.Frame}.
 *
 * @author Bianca Leopoldo Ramos.
 */
public interface FrameActionsController {

    /**
     * Chooses the format to paint the {@link cataovo.entities.Frame} whether is a dot or a rectangle
     *
     * @param point the point clicked in the frame
     * @return the image correspondent to the quantoty of clicks in the frame.
     * @throws DirectoryNotValidException
     * @throws CloneNotSupportedException
     * @see cataovo.utils.frameUtils.FrameActionsUtils#drawCircle(cataovo.opencvlib.wrappers.PointWrapper) 
     * @see cataovo.utils.frameUtils.FrameActionsUtils#drawRectangle(cataovo.opencvlib.wrappers.RectWrapper) 
     */
    public Icon paintFormat(Point point) throws DirectoryNotValidException, CloneNotSupportedException;

    /**
     * Removes the last demarked {@link cataovo.entities.Region}.
     *
     * @return the image updated
     * @throws DirectoryNotValidException
     * @see cataovo.utils.frameUtils.FrameActionsUtils#updateGridsOnFrame() 
     */
    public Icon removeLastRegion() throws DirectoryNotValidException;

    /**
     * Captures a subframe in the current selected {@link cataovo.entities.Region} to focus in the
     * application.
     *
     * @param pointClick the point clicked in the frame
     * @return the captured subframe
     * @throws DirectoryNotValidException
     * @see cataovo.utils.frameUtils.FrameActionsUtils#captureSubframe(cataovo.entities.Point, cataovo.entities.Point) 
     */
    public Icon captureSubframe(Point pointClick) throws DirectoryNotValidException;

}
