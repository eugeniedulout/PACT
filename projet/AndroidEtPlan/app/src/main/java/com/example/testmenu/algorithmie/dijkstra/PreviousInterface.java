package com.example.testmenu.algorithmie.dijkstra;

import java.util.ArrayList;
/**
 * Interface of the pere function used in the Dijkstra algorithm
 * @author Sofiene Boutaj
 *
 */

public interface PreviousInterface {
	/**
	 * Method which returns the previous vertex of the vertex in parameter.
	 * @param v, type:VertexInterface
	 * @return the previous vertex of v 
	 */
	public VertexInterface getPrevious(VertexInterface v);
	/**
	 * Method which changes the previous vertex of v1 to v2 
	 * @param v1 , type:VertexInterface
	 * @param v2 , type:VertexInterface

	 */
	public void setPrevious(VertexInterface v1, VertexInterface v2);
	/**
	 * Method which returns the shortest path from the root to a vertex
	 * @param v : a vertex
	 * @return an ArrayList containing all the vertices between the root and the vertex v
	 */
	public ArrayList<VertexInterface> getShortestPath(VertexInterface v);
	
	}
