package com.beacons.demo;

import android.util.ArrayMap;
import android.util.Log;

import java.security.Policy;
import java.util.ArrayList;

public class Trilateration {

    private ArrayMap<String,Beacon> beacons;

    public void init() {
        beacons = new ArrayMap<String, Beacon>();
    }

    public void updateBeacon(BeaconModel beaconModel) {
        Log.d("[update]","nb: " + beacons.size());
        if(!beacons.containsKey(beaconModel.uuid)) {
            beacons.put(beaconModel.uuid, new Beacon(new Point(beaconModel.x, beaconModel.y), beaconModel.distance));
        } else {
            beacons.get(beaconModel.uuid).setRadius(beaconModel.distance);
        }
        Log.d("[distance]", ""+beaconModel.distance);
        Log.d("$$[coords]","x: "+beacons.get(beaconModel.uuid).getCoord().getX() + " y: "+beacons.get(beaconModel.uuid).getCoord().getY());
        Log.d("LIST OF BEACONS *****",beacons.toString());
    }


    public Point PX(Point p, Beacon b){
       Point px = null, pn = null;
       px.setX(b.getCoord().getX() - p.getX());
       px.setY(b.getCoord().getY() - p.getY());
       pn.setX(px.getX()*(b.getRadius()/normalize(px.getX(),px.getY())));
       pn.setY(px.getY()*(b.getRadius()/normalize(px.getX(),px.getY())));
       return pn;
    }

    public double normalize(double x, double y){
        return Math.sqrt(x * x + y * y);
    }
    
    public Point add(Point p,Point P){
        Point pAdd = null;
        pAdd.setX(p.getX() + P.getX());
        pAdd.setX(p.getX() + P.getX());
        return pAdd;
    }
    
    public Point trilateration(Point p){
        Point px = null;
        int ln = beacons.size();
        for(int i=0; i<ln-1;i++){
            px = PX(p,beacons.valueAt(i));
            p = add(p,px);
        }
        p.setX(p.getX() / ln);
        p.setX(p.getY() / ln);
        return p;
    }
    
    public Point getPosition(int n,Point p) {
        for(int i=0; i<n-1;i++){
            p = add(p, trilateration(p));
        }
        return p;
    }

}
