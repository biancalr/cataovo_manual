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
import cataovo.opencvlib.converters.Converter;
import cataovo.opencvlib.imageFrameUtils.FramePrimaryUtils;
import cataovo.opencvlib.wrappers.PointWrapper;
import cataovo.opencvlib.wrappers.RectWrapper;
import cataovo.resources.MainResources;
import javax.swing.Icon;
import javax.swing.ImageIcon;

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
        frameUtils = new FramePrimaryUtils();
    }

    @Override
    public Icon paintFormat(Point point) throws DirectoryNotValidException, CloneNotSupportedException {
        switch (clickCount) {
            case 0 -> {
                clickCount++;
                this.initialPoint = new Point(point.getX(), point.getY());
                return paintDotOnFrame(this.initialPoint);
            }
            case 1 -> {
                clickCount = 0;
                return paintGridOnFrame(new Region(this.initialPoint, new Point(point.getX(), point.getY())));
            }
            default -> {
                return null;
            }
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
        this.frameUtils = new FramePrimaryUtils(MainResources.getInstance().getCurrentFrame().clone());
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
        this.frameUtils = new FramePrimaryUtils(MainResources.getInstance().getCurrentFrame().clone());
        RectWrapper rw = new RectWrapper(region);
        Icon icon = frameUtils.drawRectangle(rw);
        MainResources.getInstance().getCurrentFrame().getRegionsContainingEggs().addAll(frameUtils.getFrame().getRegionsContainingEggs());
        return icon;
    }

    /**
     *
     * @return @throws cataovo.exceptions.DirectoryNotValidException
     */
    @Override
    public Icon removeLastRegion() throws DirectoryNotValidException {
        int size = MainResources.getInstance().getCurrentFrame().getRegionsContainingEggs().size();
        if (size == 0) {
            return new ImageIcon(Converter.getInstance().convertMatToImg(frameUtils.updateGridsOnFrame()).get());
        } else {
            MainResources.getInstance().getCurrentFrame().getRegionsContainingEggs().remove(
                    (Region) MainResources.getInstance().getCurrentFrame().getRegionsContainingEggs().toArray()[size - 1]
            );
            return new ImageIcon(Converter.getInstance().convertMatToImg(frameUtils.updateGridsOnFrame()).get());
        }
    }

    /**
     * 
     * @param pointClick
     * @return
     * @throws DirectoryNotValidException 
     */
    @Override
    public Icon captureSubframe(Point pointClick) throws DirectoryNotValidException {
        Icon subframeImage = null;
        if (clickCount == 0 && initialPoint != null) {
            this.frameUtils = new FramePrimaryUtils(MainResources.getInstance().getCurrentFrame());
            subframeImage = this.frameUtils.captureSubframe(this.initialPoint, pointClick);
        }
        return subframeImage;
    }

}
