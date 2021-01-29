
public interface ShortestPathInterface {
	public Integer[] shortestPath(Integer[] position, int shopID, ShoppingList shoppingList);
	/*Returns a list of direction vectors computed depending the products to gather 
	 *  corresponding to the optimized path within the given shop
	 *  Used by Map Bloc*/
}
