package com.example.testmenu.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testmenu.Product;
import com.example.testmenu.R;
import com.example.testmenu.adapters.ProductAdapter;

import java.util.ArrayList;

public class ListOfProductsFragment extends Fragment{
    private Button testButton;
    private int count = 0;
    private ListView listViewOfProducts;
    private ArrayList<Product> displayedProducts = new ArrayList<Product>();

    public ListOfProductsFragment(ArrayList<Product> products) {
        super();
        this.displayedProducts = products;

    }

    public void setdisplayedProducts(ArrayList<Product> products) {
        this.displayedProducts = products;
        ProductAdapter adapter = new ProductAdapter(getContext(), products);
        listViewOfProducts.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_of_products, container, false);


        listViewOfProducts = (ListView) v.findViewById(R.id.listViewOfProducts);

        ProductAdapter adapter = new ProductAdapter(getContext(), displayedProducts);
        listViewOfProducts.setAdapter(adapter);

        listViewOfProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayedProducts.get(position).displayInfo(getContext());
            }
        });

        return v;
    }


}