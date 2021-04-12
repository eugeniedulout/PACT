package com.example.testmenu.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testmenu.FragmentController;
import com.example.testmenu.Ingredient;
import com.example.testmenu.Product;
import com.example.testmenu.R;
import com.example.testmenu.Recette;
import com.example.testmenu.adapters.ProductAdapter;
import com.example.testmenu.adapters.RecetteAdapter;
import com.example.testmenu.adapters.RecycleViewConsigneRecetteAdapter;

import java.util.ArrayList;


public class DisplayedProductsFromAListFragment extends Fragment {

    private ArrayList<Product> productsToDisplay = new ArrayList<>();
    private String nameOfTheListSelected;
    private TextView nameOfTheListSelectedTextView;
    public DisplayedProductsFromAListFragment(ArrayList<Product> productsToDisplay, String nameOfTheListSelected){
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

        ListView listViewOfProducts = (ListView) v.findViewById(R.id.productListView);

        ProductAdapter adapter = new ProductAdapter(getContext(), productsToDisplay);
        listViewOfProducts.setAdapter(adapter);

        listViewOfProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                productsToDisplay.get(position).displayInfo(getContext());
            }
        });

        return v;
    }

}
