package com.example.testmenu.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.testmenu.FragmentController;
import com.example.testmenu.Product;
import com.example.testmenu.R;

import java.util.ArrayList;

public class BuildingListFragment extends Fragment implements View.OnClickListener{
    private Button testButton;
    private ImageView addProduct;
    private ArrayList<Product> displayedListOfProducts = new ArrayList<Product>();
    private ImageView addTheList;
    private EditText nameOfTheList;
    private ImageView returnTodisplayList;
    private int marketId;

    public BuildingListFragment(int marketId){
        this.marketId = marketId;
    }


    public void setDisplayedListOfProducts(ArrayList<Product> displayedListOfProducts) {
        this.displayedListOfProducts = displayedListOfProducts;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.e("TAG", ""+marketId);

        getParentFragmentManager().setFragmentResultListener("requestProductToAdd", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {

                Product productToAdd = (Product) bundle.getSerializable("productToAdd");
                if(productToAdd != null) displayedListOfProducts.add(productToAdd);
            }
        });

        View v = inflater.inflate(R.layout.fragment_building_list, container, false);

        FragmentController.swapFragment(new ListOfProductsFragment(displayedListOfProducts),R.id.listOfProductsContainer,getContext());

        nameOfTheList = v.findViewById(R.id.editNameListe);

        returnTodisplayList = (ImageView)v.findViewById(R.id.returnToDisplayList);
        returnTodisplayList.setOnClickListener(this::onClick);

        addTheList = (ImageView)v.findViewById(R.id.addTheList);
        addTheList.setOnClickListener(this::onClick);


        addProduct = (ImageView) (v.findViewById(R.id.addProductButton));
        addProduct.setOnClickListener(this::onClick);


        return v;
    }


    @Override
    public void onClick(View v) {
        Fragment fragment;
        switch (v.getId()) {
            case R.id.addProductButton:
                fragment = new AddProductFragment(marketId);
                break;
            default:
                fragment = new ListFragment();
                if (v.getId() == R.id.addTheList) {
                    displayedListOfProducts.clear();
                    nameOfTheList.setText("");
                }
                break;
        }
        FragmentController.swapFragmentInMainContainer(fragment, getContext());
    }
    private void initList() {
        displayedListOfProducts.add(new Product("Pomme", "pomme", 0.5, "La pomme c'est bon pour la santé !"));
        displayedListOfProducts.add(new Product("Pates", "pates", 0.7, "Les pâtes c'est pas cher !"));
        displayedListOfProducts.add(new Product("Beurre", "beurre", 1));
        displayedListOfProducts.add(new Product("Riz", "riz", 1.3));
        displayedListOfProducts.add(new Product("Nutella", "nutella", 3,  "Le nutella c'est pas bon pour la santé !"));
        displayedListOfProducts.add(new Product("Pates", "pates", 0.7));
        displayedListOfProducts.add(new Product("Riz", "riz", 1.3));
        displayedListOfProducts.add(new Product("Pomme", "pomme", 0.5));
        displayedListOfProducts.add(new Product("Nutella", "nutella", 3));
        displayedListOfProducts.add(new Product("Beurre", "ic_baseline_check_24", 0.5));
        displayedListOfProducts.add(new Product("Pates", "pates", 0.7));
        displayedListOfProducts.add(new Product("Riz", "riz", 1.3));
    }
}