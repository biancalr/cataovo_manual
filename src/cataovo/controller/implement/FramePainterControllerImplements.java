/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.controller.implement;

import cataovo.controller.FramePainterController;
import cataovo.entities.Point;
import cataovo.entities.Region;
import cataovo.exceptions.DirectoryNotValidException;
import cataovo.opencvlib.imageFrameUtils.FramePrimaryUtils;
import cataovo.opencvlib.wrappers.PointWrapper;
import cataovo.opencvlib.wrappers.RectWrapper;
import cataovo.resources.MainPageResources;
import javax.swing.Icon;

/**
 *
 * @author bibil
 */
public class FramePainterControllerImplements implements FramePainterController {

    private int clickCount;
    private Point initialPoint = null;
    private FramePrimaryUtils frameUtils;

    public FramePainterControllerImplements() {
        clickCount = 0;
        this.initialPoint = new Point(0, 0);
        frameUtils = new FramePrimaryUtils();
    }

    @Override
    public Icon paintFormat(Point point) throws DirectoryNotValidException, CloneNotSupportedException {
        switch (clickCount) {
            case 0:
                clickCount++;
                this.initialPoint = new Point(point.getX(), point.getY());
                return paintDotOnFrame(this.initialPoint);
            case 1:
                clickCount = 0;
                return paintGridOnFrame(new Region(this.initialPoint, new Point(point.getX(), point.getY())));
            default:
                return null;
        }
    }

    /**
     *
     * @param point
     * @return
     * @throws DirectoryNotValidException
     * @throws CloneNotSupportedException
     */
    private Icon paintDotOnFrame(Point point) throws DirectoryNotValidException, CloneNotSupportedException {
        this.frameUtils = new FramePrimaryUtils(MainPageResources.getInstance().getCurrentFrame().clone());
        PointWrapper pw = new PointWrapper(point);
        return frameUtils.drawCircle(pw);
    }

    /**
     *
     * @param region
     * @return
     * @throws DirectoryNotValidException
     * @throws CloneNotSupportedException
     */
    private Icon paintGridOnFrame(Region region) throws DirectoryNotValidException, CloneNotSupportedException {
        this.frameUtils = new FramePrimaryUtils(MainPageResources.getInstance().getCurrentFrame().clone());
        RectWrapper rw = new RectWrapper(region);
        Icon icon = frameUtils.drawRectangle(rw);
        MainPageResources.getInstance().getCurrentFrame().getRegionsContainingEggs().addAll(frameUtils.getFrame().getRegionsContainingEggs());
        return icon;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean removeLastRegion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
