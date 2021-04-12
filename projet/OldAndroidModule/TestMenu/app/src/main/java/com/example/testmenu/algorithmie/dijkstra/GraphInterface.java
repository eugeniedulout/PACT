package com.example.testmenu.algorithmie.dijkstra;
import java.util.ArrayList;
/**
 * Interface to create a graph
 * @author Sofiene Boutaj
 *
 */

public interface GraphInterface {

	/**
	 * return the size of the graph, ie the number of vertices
	 * @return an integer which is the size of the graph
	 */
	public int sizeGraph();
	/**
	 * Method which returns the weight of the edge (v1, v2)
	 * Note: In our project we'll consider that the weight of all edges are equal to 1 
	 * @param v1, type: VertexInterface
	 * @param v2, type:VertexInterface
	 * @return the weight of the edge
	 */
	public double getWeight(VertexInterface v1,VertexInterface v2);
	/**
	 * Method which returns all the vertices of the Graph
	 * @return all the vertices of the graph
	 */
	public ArrayList<VertexInterface> getAllVertices() ;
	/**
	 * This method returns all the successors of the vertex in parameter
	 * @param vertex type : VertexInterface 
	 * @return all the successors of the vertex
	 */
	public ArrayList<VertexInterface> getSuccessors(VertexInterface vertex) ;
	
	
	
}

