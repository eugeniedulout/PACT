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

    public Point getPosition() {
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
        return G;
    }


    private Point getPos2Beacons(Beacon b1, Beacon b2) {
        Point p1 = b1.getCoord();
        Point p2 = b2.getCoord();
        Log.d("[GetPos2Beacons]","B1 -> "+b1.toString() + " - B2 -> "+b2.toString());
        double alpha = getAngle(p1,p2);
        p1.translatePol(b1.getRadius(), alpha);
        p2.translatePol(-b2.getRadius(), alpha);
        return getMiddle(p1,p2);
    }

    private double getAngle(Point p1, Point p2) {
        return Math.atan((p1.getY()-p2.getY())/(p1.getX()-p2.getX()));
    }

    private Point getMiddle(Point p1, Point p2) {
        return new Point((p1.getX()+p2.getX())/2, (p1.getY()+p2.getY())/2);
    }

    private Point gravityCenter(ArrayList<Point> points) {
        double x_sum = 0, y_sum = 0;
        int nb_points = points.size();
        Log.d("[GRAVITY]","--------------------");
        for(Point p : points) {
            Log.d("[GRAVITY]","x: "+p.getX()+ " y: "+p.getY());
            x_sum += p.getX();
            y_sum += p.getY();
        }
        Log.d("[GRAVITY]","----------------------");
        return new Point(x_sum/nb_points, y_sum/nb_points);
    }
}