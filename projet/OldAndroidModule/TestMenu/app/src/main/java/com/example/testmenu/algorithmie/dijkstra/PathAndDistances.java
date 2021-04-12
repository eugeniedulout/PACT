package com.example.testmenu.algorithmie.dijkstra;

import java.util.ArrayList;

public class PathAndDistances {
	
	private ArrayList<ArrayList<VertexInterface>> path = new ArrayList<ArrayList<VertexInterface>>();
	private ArrayList<Double> distances = new ArrayList<Double>();
	public PathAndDistances(ArrayList<ArrayList<VertexInterface>> path, ArrayList<Double> distances) {
		super();
		this.path = path;
		this.distances = distances;
	}
	public ArrayList<ArrayList<VertexInterface>> getPath() {
		return path;
	}

	public ArrayList<Double> getDistances() {
		return distances;
	}

}
