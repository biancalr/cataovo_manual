/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cataovo.entities;

import java.util.Objects;

/**
 * The initial Point. Class that represents the initial Point from wich a
 * {@link Region} containig an egg is formed.
 *
 * @author bibil
 */
public final class Point {

    /**
     * The X axis of the initial {@code Point}
     */
    private Double x;
    /**
     * The Y axis of the initial {@code Point}
     */
    private Double y;

    /**
     *
     * @param x a X axis to set
     * @param y a Y axis to set
     */
    public Point(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Empty constructor
     */
    public Point() {
        this.x = 0.0;
        this.y = 0.0;
    }

    /**
     * 
     * @param point 
     */
    public Point(java.awt.Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    /**
     *
     * @return the X axis
     */
    public Double getX() {
        return x;
    }

    /**
     *
     * @param x an X axis to set
     */
    public void setX(Double x) {
        this.x = x;
    }

    /**
     *
     * @return the Y axis
     */
    public Double getY() {
        return y;
    }

    /**
     *
     * @param y a Y axis to set
     */
    public void setY(Double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(x).append(",").append(y);
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
        Point p = (Point) obj;
        return ((Objects.equals(p.x, this.x)) && (Objects.equals(p.y, this.y)));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.x);
        hash = 71 * hash + Objects.hashCode(this.y);
        return hash;
    }

}
