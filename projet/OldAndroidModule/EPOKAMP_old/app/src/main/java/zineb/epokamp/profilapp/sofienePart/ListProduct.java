package zineb.epokamp.profilapp.sofienePart;


import java.util.ArrayList;

public class ListProduct {

    private String listName;
    private ArrayList<Product> listOfProducts;

    public String getListName() {
        return listName;
    }

    public ArrayList<Product> getListOfProducts() {
        return listOfProducts;
    }

    public ListProduct(String listName, ArrayList<Product> listOfProducts) {
        this.listName = listName;
        this.listOfProducts = listOfProducts;
    }
    /*
    public void displayProductsInTheList(Context context) {
        FragmentController.swapFragmentInMainContainer(new DisplayedProductsFromAListFragment(listOfProducts, listName), context);
    }*/


}
