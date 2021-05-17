package com.example.testmenu.algorithmie.dijkstra;


import android.util.Log;

import java.util.ArrayList;

/**
 * Dijkstra algorithm class 
 * @author Sofiene Boutaj
 *
 */

public class Dijkstra {

	/**
	 * Class method which apply Dijkstra algorithm with a graph and a root as parameters. The method returns a PreviousInterface type. 
	 * Then we can have access to the shortestPath by using getShortestPath method from the Class "Previous"
	 * @param g, the graph, type:GraphInterface
	 * @param r,the root of the graph, type:VertexInterface
	 * @return PreviousInterface previous 
	 */
	public static PreviousAndPi dijkstra(GraphInterface g, VertexInterface r, int numberOfProducts){
		int compteurProduct = 0;
		
		ASet aset = new ASet();
		Pi pi = new Pi();
		Previous previous = new Previous(r,g);
		aset.add(r);
		VertexInterface pivot = r;
		System.out.println(r.getLabel());

		ArrayList<VertexInterface> succ = g.getSuccessors(pivot);
		for(VertexInterface vert : succ)
			Log.e("Successeurs de " + pivot.getLabel(), "      " + vert.getLabel());
		pi.initToInfiniteValue(g,r); 
		//System.out.println("heyyy");
		int sizeGraph = g.sizeGraph();
		for (int j = 1; j< sizeGraph; j++ ){

			previous.updatePiAndPrevious(aset, pi, previous, pivot);
			pivot = pi.min(aset, g, pivot);
			// Log.e("ppp " , pivot.getLabel());

			/*ArrayList<VertexInterface> successors = g.getSuccessors(pivot);
			for(VertexInterface vertex : successors)
				Log.e("successor de" + pivot.getLabel(), vertex.getLabel());*/

			 // System.out.println(pivot.getLabel());
			 // System.out.println(pivot.getLabel());
			/*System.out.println();
			System.out.println();
			if(pivot.isProductPoint())
				System.out.println("pivot !!!!!!!!!!!!!!!!!!" + pivot.getLabel() + "   " + pi.getPi(pivot));*/
			aset.add(pivot);
			if(pivot.isProductPoint()) {
				System.out.println("pivot !!!!!!!!!!!!!!!!!!" + pivot.getLabel() + "   " + pi.getPi(pivot));
				compteurProduct++;
			}
			if(compteurProduct ==numberOfProducts +1)
				return new PreviousAndPi(previous, pi);
				
			//System.out.println(pi.getPi(pivot));

		}
	return new PreviousAndPi(previous, pi);
	}

}
