package com.example.testmenu.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testmenu.Controller;
import com.example.testmenu.FragmentController;
import com.example.testmenu.Product;
import com.example.testmenu.R;
import com.example.testmenu.algorithmie.point.Point;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddProductFragment extends Fragment {
    private ImageButton addButton;
    private ImageButton minusButtton;
    private TextView quantityNumberText;
    private AutoCompleteTextView searchProduct;
    private Product foundProduct;
    private  Fragment fragmentProductInfo;
    private int count = 1;
    private FloatingActionButton addToListButton;
    private int marketId;

    private ArrayList<String> arrayProductsName = new ArrayList<String>();

    private int dataSentCounter = 0;

    public  AddProductFragment(int marketId) {
        this.marketId = marketId;
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

       // ArrayList<Product> productsFromMarket = Controller.getAllProducts(marketId);
        ArrayList<Product> productsFromMarket = new ArrayList<Product>();
        initList(productsFromMarket);
        int m = productsFromMarket.size();
        for(int i=0; i< m; i++) {
            arrayProductsName.add(productsFromMarket.get(i).getName());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_selectable_list_item, arrayProductsName);
        searchProduct.setAdapter(adapter);
        searchProduct.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String name = searchProduct.getText().toString();
                if( name != null) {
                for(int i=0; i<m; i++) {
                    if (name.equals(productsFromMarket.get(i).getName())) {
                        foundProduct = productsFromMarket.get(i);
                        FragmentController.swapFragment(new ProductInfoFragment(foundProduct), R.id.containerProductInfo, getContext());
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
                    Bundle result = new Bundle();
                    foundProduct.multiplyByQuantity(count);
                    Product productToAdd = foundProduct;
                    result.putSerializable("productToAdd", productToAdd);

                    getParentFragmentManager().setFragmentResult("requestProductToAdd", result);
                    FragmentController.swapFragmentInMainContainer(new BuildingListFragment(marketId), getContext());


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