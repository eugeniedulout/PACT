package com.example.testmenu.bluetooth;

import androidx.annotation.NonNull;

public class Beacon {
    private Point coord;
    private double radius;

    public Beacon(Point coord, double radius) {
        this.coord = coord;
        this.radius = radius;
    }

    public Point getCoord() {
        return coord;
    }

    public void setCoord(Point coord) {
        this.coord = coord;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @NonNull
    @Override
    public String toString() {
        return "x: "+this.getCoord().getX()+" y: " + this.getCoord().getY() + " radius: "+this.getRadius();
    }
}