package com.example.testmenu.bluetooth;

import org.json.JSONException;
import org.json.JSONObject;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(JSONObject coords) throws JSONException {
        this.x = coords.getDouble("x");
        this.y = coords.getDouble("y");
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void translatePol(double R, double alpha) {
        this.x += R*Math.cos(alpha);
        this.y += R*Math.sin(alpha);
    }
}
