/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cataovo.entities;

import java.util.Objects;

/**
 * The area containg an egg. A quadrilateral region where indicates the
 * presence of an egg.
 *
 * @author bibil
 */
public final class Region {

    /**
     * The region's lenght
     */
    private Double lenght;
    /**
     * The region's width
     */
    private Double width;
    /**
     * The initial point that every Region begins to be calculated
     */
    private Point initialPoint;

    /**
     *
     * @param length
     * @param width
     * @param initialPoint
     */
    public Region(Double length, Double width, Point initialPoint) {
        this.lenght = length;
        this.width = width;
        this.initialPoint = initialPoint;
    }

    /**
     * Empty constructor
     */
    public Region() {
        this.initialPoint = new Point();
        this.lenght = 0.0;
        this.width = 0.0;
    }

    /**
     * 
     * @return the lenght
     */
    public Double getLenght() {
        return lenght;
    }

    /**
     * 
     * @param lenght a lenght to set
     */
    public void setLenght(Double lenght) {
        this.lenght = lenght;
    }

    /**
     * 
     * @return the width
     */
    public Double getWidth() {
        return width;
    }

    /**
     * 
     * @param width a width to set
     */
    public void setWidth(Double width) {
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
                .append(",").append(lenght).append(",").append(width);
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
        return (Objects.equals(width, reg.width) && Objects.equals(lenght, reg.lenght)
                && Objects.equals(initialPoint, reg.initialPoint));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.lenght);
        hash = 97 * hash + Objects.hashCode(this.width);
        hash = 97 * hash + Objects.hashCode(this.initialPoint);
        return hash;
    }

}
