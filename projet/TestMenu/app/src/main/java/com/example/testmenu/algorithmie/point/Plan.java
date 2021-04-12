package com.example.testmenu.algorithmie.point;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.testmenu.algorithmie.dijkstra.Dijkstra;
import com.example.testmenu.algorithmie.dijkstra.GraphInterface;
import com.example.testmenu.algorithmie.dijkstra.PathAndDistances;
import com.example.testmenu.algorithmie.dijkstra.Previous;
import com.example.testmenu.algorithmie.dijkstra.PreviousAndPi;
import com.example.testmenu.algorithmie.dijkstra.VertexInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;



/**
 * Class which manages the maze
 * @author Sofiene Boutaj
 *
 */
public class Plan implements GraphInterface {

	
	private ArrayList<Point> coordonnes = new ArrayList<Point>();
	private ArrayList<Point> produits = new ArrayList<Point>();
	private Point entree;
	private Point sortie;
	private ArrayList<ArrayList<Double>> matrix;



	public Plan( ArrayList<Point> coordonnes, ArrayList<Point> produits, Point entree, Point sortie) {
		this.coordonnes = coordonnes;
		this.produits = produits;
		this.entree = entree;
		this.sortie = sortie;
	}
	/**
	 * Method which returns all the boxes of the Maze.
	 * @return all the boxes of the maze
	 */
	public ArrayList<Point> getCoordonnes(){
		return coordonnes;
	}
	public ArrayList<Point> getProduits(){
		return produits;
	}


	@Override
	public int sizeGraph() {
		// TODO Auto-generated method stub
		return this.getAllVertices().size();
	}
	
	@Override
	public double getWeight(VertexInterface v1, VertexInterface v2) {
		// TODO Auto-generated method stub
		return Math.sqrt(Math.pow((v1.getX() - v2.getX()),2)  + Math.pow((v1.getY() - v2.getY()),2 )); 
	}
	@Override
	public ArrayList<VertexInterface> getAllVertices() {
		
		ArrayList<VertexInterface> vertices = new ArrayList<VertexInterface>();
			
		for (int i=0; i< coordonnes.size(); i++) {
			vertices.add(coordonnes.get(i));	
			}
		for (int i=0 ; i< produits.size(); i++) {
			vertices.add(produits.get(i));	
			}
		vertices.add(entree);
		vertices.add(sortie);

		return vertices;
	}

	@Override
	public ArrayList<VertexInterface> getSuccessors(VertexInterface vertex) {
		// TODO Auto-generated method stub
		ArrayList<VertexInterface> successors = new ArrayList<VertexInterface>();
		
		ArrayList<VertexInterface> vertices = getAllVertices();
		
		final int n = vertices.size(); // number of lines
		for(int i =0; i < n; i++ ) {
			if(0.2 < getWeight(vertex, vertices.get(i)) && getWeight(vertex, vertices.get(i)) < 1+0.1 ) 
				successors.add(vertices.get(i));
		}
		return successors;
	}

	public final PathAndDistances solve(VertexInterface from) {
			

		PreviousAndPi dijkstraResult = (PreviousAndPi) Dijkstra.dijkstra(this, from, produits.size());
		ArrayList<ArrayList<VertexInterface>> chemin = new ArrayList<ArrayList<VertexInterface>>();
		Previous previous = dijkstraResult.getPrevious();
		
		int n = produits.size();

		for(int i =0; i <n; i++) {
			ArrayList<VertexInterface> p = previous.getShortestPath(produits.get(i));
			 
			chemin.add(p);
		

		}
		
		ArrayList<Double> weights = new ArrayList<Double>();


		for(int i =0; i< n; i++ ) {
			
			weights.add((double) dijkstraResult.getPI().getPi(produits.get(i)));
			// System.out.println((double) dijkstraResult.getPI().getPi(produits.get(i)));
		}
		
		n = chemin.size();
		/*System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(n);

		for(int i=0; i<n; i++){
			System.out.println(chemin.get(i).getLabel());
				}

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();*/

    	//return  new PathAndDistances((ArrayList<VertexInterface>)chemin.subList(1, n-2), weights);
    	return  new PathAndDistances(chemin, weights);

		}
	
	
}
	
