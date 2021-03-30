import java.util.ArrayList;

public interface DataBaseToAndroid {
	
//-------------- Methods related to the user's connection -----------
	/**
	 * This method return true if the username and password are correct
	 * @param username
	 * @param password
	 * @return true if the username and password are correct, false otherwise
	 */
	boolean connect(String username, String password); 
	
	
	/**
	 * Il faudrait qu'on se concerte pour cette fonction
	 */
	boolean signUp(); 
	
	
//-------------- Methods related to the products -----------
	
	ArrayList<Product> getAllProducts();
	ArrayList<ListProduct> getUserLists(int userId);
	

	/**
	 * Method which adds to the database a list of products
	 */
	void addNewListOfProducts();
	
	
	
//-------------- Methods related to the markets -----------

	ArrayList<Market> getAllMarkets();
	ArrayList<ProductOnSpecialOffer> getMarketOffers(Market market);

//-------------- Methods related to the users -----------

	int getUserId();
	ArrayList<Integer> getUserFriends(int userId);
	


//-------------- Methods related to the recettes -----------

	ArrayList<Recette> getUserRecettes(int userId);
	
	/**
	 * Method which adds to the database a new recette
	 */
	void addNewRecette();
}



// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// Pour que vous ayez une idée de à quoi ressemble les classes
// Je vous les mets en bas en version allégé (sans les getters, setters, et methodes)
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


/////////////// Product Class  ///////////////

public class Product {
    private String name;
    private String productImageUrl;
    private double price;
    private boolean inStock;
    private String description;
    private String productTags;


    public Product(String name, String productImageUrl, double price) {
        this.name = name;
        this.productImageUrl = productImageUrl;
        this.price = price;
    }

    public Product(String name, String productImageUrl, double price, boolean inStock, String description) {
        this.name = name;
        this.productImageUrl = productImageUrl;
        this.price = price;
        this.inStock = inStock;
        this.description = description;
    }

}


/////////////// ProductOnSpecialOffer Class  ///////////////


public class ProductOnSpecialOffer extends Product {

    private double newPrice;
    private String expirationDate;


    public ProductOnSpecialOffer(String name, String productImage, double price, double newPrice, String expirationDate) {
        super(name, productImage, price);
        this.newPrice = newPrice;
        this.expirationDate = expirationDate;
    }

    public ProductOnSpecialOffer(String name, String productImage, double price, boolean inStock, String description, double newPrice, String expirationDate) {
        super(name, productImage, price, inStock, description);
        this.newPrice = newPrice;
        this.expirationDate = expirationDate;

    }
}



/////////////// ListProduct Class  ///////////////


public class ListProduct {

    private String listName;
    private ArrayList<Product> listOfProducts;

    public ListProduct(String listName, ArrayList<Product> listOfProducts) {
        this.listName = listName;
        this.listOfProducts = listOfProducts;
    }

}

/////////////// Market Class  ///////////////


public class Market {
    private String marketName;
    private String marketLogoUrl;
    private String openHour;
    private String closeHour;

    public Market(String marketName, String marketLogoUrl, String openHour, String closeHour) {
        this.marketName = marketName;
        this.marketLogoUrl = marketLogoUrl;
        this.openHour = openHour;
        this.closeHour = closeHour;
    }
}

/////////////// Ingredient Class  ///////////////


public class Ingredient {
    private String name;
    private String ingredientImageUrl;


    public Ingredient(String name, String ingredientImageUrl) {
        this.name = name;
        this.ingredientImageUrl = ingredientImageUrl;
    }

}


/////////////// Recette Class  ///////////////

public class Recette {
    private String recetteName;
    private ArrayList<Ingredient> listOfIngredients;
    private ArrayList<String> cookingInstructions;

    public Recette(String recetteName, ArrayList<Ingredient> listOfIngredients, ArrayList<String> cookingInstructions) {
        this.recetteName = recetteName;
        this.listOfIngredients = listOfIngredients;
        this.cookingInstructions = cookingInstructions;
    }

}
