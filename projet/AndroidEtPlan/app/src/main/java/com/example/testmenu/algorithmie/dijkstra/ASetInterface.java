package com.example.testmenu.algorithmie.dijkstra;
/**
 * Interface of the set A which used in Dijkstra algorithm.
 * A is a set of vertices
 * @author Sofiene Boutaj
 *
 */

public interface ASetInterface {
	/**
	 * This method allows to check if an element is in the set 
	 * @param vertex, type VertexInterface
	 * @return a boolean which is equal to true if the element is in the set
	 */
	public boolean contains(VertexInterface vertex);
	/**
	 * Method which adds a vertex into the set
	 * @param vertex : it is the vertex we add in the set
	 */
	public void add(VertexInterface vertex);
	

}
