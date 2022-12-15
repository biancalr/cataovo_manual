/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.opencvlib.imageFrameUtils;

import cataovo.entities.Frame;
import cataovo.entities.Point;
import cataovo.entities.Region;
import cataovo.opencvlib.converters.Converter;
import cataovo.opencvlib.wrappers.MatWrapper;
import cataovo.opencvlib.wrappers.PointWrapper;
import cataovo.opencvlib.wrappers.RectWrapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author bibil
 */
public abstract class FrameUtils {

    protected static final Logger LOG = Logger.getLogger(FrameUtils.class.getName());
    protected PointWrapper pointWrapper;
    protected RectWrapper rectWrapper;
    protected MatWrapper matWrapper;
    protected Frame frame = null;
    protected ImageUtils imageUtils;

    public FrameUtils(Frame frame) {
        pointWrapper = new PointWrapper();
        rectWrapper = new RectWrapper();
        matWrapper = new MatWrapper(frame);
        this.frame = frame;
        this.imageUtils = new ImageUtilsImplements();
    }

    public FrameUtils() {
        pointWrapper = new PointWrapper();
        rectWrapper = new RectWrapper();
        matWrapper = new MatWrapper();
        this.imageUtils = new ImageUtilsImplements();
    }

    public PointWrapper getPointWrapper() {
        return pointWrapper;
    }

    public RectWrapper getRectWrapper() {
        return rectWrapper;
    }

    public MatWrapper getMatWrapper() {
        return matWrapper;
    }

    /**
     *
     * @param pw
     * @return
     */
    protected abstract Icon drawCircle(PointWrapper pw);

    /**
     *
     * @param rw
     * @return
     */
    protected abstract Icon drawRectangle(RectWrapper rw);

    protected Icon dot(PointWrapper pw) {
        LOG.log(Level.INFO, "Starting..");
//        mockRegionsInFrame();
        if (!this.frame.getRegionsContainingEggs().isEmpty()) {
            this.matWrapper = preprareGrids();
        } else {
            matWrapper = Converter.getInstance().convertImageFrameToMat(frame);
        }
        matWrapper.setOpencvMat(imageUtils.circle(pw.getOpencvPoint(), matWrapper.getOpencvMat()).clone());
        return new ImageIcon(Converter.getInstance().convertMatToImg(matWrapper).get());
    }

    protected Icon square(RectWrapper rw) {
        LOG.log(Level.INFO, "Starting..");
//        mockRegionsInFrame();
        if (!this.frame.getRegionsContainingEggs().isEmpty()) {
            this.matWrapper = preprareGrids();
        }
        pointWrapper = new PointWrapper(
                new Point(rw.getRegion().getInitialPoint().getX(),
                        rw.getRegion().getInitialPoint().getY()));
        PointWrapper pw2 = new PointWrapper(new Point(
                Math.abs(rw.getRegion().getInitialPoint().getX() - rw.getRegion().getWidth()),
                Math.abs(rw.getRegion().getInitialPoint().getY() - rw.getRegion().getHeight())));
        this.frame.getRegionsContainingEggs().add(captureGrid(pointWrapper, pw2));
        matWrapper.setOpencvMat(imageUtils.rectangle(
                pointWrapper.getOpencvPoint(), pw2.getOpencvPoint(), matWrapper.getOpencvMat()));
        return new ImageIcon(Converter.getInstance().convertMatToImg(matWrapper).get());
    }

    /**
     *
     * @param beginGrid
     * @param endGrid
     * @return
     */
    private Region captureGrid(PointWrapper beginGrid, PointWrapper endGrid) {
        return new RectWrapper(imageUtils.captureGridMat(beginGrid.getOpencvPoint(), endGrid.getOpencvPoint())).getRegion();
    }

    /**
     *
     * @return
     */
    protected MatWrapper preprareGrids() {
        MatWrapper mw = new MatWrapper(this.frame);
        this.frame.getRegionsContainingEggs().stream().forEach((r) -> {
            PointWrapper pw1 = new PointWrapper(
                    new Point(r.getInitialPoint().getX(),
                            r.getInitialPoint().getY()));
            PointWrapper pw2 = new PointWrapper(new Point(
                    Math.abs(r.getInitialPoint().getX() - r.getWidth()),
                    Math.abs(r.getInitialPoint().getY() - r.getHeight())));
            MatWrapper wrapper = new MatWrapper();
            wrapper.setOpencvMat(mw.getOpencvMat());
            mw.setOpencvMat(imageUtils.rectangle(
                    pw1.getOpencvPoint(), pw2.getOpencvPoint(), wrapper.getOpencvMat()).clone());
        });
        return mw;
    }

    private void mockRegionsInFrame() {
        this.frame.getRegionsContainingEggs().add(new Region(75, 83, new Point(268, 459)));
        this.frame.getRegionsContainingEggs().add(new Region(65, -40, new Point(300, -169)));
    }

}
