
public interface SuggestionInterface {
	public Products[] suggestion(int shopID, Integer[] userPreferences, ShoppingList[] shoppingListHistoric, ShoppingList currentList);
	/*Returns a list of suggested products computed depending on the users preferences,
	 *  their previous shopping list and what is currently in the one being assembled
	 *  Used by Android Interface Bloc OR the Server Bloc*/
}
