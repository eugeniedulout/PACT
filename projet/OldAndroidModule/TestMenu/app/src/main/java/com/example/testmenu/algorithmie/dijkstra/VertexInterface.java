package com.example.testmenu.algorithmie.dijkstra;
/**
 * Interface which manage the vertices in the graph
 * @author Sofiene Boutaj
 *
 */

public interface VertexInterface {
	/**
	 * Method which compares two vertices and return if they are equal 
	 * @param vertex; type: Object
	 * @return a boolean which is equal to true if the two vertices are equal
	 */
	public boolean equals(Object vertex);
	/**
	 * Method which allows to get some information about a box
	 * like its coordinate or its state (Empty or not).
	 * @return a string which contain the label of the vertex
	 */
	public String getLabel();

	/**
	 * Getter which returns x attribute
	 * (x is the coordinate of the point on the x-axis).
	 * @return the integer x
	 */
	public double getX();
	
	/**
	 * Getter which returns y attribute
	 * (y is the coordinate of the point on the y-axis).
	 * @return the integer y
	 */
	public double getY();
	
	public boolean isProductPoint();
}
