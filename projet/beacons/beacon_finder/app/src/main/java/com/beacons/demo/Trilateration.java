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

    public Point trilateration(Point p){
        Point px = null;
        int ln = beacons.size();
        for(int i=0; i<ln-1;i++){
            px = PX(p,beacons.valueAt(i));
            p.setX(p.getX() + px.getX());
            p.setX(p.getX() + px.getX());
        }
        p.setX(p.getX() / ln);
        p.setX(p.getY() / ln);
        return p;
    }
    
    public Point getPosition(int n,Point p) {
        ArrayList<Point> sommets = new ArrayList<Point>();
        for(int i=0; i< beacons.size()-1; i++) {
            for(int j=i+1; j <beacons.size(); j++) {
                Beacon b1 = beacons.valueAt(i);
                Beacon b2 = beacons.valueAt(j);
                Log.d("[INFOS]", "B1 -> " + b1.toString() + " / B2 -> " + b2.toString());

                sommets.add(getPos2Beacons(b1,b2));
            }
        }
        Point G = gravityCenter(sommets);
        Log.d("Centre G --------", G.getX() +" " + G.getY());
        return P;
    }

}
