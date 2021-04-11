package com.example.testmenu.algorithmie.point;


import com.example.testmenu.algorithmie.dijkstra.VertexInterface;

import java.io.Serializable;

public class Point implements VertexInterface, Serializable {
	
	private final double x;
	private final double y;
	
	/**
	 * Constructor of the class Point
	 * @param x : the coordinate on the x-axis
	 * @param y : the coordinate on the y-axis
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public double getX() {
		return x;
	}
	
	@Override
	public double getY() {
		return y;
	}
	
	@Override
	public boolean isProductPoint() {
		return false;
	}
	
	/**
	 * Method which returns the label of the point
	 * @return the label of the point
	 */
	public String getLabel() {
		return "coordonn�es: (" + this.x + ", "+  this.y + ")";
	}
	
	@Override
	public boolean equals(Object vertex) {
		if(vertex instanceof Point) {
			Point v = (Point)vertex;
			if(this.x == v.x &&  this.y == v.y)
				return true;
		}
		return false;
		
	}

}
