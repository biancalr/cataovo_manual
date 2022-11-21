/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.opencvlib.wrappers;

import org.opencv.core.Rect;

/**
 *
 * @author bibil
 */
public class RectWrapper {
    
    private Rect rect;

    public RectWrapper() {
    }

    public RectWrapper(Rect rect) {
        this.rect = rect;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
    
}
