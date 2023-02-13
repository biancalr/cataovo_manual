/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.entities;

import java.io.Serializable;
import java.util.Objects;

/**
 * The area containg an egg. A quadrilateral region where indicates the
 * presence of an egg.
 *
 * @author bibil
 */
public final class Region implements Cloneable, Serializable{

    /**
     * The region's height
     */
    private int height;
    /**
     * The region's width
     */
    private int width;
    /**
     * The initial point that every Region begins to be calculated
     */
    private Point initialPoint;

    /**
     *
     * @param height
     * @param width
     * @param initialPoint
     */
    public Region(int height, int width, Point initialPoint) {
        this.height = height;
        this.width = width;
        this.initialPoint = initialPoint;
    }
    
    public Region (Point initialPoint, Point finalPoint){
        this.initialPoint = initialPoint;
        this.width = initialPoint.getX() - finalPoint.getX();
        this.height = initialPoint.getY() - finalPoint.getY();
    }

    /**
     * Empty constructor
     */
    public Region() {
        this.initialPoint = new Point();
        this.height = 0;
        this.width = 0;
    }

    /**
     * 
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * 
     * @param height a height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * 
     * @param width a width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 
     * @return the initial Point
     */
    public Point getInitialPoint() {
        return initialPoint;
    }

    /**
     * 
     * @param initialPoint a initial point to set
     */
    public void setInitialPoint(Point initialPoint) {
        this.initialPoint = initialPoint;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(initialPoint.toString())
                .append(",").append(height).append(",").append(width);
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        Region reg = (Region) obj;
        return (Objects.equals(width, reg.width) && Objects.equals(height, reg.height)
                && Objects.equals(initialPoint, reg.initialPoint));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.height);
        hash = 97 * hash + Objects.hashCode(this.width);
        hash = 97 * hash + Objects.hashCode(this.initialPoint);
        return hash;
    }

    @Override
    public Region clone() throws CloneNotSupportedException {
        return (Region) super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    

}
