package com.example.plandynamiquev2;

import java.util.ArrayList;

public class Trajet {
    ArrayList<Point> listeSommets ;

    public Trajet (){
        this.listeSommets = new ArrayList<Point>() ;

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
