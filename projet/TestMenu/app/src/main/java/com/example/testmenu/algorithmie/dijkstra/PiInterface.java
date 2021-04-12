package com.example.testmenu.algorithmie.dijkstra;

/**
 * Interface of the Pi function used in Dijkstra algorithm
 * @author Sofiene Boutaj
 *
 */

public interface PiInterface {
	/**
	 * Method which returns the distance of the shortest path between r and y (it is the value of Pi(y))
	 * @param v : a vertex
	 * @return the pi value of the vertex
	 */
	public double getPi(VertexInterface v);
	
	/**
	 * Method which affects the value "val" to Pi(v)
	 * @param v : the vertex 
	 * @param val : the value we want to set
	 */
	public void setPi(VertexInterface v, double val);
	
	
	
	
	
}
