
package com.example.testmenu.algorithmie.dijkstra;

import java.util.HashSet;

/**
 * Class of the set A which is used in Dijkstra algorithm.
 * A is a set of vertices
 * @author Sofiene Boutaj
 * 
 */

public class ASet implements ASetInterface {
	
	private HashSet<VertexInterface> aset= new HashSet<VertexInterface>();
	
	/**
	 * Constructor of the ASet Class
	 */
	public ASet() {
		
	}
	
	/**
	 * Getter which return the aset attribute(aset is the set of vertices
	 * of Dijkstra algorithm)
	 * @return the set of vertices aset
	 */
	public HashSet<VertexInterface> getAset() {
		return aset;
	}
	
	@Override
	public boolean contains(VertexInterface vertex) {

		return aset.contains(vertex);
	}

	@Override
	public void add(VertexInterface vertex) {

		aset.add(vertex);
	}
	/**
	 * This method allows to check if a vertex is a successor of any vertices in the aset
	 * @param vertex, type: VertexInterface
	 * @param g, type: GraphInterface
	 * @return  a boolean which is equal to true if the vertex is a successor of any vertex in the aset 
	 */
	public boolean isSuccessorOfA(VertexInterface vertex, GraphInterface g) {
		boolean isSuccessor = false;

		for(VertexInterface v : aset) {
			if(g.getSuccessors(v).contains(vertex))
				isSuccessor = true;
			
		}
		return isSuccessor;
	}

}
