package com.example.testmenu.algorithmie;

import android.util.Log;

import com.example.testmenu.algorithmie.dijkstra.PathAndDistances;
import com.example.testmenu.algorithmie.dijkstra.VertexInterface;
import com.example.testmenu.algorithmie.point.Plan;
import com.example.testmenu.algorithmie.point.Point;
import com.example.testmenu.algorithmie.point.ProductPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Main class of the project
 * @author Sofiene Boutaj
 *
 */

public class PlusCourtChemin {



    public static ArrayList<Point>  getCoordonnesChemin(ArrayList<Point> pointProduits)  {


        ArrayList<Point> coordonnes = new ArrayList();

        addCoordonnes(coordonnes);

        Point entree  = new ProductPoint(3,0);
        Point sortie  =  new ProductPoint(6,0);

		/*for(Point points : coordonnes)
			Log.e(points.getLabel());*/


        ArrayList<Point> produits = pointProduits;

        Plan labyrintheResolu = new Plan(coordonnes, produits, entree,sortie);
        // The maze is solved in the file 'data/labyrintheResolu'


        int m = produits.size();
        for(int i=0; i< m; i++) {
            for(int j=0; j<m; j++) {

                if(i<j) {
                    Log.e("Test" ,produits.get(i).getLabel() + "  " + i + j + " " + produits.get(j).getLabel());

                    if(i ==2 && j ==3)
                        Log.e("Test" ,Math.abs(produits.get(i).getY() - produits.get(j).getY()) + "  "+ Math.abs(Math.abs(produits.get(i).getX() - produits.get(j).getX())-1)) ;
                    if(Math.abs(produits.get(i).getY() - produits.get(j).getY()) < 0.1 && Math.abs(Math.abs(produits.get(i).getX() - produits.get(j).getX())-1) <0.1) {
                        Point produitI = produits.get(i);
                        Point produitJ = produits.get(j);
                        Log.e("Test" ,"onnn aa trouvvééé : " + produitI.getLabel() + "  " + produitJ.getLabel());
                        if((produitI.getX() > produitJ.getX())) {
                            produits.set(i, new ProductPoint(produitI.getX()+0.25, produitI.getY()));
                            produits.set(j, new ProductPoint(produitJ.getX()-0.25, produitI.getY()));
                            Log.e("Test" , produits.get(i).getLabel());
                            Log.e("Test" , produits.get(j).getLabel());


                        }
                        else {
                            produits.set(i, new ProductPoint(produitI.getX()-0.25, produitI.getY()));
                            produits.set(j, new ProductPoint(produitJ.getX()+0.25, produitI.getY()));
                            Log.e("Test" , produits.get(i).getLabel());
                            Log.e("Test" , produits.get(j).getLabel());
                        }

                    }
                }

            }
        }



        ArrayList<PathAndDistances> result = new ArrayList<PathAndDistances>();

        ArrayList<Point> total = produits;
        total.add(entree);
        total.add(sortie);

        int taille = total.size();

        ArrayList<Double> vide = new ArrayList<Double>();
        vide.add(0.0);


        for(int i = 0; i< taille; i++ ) {
            result.add(labyrintheResolu.solve(total.get(i)));
            Log.e("Test" ,"hey");
        }

        PathAndDistances patth = labyrintheResolu.solve(total.get(0));
		/*
		for(int i =0; i < patth.getPath().size(); i++) {
			for(int j=0; j< patth.getPath().get(i).size(); j++)
				Log.e("Test" ,patth.getPath().get(i).get(j).getLabel());
			Log.e("Test" ,);
		}*/


        for(int j=0; j< patth.getPath().get(2).size(); j++)
            Log.e("Test" ,patth.getPath().get(2).get(j).getLabel());


        for(int i =0; i < patth.getDistances().size(); i++) {
            Log.e("Test" , ""+patth.getDistances().get(i));
        }

        patth.getDistances().get(2);


        double[][] adjencyMatrix = new double[taille+1][taille+1];


        for(int i=0; i< taille; i++) {

            for(int j=0; j< taille; j++) {
                adjencyMatrix[i][j] = Math.round( result.get(j).getDistances().get(i));
            }

        }


        // Ajout du point fictif
        for(int i=0; i<taille+1; i++)
        {
            if( i < taille - 2) {
                adjencyMatrix[taille][i] = 100000000;
            }
            else
                adjencyMatrix[taille][i] = 0;

        }

        for(int i=0; i<taille; i++)
        {
            if( i < taille - 2) {
                adjencyMatrix[i][taille] =100000000 ;
            }
            else
                adjencyMatrix[i][taille] = 0;

        }


        for(int i=0; i< taille+1; i++) {

            for(int j=0; j< taille+1; j++) {
                Log.e("Test" ,""+adjencyMatrix[i][j]);
            }


        }

	        /*double[][] distanceMatrix =  {{0.0, 1.121212121212121*1000, 1.1717171717171717*1000, 1.121212121212121*1000, 0.6767676767676767*1000, 1.2222222222222223*1000, 0.25757575757575757*1000, 1.075757575757576*1000, 100000000},
                    {1.121212121212121*1000, 0.0, 0.8989898989898989*1000, 0.18181818181818177*1000, 0.8484848484848484*1000, 1.3939393939393938*1000, 1.196969696969697*1000, 1.4696969696969695*1000, 100000000},
                    {1.1717171717171717*1000, 0.8989898989898988, 0.0, 0.8989898989898988, 0.898989898989899, 0.898989898989899, 1.2474747474747476, 0.9747474747474749, 100000000},
                    {1.121212121212121*1000, 0.18181818181818177, 0.8989898989898989, 0.0, 0.8484848484848484, 1.3939393939393938, 1.196969696969697, 1.4696969696969695, 100000000},
                    {0.6767676767676768*1000, 0.8484848484848486, 0.898989898989899, 0.8484848484848486, 0.0, 0.9494949494949494, 0.5303030303030303, 0.803030303030303, 100000000},
                    {1.2222222222222219*1000, 1.3939393939393938, 0.8989898989898989, 1.3939393939393938, 0.9494949494949492, 0.0, 1.0757575757575755, 0.25757575757575746, 100000000},
                    {0.25757575757575757, 1.196969696969697, 1.2474747474747474, 1.196969696969697, 0.5303030303030303, 1.0757575757575757, 0.0, 0.9292929292929293, 0},
                    {1.075757575757576, 1.4696969696969695, 0.9747474747474748, 1.4696969696969695, 0.803030303030303, 0.25757575757575746, 0.9292929292929294, 0.0, 0},
                    {100000000, 100000000, 100000000, 100000000, 100000000, 100000000, 0, 0, 0}};*/


        List<Integer> ordresolution = TspDynamicProgrammingIterative.getOrdre(adjencyMatrix,taille-2);
        Collections.reverse(ordresolution);

        List<Integer> realOrderSolution =   ordresolution.subList(0, ordresolution.size()-2);

        int size = realOrderSolution.size();

        for (int i=0; i< realOrderSolution.size(); i++)
            Log.e("Test" ,""+realOrderSolution.get(i));

        ArrayList<Point> coordonnesCheminFinal = new ArrayList<Point>();


        /*
		for (int i =0; i<size-1; i++) {
			 ArrayList<VertexInterface> pointts = result.get(realOrderSolution.get(i+1)).getPath().get(realOrderSolution.get(i));
			 m = pointts.size();
			 for(int j=0; j< m; j++) {
				 System.out.println("iteration i= " + i + (pointts).get(j).getLabel() );
				 coordonnesCheminFinal.add((Point)pointts.get(j));
			 }

		}*/
        System.out.println();
        System.out.println();
        System.out.println();
        List<VertexInterface> pointts = result.get(realOrderSolution.get(1)).getPath().get(realOrderSolution.get(0));
        m = pointts.size();
        for(int j=0; j< m; j++) {
            System.out.println("iteration i= " + 0 + (pointts).get(j).getLabel() );
            coordonnesCheminFinal.add((Point)pointts.get(j));
        }
        for (int i =1; i<size-1; i++) {
            pointts = result.get(realOrderSolution.get(i+1)).getPath().get(realOrderSolution.get(i)).subList(1, result.get(realOrderSolution.get(i+1)).getPath().get(realOrderSolution.get(i)).size());
            m = pointts.size();
            for(int j=0; j< m; j++) {
                System.out.println("iteration i= " + i + (pointts).get(j).getLabel() );
                coordonnesCheminFinal.add((Point)pointts.get(j));
            }

        }

        System.out.println();
        System.out.println();
        System.out.println();

        /**

        for(int i = 0; i<m; i++) {
            coordonnesCheminFinalE.add(new com.example.testmenu.plan_dynamique.Point((int) coordonnesCheminFinal.get(i).getX(), (int) coordonnesCheminFinal.get(i).getY()));
            Log.e("entier: " , coordonnesCheminFinalE.get(i).getLabel());
        }
         */



		/*for(int i=0; i<coordonnesCheminFinal.size(); i++) {
			Log.e("Test" ,coordonnesCheminFinal.get(i).getLabel());
		}*/


		 /*ArrayList<VertexInterface> vertices = labyrintheResolu.getSuccessors(produits.get(2));
		Log.e("Test" ,);
		Log.e("Test" ,);
		Log.e("Test" ,);

		for (VertexInterface vertex : vertices)
			Log.e("Test" ,vertex.getLabel());*/
        ArrayList<Point> coordonnesCheminFinalFiltre = new ArrayList<Point>();
        m  = coordonnesCheminFinal.size();
        /**
        for(int i= 1; i<m; i++) {
            Log.e("jjjjj", ""+m);
            Point point2=coordonnesCheminFinal.get(i);
            Point point1=coordonnesCheminFinal.get(1-1);

            if (point1.getX()==point2.getX() && point1.getY()==point2.getY()){
                coordonnesCheminFinal.remove(i);
            }
        }

        for (int i=0; i<coordonnesCheminFinal.size();i++){
            Log.e("jjjjjjjjjjj", coordonnesCheminFinal.get(i).getLabel());
        }
         */

        return coordonnesCheminFinal;
    }


