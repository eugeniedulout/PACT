package com.example.testmenu.algorithmie.dijkstra;

import java.util.Hashtable;
/**
 * Class of the Pi function used in Dijkstra algorithm
 * @author Sofiene Boutaj
 *
 */
public class Pi implements PiInterface {
	private Hashtable<VertexInterface, Double> p = new Hashtable<VertexInterface, Double>();
	/**
	 * Constructor of the Pi class
	 */
	public Pi () {
	}
	@Override
	public double getPi(VertexInterface y) {
		// TODO Auto-generated method stub
		if(p.get(y) == null)
			return 0;
		else	
			return p.get(y);
	}

	@Override
	public void setPi(VertexInterface v, double val) {
		if(p.containsKey(v)) {
			p.replace(v, val);
		}
		else
			p.put(v, val);

	}
	/**
	 * Initialization of the pi values for vertices in the Dijkstra algorithm
	 * @param g : the graph, type: GraphInterface
	 * @param r : the root of the graph, type: VertexInterface
	 */
	public void initToInfiniteValue(GraphInterface g,VertexInterface r) {
		p.put(r, 0.0);

		for (int j =0; j < g.sizeGraph(); j++) {
			// For all vertex that are different from r
			for(VertexInterface vertex : g.getAllVertices()) {
				if(!vertex.equals(r))
					setPi(vertex, Integer.MAX_VALUE);
			}
		}
	}
	
	/**
	 * Method which returns the vertex which is not in the aset
	 * of the Dijkstra algorithm and which has the minimum pi value
	 * @param aset : the set of vertices of the Dijkstra algorithm, type: ASet 
	 * @param g : the graph,  type:GraphInterface
	 * @param pivot: : the vertex 'pivot' in Dijkstra algorithm
	 * @return  the vertex which is not in the aset of the Dijkstra algorithm and which has the minimum pi value
	 */
	public VertexInterface min(ASet aset, GraphInterface g, VertexInterface pivot ) {
		VertexInterface min = null;
		try {
			for(VertexInterface vertex : g.getAllVertices()) {
				if(!aset.getAset().contains(vertex)  && aset.isSuccessorOfA(vertex,g)) {
					if(min == null )
						min  = vertex;
					else if(getPi(vertex) < getPi(min))
						min  = vertex;
				}
			}
			if(min == null) // If no vertex was found, we don't change the pivot
				min = pivot;
			
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		return min;
	}


}
