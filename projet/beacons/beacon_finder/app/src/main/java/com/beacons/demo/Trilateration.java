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


    public Point PN(Point p, Beacon b){
       Point px = new Point(b.getCoord().getX() - p.getX(), b.getCoord().getY() - p.getY());

       double norme = normalize(px);

       Point pn = new Point(px.getX()*(1 - b.getRadius()/norme), px.getY()*(1 - b.getRadius()/norme));
       Log.d("POSITION --> Beacons]", "x: " + b.getCoord().getX() + " // y: " + b.getCoord().getY());
       Log.d("[POSITION -- > PN]", "x : " + pn.getX() + " ---- y: "+pn.getY());
       return pn;
    }

    public double normalize(Point p){
        return Math.sqrt(p.getX() * p.getX() + p.getY() * p.getY());
    }
    
    public Point add(Point p,Point P){
        return new Point(p.getX() + P.getX(), p.getY() + P.getY());
    }
    
    public Point trilateration(Point orig){
        Point pn = null;
        Point sum = new Point(0, 0);
        int ln = beacons.size();


        for(int i=0; i<ln;i++){
            Log.d("[POSITION --> ORIG]", "x: " + orig.getX() + " ---- y: " + orig.getY());
            pn = PN(orig,beacons.valueAt(i));
            sum = add(sum,pn);
        }
        Point trans = new Point(sum.getX() / ln, sum.getY() / ln);
        return trans;
    }
    
    public Point getPosition(int n,Point p) {
        Point pos = new Point(p.getX(), p.getY());
        for(int i=0; i<n-1;i++){
            Log.d("\n\n[POSITION]", " ---> TOUR "+i);
            pos = add(pos, trilateration(pos));
        }
        return pos;
    }

}