    private static void addCoordonnes(ArrayList<Point> coordonnes) {
	    /*for (int i=1; i<18;i++)
	    	coordonnes.add(new Point( 10000/11,i* 10000/18));
	    for (int i=1; i<18;i++)
	    	coordonnes.add(new Point(4* 10000/11,i* 10000/18));
	    for (int i=1; i<18;i++)
	    	coordonnes.add(new Point(7* 10000/11,i* 10000/18));
	    for (int i=1; i<18;i++)
	    	coordonnes.add(new Point(10* 10000/11,i* 10000/18));

	    for (int i=2; i<18;i++)
	    	coordonnes.add(new Point(i* 10000/11, 10000/18));

	    for (int i=2; i<18;i++)
	    	coordonnes.add(new Point(i* 10000/11,17* 10000/18));

	    for (int i=2; i<18;i++)
	    	coordonnes.add(new Point(i* 10000/11, 10000/2));*/

        for (int i=1; i<14;i++)
            coordonnes.add(new Point( 0,i));
        for (int i=1; i<14;i++)
            coordonnes.add(new Point(3,i));

        for (int i=1; i<14;i++)
            coordonnes.add(new Point(6,i));
        for (int i=1; i<14;i++)
            coordonnes.add(new Point(9,i));

        for (int i=0; i<10;i++)
            coordonnes.add(new Point(i,14));
        for (int i=0; i<10;i++)
            if(i  != 3 && i!= 6)
                coordonnes.add(new Point(i,0));

        coordonnes.add(new Point(1,7));
        coordonnes.add(new Point(2,7));
        coordonnes.add(new Point(4,7));
        coordonnes.add(new Point(5,7));
        coordonnes.add(new Point(7,7));
        coordonnes.add(new Point(8,7));






    }


