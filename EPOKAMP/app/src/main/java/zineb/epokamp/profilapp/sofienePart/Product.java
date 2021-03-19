package zineb.epokamp.profilapp.sofienePart;

import android.content.Context;
import android.os.Parcel;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String productImage;
    private double price;
    private boolean inStock;
    private String description;



    public Product(String name, String productImage, double price) {
        this.name = name;
        this.productImage = productImage;
        this.price = price;
    }

    public Product(String name, String productImage, double price, boolean inStock, String description) {
        this.name = name;
        this.productImage = productImage;
        this.price = price;
        this.inStock = inStock;
        this.description = description;
    }

    protected Product(Parcel in) {
        name = in.readString();
        productImage = in.readString();
        price = in.readDouble();
        inStock = in.readByte() != 0;
        description = in.readString();
    }
    /*
    public void displayInfo(Context context) {
        FragmentController.swapFragmentInMainContainer(new ProductInfoFragment(this), context);
    }*/


    public String getName() {
        return name;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductDescription() {
        return description;
    }

    public boolean getInStock() {
        return inStock;
    }



    public double getPrice() {
        return price;
    }

}
