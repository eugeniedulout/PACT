package com.example.testmenu.algorithmie.point;


import com.example.testmenu.algorithmie.dijkstra.VertexInterface;

import java.io.Serializable;

public class ProductPoint extends Point implements VertexInterface, Serializable {

	public ProductPoint(double x, double y) {
		super(x, y);
	}
	
	@Override
	public boolean isProductPoint() {
		return true;
	}
}