    private static void addProduit(ArrayList<Point> produits) {
	    /*produits.add(new ProductPoint(2 *10000/11,3 * 10000/18));
	    produits.add(new ProductPoint(5* 10000/11,15 * 10000/18));
	    produits.add(new ProductPoint(8 * 10000/11,11* 10000/18));
	    produits.add(new ProductPoint(9* 10000/11,3* 10000/18));
	    produits.add(new ProductPoint(5* 10000/11,3* 10000/18));
	    produits.add(new ProductPoint(2* 10000/11,14* 10000/18));
	    produits.add(new ProductPoint(2* 10000/11,14* 10000/18));
	    produits.add(new ProductPoint(3* 10000/11,8* 10000/18));*/

		/*produits.add(new ProductPoint(2,5));
	    produits.add(new ProductPoint(6,3));
	    produits.add(new ProductPoint(8,13));
	    produits.add(new ProductPoint(9,13));
	    produits.add(new ProductPoint(2,13));
	    produits.add(new ProductPoint(3,13));*/

        produits.add(new ProductPoint(2,3));
        produits.add(new ProductPoint(4,11));
        produits.add(new ProductPoint(5,12));
        produits.add(new ProductPoint(8,11));
        produits.add(new ProductPoint(8,2));

        //produits.add(new ProductPoint(5,13));
	   /* produits.add(new ProductPoint(8,13));
	    produits.add(new ProductPoint(8,13));*/


	   /* produits.add(new ProductPoint(5* 10000/11,15 * 10000/18));
	    produits.add(new ProductPoint(10000/11,0));*/

    }

    //ArrayList<VertexInterface> chemin = getPrevious().getShortestPath(dest);





}

