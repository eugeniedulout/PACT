public interface ServerInterface {
	public void correctDB(int productID, int shopID, Integer[] coordinates, boolean admin);
	/*Corrects the DB for a specific product
	 * Used by Map Bloc AND Web Bloc*/
	
	/* this should also contain functions related to connecting an account 
	 * and making requests to the DataBase. 
	 * However, these fall within the tasks attributed to the Client-Server Bloc 
	 * and thus aren't specified her*/ 
}
