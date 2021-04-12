package com.example.testmenu.algorithmie.point;

import com.example.testmenu.algorithmie.dijkstra.VertexInterface;

import java.io.Serializable;

public class PointE  {

    private final int x;
    private final int y;

    /**
     * Constructor of the class Point
     * @param x : the coordinate on the x-axis
     * @param y : the coordinate on the y-axis
     */
    public PointE(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getLabel() {
        return "coordonn�es: (" + this.x + ", "+  this.y + ")";
    }

}

