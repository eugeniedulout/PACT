package com.example.testmenu.algorithmie.dijkstra;

import java.util.ArrayList;

import java.util.Hashtable;
/**
 * Class of the pere function in the Dijkstra algorithm
 *@author Sofiene Boutaj
 */
public class Previous implements PreviousInterface {
	private Hashtable<VertexInterface, VertexInterface> h = new Hashtable<VertexInterface, VertexInterface>();
	private final VertexInterface root;
	private ArrayList<VertexInterface> shortestPath = new ArrayList<VertexInterface>();
	private String finalMessage = "The maze is solved ! Look at the file: 'LabyrintheResolu.txt' in the folder 'data' to see the result" ;

	
   /** 
    * Constructor of the Previous Class
    * @param root : the root of the graph
    */
	public Previous(VertexInterface root) {

		this.root = root;
		this.h.put(root, root);

	}
	/**
	 * Getter which returns the the finalMessage attribute(finalMessage is the message which is displayed
	 * if the maze has been solved).
	 * @return  the finalMessage attribute 
	 */
	public String getFinalMessage() {
		
		return finalMessage;
	}
	@Override
	public VertexInterface getPrevious(VertexInterface v) {

		if(h.containsKey(v))
			return h.get(v);
		else
			return null;
	}

	@Override
	public void setPrevious(VertexInterface v1, VertexInterface v2) {

		if(h.containsKey(v1)) 
			h.replace(v1, v2);
		
		else
			h.put(v1, v2);

	}
	
/*	@Override
	public ArrayList<VertexInterface> getShortestPath(VertexInterface v) {

		shortestPath.add(v);
		try {
			if(v.equals(root))
				return shortestPath;
			else
				return getShortestPath( getPrevious(v));
			
			}
    catch(NullPointerException e)
		{
		finalMessage = "The maze can't be solved";
		return shortestPath;
		}
	catch(Exception e) {
		return shortestPath;
		
		}
	}*/
	
	@Override
	public ArrayList<VertexInterface> getShortestPath(VertexInterface v) {

		ArrayList<VertexInterface> shortPath = new ArrayList<VertexInterface>();
		VertexInterface a = v;
		while(a != null && !getPrevious(a).equals(root)) {
			if( !a.equals(v))
				shortPath.add(a);
			a = getPrevious(a);
		}
		shortPath.add(a);
		return shortPath;
	}
	
	/**
	 * Method which updates the Pi value and the previous vertex of all the successors
	 * of the vertex 'pivot' in Dijkstra algorithm.
	 * @param g : the graph 
	 * @param aset : the set of vertices of Dijkstra algorithm
	 * @param pi :  the pi function of Dijkstra algorithm
	 * @param previous : the previous function of Dijkstra algorithm
	 * @param pivot : the vertex 'pivot' in Dijkstra algorithm
	 */
	public void updatePiAndPrevious(GraphInterface g, ASet aset, Pi pi, Previous previous, VertexInterface pivot ) {
		ArrayList<VertexInterface> allVertices = g.getAllVertices();
		for(VertexInterface vertex : allVertices) {
			if( (!aset.contains(vertex)) && g.getSuccessors(pivot).contains(vertex)) {

				if(pi.getPi(pivot) + g.getWeight(pivot,vertex) < pi.getPi(vertex) ){
					pi.setPi(vertex, pi.getPi(pivot) + g.getWeight(pivot,vertex));
					// System.out.println("ajout de precedent (" +  pivot.getLabel() +" � : " + vertex.getLabel());
					previous.setPrevious(vertex, pivot);
				}
			}	
		}
	}
}
