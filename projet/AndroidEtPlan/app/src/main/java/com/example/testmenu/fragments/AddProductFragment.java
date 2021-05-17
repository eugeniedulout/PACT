package com.example.testmenu.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.testmenu.Controller;
import com.example.testmenu.FragmentController;
import com.example.testmenu.Product;
import com.example.testmenu.ProductOnSpecialOffer;
import com.example.testmenu.R;
import com.example.testmenu.suggestion.SlopeOne;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddProductFragment extends Fragment {
    private ImageButton addButton;
    private ImageButton minusButtton;
    private TextView quantityNumberText;
    private AutoCompleteTextView searchProduct;
    private Product foundProduct;
    private int count = 1;
    private FloatingActionButton addToListButton;
    private int marketId;
    private String listeNameTexte;
    private  TextView priceTotalTextView;
    private double totalPrice = 0;
    private ArrayList<String> arrayProductsName = new ArrayList<String>();
    private ArrayList<Product> displayedListOfProducts = new ArrayList<Product>();
    private CardView goToSeeCart;
    private boolean isFromBuildingFragment;
    private  int compteurDispalyedProductFromAlistFragment;
    public  AddProductFragment(int marketId, String listeNameTexte, ArrayList<Product> displayedListOfProducts, boolean isFromBuildingFragment ) {
        this.marketId = marketId;
        this.listeNameTexte = listeNameTexte;
        this.displayedListOfProducts = displayedListOfProducts;
        this.isFromBuildingFragment = isFromBuildingFragment;

    }

    public  AddProductFragment(int marketId, String listeNameTexte, ArrayList<Product> displayedListOfProducts, boolean isFromBuildingFragment, int compteurDispalyedProductFromAlistFragment) {
        this.marketId = marketId;
        this.listeNameTexte = listeNameTexte;
        this.displayedListOfProducts = displayedListOfProducts;
        this.isFromBuildingFragment = isFromBuildingFragment;
        this.compteurDispalyedProductFromAlistFragment =compteurDispalyedProductFromAlistFragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_product, container, false);
        addButton = (ImageButton) (v.findViewById(R.id.addNewRecette));
        minusButtton = (ImageButton) (v.findViewById(R.id.minusButton));
        quantityNumberText = (TextView)v.findViewById(R.id.quantiteNumberText);
        searchProduct = (AutoCompleteTextView)v.findViewById(R.id.serachProduct);
        addToListButton = (FloatingActionButton)v.findViewById(R.id.addToListButton);
        Log.e("TAG2", ""+marketId);
        Log.e("TAG2", ""+marketId);

        TextView suggestionText = (TextView)v.findViewById(R.id.suggestonText);


        SlopeOne slopeOne = new SlopeOne();
        String result = slopeOne.getSlopeOne();
        suggestionText.setText("Produit suggeré " + result);

        ArrayList<Product> productsFromMarket = Controller.getAllProducts(marketId);
        ArrayList<ProductOnSpecialOffer> productsFromMarketPromotions = Controller.getMarketOffers(marketId);

        for(int i =0; i<productsFromMarketPromotions.size();i++){
            productsFromMarket.add(productsFromMarketPromotions.get(i));
        }
        //ArrayList<Product> productsFromMarket = new ArrayList<Product>();
        //initList(productsFromMarket);
        int m = productsFromMarket.size();
        for(int i=0; i< m; i++) {
            arrayProductsName.add(productsFromMarket.get(i).getName());
        }

        goToSeeCart = (CardView)v.findViewById(R.id.goToSeeCart);


        goToSeeCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFromBuildingFragment)
                    FragmentController.swapFragmentInMainContainer(new BuildingListFragment(displayedListOfProducts, listeNameTexte, marketId), getContext());
                else
                    FragmentController.swapFragmentInMainContainer(new DisplayedProductsFromAListFragment(displayedListOfProducts, listeNameTexte, marketId, compteurDispalyedProductFromAlistFragment), getContext());

            }
        });

        priceTotalTextView = (TextView)v.findViewById(R.id.priceTotalTextView);


        for (int i = 0; i < displayedListOfProducts.size(); i++) {
            totalPrice += displayedListOfProducts.get(i).getPrice();
        }
        String value = String.valueOf(totalPrice / 100);
        priceTotalTextView.setText(value + " €");



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_selectable_list_item, arrayProductsName);
        searchProduct.setAdapter(adapter);
        searchProduct.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String name = searchProduct.getText().toString();
                if( name != null) {
                for(int i=0; i<m; i++) {
                    if (name.equals(productsFromMarket.get(i).getName())) {
                        foundProduct = productsFromMarket.get(i);
                        foundProduct.displayInfo(getContext(), R.id.containerProductInfo);
                        View view = getActivity().getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }

                    }
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });


        addToListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(foundProduct != null) {
                        Product productToAdd = foundProduct;
                        productToAdd.multiplyByQuantity(count);
                        int indiceAlreadyInTheList = productsFromMarket.size() + 1;
                        for(int i =0; i<displayedListOfProducts.size(); i++) {
                            if(foundProduct.getProductImageUrl().equals(displayedListOfProducts.get(i).getProductImageUrl())){
                                indiceAlreadyInTheList = i;
                            }
                        }
                        if(indiceAlreadyInTheList == productsFromMarket.size() + 1) {

                            displayedListOfProducts.add(productToAdd);
                            totalPrice += foundProduct.getPrice();

                        }
                        else{
                            displayedListOfProducts.set(indiceAlreadyInTheList, productToAdd);
                            totalPrice = 0;
                            for (int i = 0; i < displayedListOfProducts.size(); i++) {
                                totalPrice += displayedListOfProducts.get(i).getPrice();
                            }
                        }
                        String value = String.valueOf(totalPrice / 100);
                        priceTotalTextView.setText(value + " €");
                        searchProduct.setText("");
                        count = 1;
                        quantityNumberText.setText("" + count);
                    }


            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              count++;
                                              quantityNumberText.setText(String.valueOf(count));

                                          }
                                      }
        );
        minusButtton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if(count > 1)
                                                 count--;
                                             quantityNumberText.setText(String.valueOf(count));
                                         }
                                     }
        );
        //Fragment fragmentProductInfo = (Fragment)v.findViewById(R.id.fragmentProductInfo);


        return v;
    }



    private void initList(ArrayList<Product> products){

        products.add(new Product("Pomme", "pomme", 50, "La pomme c'est bon pour la santé !"));
        products.add(new Product("Pates", "pates",70,  "Les pâtes c'est pas cher !"));
        products.add(new Product("Beurre", "beurre", 100));
        products.add(new Product("Riz", "riz", 130));
        products.add(new Product("Nutella", "nutella", 300, "Le nutella c'est pas bon pour la santé !"));


    }
}