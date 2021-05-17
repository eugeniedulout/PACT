package com.example.testmenu.plan_dynamique;

import com.example.testmenu.algorithmie.point.Point;

import java.util.ArrayList;

public class Trajet {
    ArrayList<Point> listeSommets ;

    public Trajet (){
        this.listeSommets = new ArrayList<Point>() ;

    }
    public Trajet (ArrayList<Point> listeSommets){
        this.listeSommets = listeSommets ;

    }

    void addSommet (Point p){
        this.listeSommets.add(p);
    }

    Point get (int i){
        return this.listeSommets.get(i) ;
    }

    int size (){
        return this.listeSommets.size();
    }
}
