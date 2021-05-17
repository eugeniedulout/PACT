package com.example.testmenu.algorithmie.point;
import android.util.Log;

import com.example.testmenu.algorithmie.dijkstra.Dijkstra;
import com.example.testmenu.algorithmie.dijkstra.GraphInterface;
import com.example.testmenu.algorithmie.dijkstra.PathAndDistances;
import com.example.testmenu.algorithmie.dijkstra.Previous;
import com.example.testmenu.algorithmie.dijkstra.PreviousAndPi;
import com.example.testmenu.algorithmie.dijkstra.VertexInterface;

import java.util.ArrayList;


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
	private VertexInterface[][] matrix = new VertexInterface[10][15] ;
	ArrayList<VertexInterface> allVertices = new  ArrayList<VertexInterface>();


	public Plan(ArrayList<Point> coordonnes, ArrayList<Point> produits, Point entree, Point sortie) {
		this.coordonnes = coordonnes;
		int sizeProduits = produits.size();
		for(int i=0; i<sizeProduits; i++ ) {
			Point produitI = produits.get(i);
			this.produits.add(new ProductPoint(produitI.getX(), produitI.getY()));
		}
		this.produits = produits;
		this.entree = entree;
		this.sortie = sortie;
		allVertices = getAllVertices();
		initMatrix();

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

	public void initMatrix() {
		for (int i=1; i<14;i++)
			matrix[0][i] = new Point( 0,i);

		for (int i=1; i<14;i++)
			matrix[3][i] = new Point( 3,i);

		for (int i=1; i<14;i++)
			matrix[6][i] =  new Point( 6,i);

		for (int i=1; i<14;i++)
			matrix[9][i] = new Point(9,i);

		for (int i=0; i<10;i++)
			matrix[i][14] = new Point(i,14);

		for (int i=0; i<10;i++)
			if(i  != 3 && i!= 6)
				matrix[i][0] = new Point(i,0);

		matrix[1][7] =  new Point(1,7);
		matrix[2][7] =  new Point(2,7);
		matrix[4][7] = new Point(4,7);
		matrix[5][7] =  new Point(5,7);
		matrix[7][7] =  new Point(7,7);
		matrix[8][7] =  new Point(8,7);

		matrix[3][0] =  new Point(3,0);
		matrix[6][0] =  new Point(6,0);


		int produitTaille = produits.size();

		for(int k=0; k<produitTaille; k++){
			Point produitsI = produits.get(k);
			double x= produitsI.getX();
			double y = produitsI.getY();
			int x_integer = (int)x;
			int y_integer = (int)y;
			matrix[x_integer][y_integer] = produitsI;
		}

		for(int i =0; i< matrix.length; i++) {
			for (int j=0; j< matrix[0].length; j++)
				if(matrix[i][j] != null)
					Log.e("matrix[" + String.valueOf(i) + "][" +String.valueOf(j) + "]", matrix[i][j].getLabel() );
				else
					Log.e("matrix[" + String.valueOf(i) + "][" +String.valueOf(j) + "]", "null");
		}


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

	/*
	@Override
	public ArrayList<VertexInterface> getSuccessors(VertexInterface vertex) {
		// TODO Auto-generated method stub
		ArrayList<VertexInterface> successors = new ArrayList<VertexInterface>();

		int n = allVertices.size();
		for(int i =0; i < n; i++ ) {
			if(0.2 < getWeight(vertex, allVertices.get(i)) && getWeight(vertex, allVertices.get(i)) < 1+0.01 )
				successors.add(allVertices.get(i));
		}
		return successors;



	}*/


		@Override
	public ArrayList<VertexInterface> getSuccessors(VertexInterface vertex) {
		// TODO Auto-generated method stub
		ArrayList<VertexInterface> successors = new ArrayList<VertexInterface>();

		final int x = (int)vertex.getX();
		final int y = (int)vertex.getY();
		final int n = matrix.length; // number of lines
		final int m = matrix[0].length; // column's number

		if(x > 0 && matrix[x-1][y] != null)
			successors.add(matrix[x-1][y]); // We add the left neighbor

		if(x < n-1 && matrix[x+1][y] != null)
			successors.add(matrix[x+1][y]);

		if(y > 0 && matrix[x][y-1] != null)
			successors.add(matrix[x][y-1]);

		if(y < m-1 && matrix[x][y+1] != null)
			successors.add(matrix[x][y+1]);

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
	
