package com.example.testmenu.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.testmenu.FragmentController;
import com.example.testmenu.Product;
import com.example.testmenu.R;
import com.example.testmenu.fragments.ListOfProductsFragment;

import java.util.ArrayList;


public class DisplayedIngredientsFromRecetteFragment extends Fragment {

    private ArrayList<Product> productsToDisplay = new ArrayList<>();
    private String nameOfTheListSelected;
    private TextView nameOfTheListSelectedTextView;
    public DisplayedIngredientsFromRecetteFragment(ArrayList<Product> productsToDisplay, String nameOfTheListSelected){
        super();
        this.productsToDisplay = productsToDisplay;
        this.nameOfTheListSelected= nameOfTheListSelected;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_displayed_products_from_a_list, container, false);
        nameOfTheListSelectedTextView = (TextView)v.findViewById(R.id.nameOfTheListSelected);
        nameOfTheListSelectedTextView.setText(nameOfTheListSelected);

        FragmentController.swapFragment(new ListOfProductsFragment(productsToDisplay), R.id.containerOfProdcutsFromListSelected, getContext() );

        return v;
    }

}