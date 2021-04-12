package com.example.testmenu.plan_dynamique;

import com.example.testmenu.algorithmie.point.Point;

import java.util.ArrayList;

public class Trajet {
    ArrayList<com.example.testmenu.algorithmie.point.Point> listeSommets ;

    public Trajet (){
        this.listeSommets = new ArrayList<com.example.testmenu.algorithmie.point.Point>() ;

    }
    public Trajet (ArrayList<com.example.testmenu.algorithmie.point.Point> listeSommets){
        this.listeSommets = listeSommets ;

    }

    void addSommet (Point p){
        this.listeSommets.add(p);
    }

    com.example.testmenu.algorithmie.point.Point get (int i){
        return this.listeSommets.get(i) ;
    }

    int size (){
        return this.listeSommets.size();
    }
}
