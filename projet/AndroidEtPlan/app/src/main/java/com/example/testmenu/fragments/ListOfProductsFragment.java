package com.example.testmenu.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testmenu.Product;
import com.example.testmenu.R;
import com.example.testmenu.adapters.RecycleViewProductAdapter;

import java.util.ArrayList;

public class ListOfProductsFragment extends Fragment{
    private Button testButton;
    private int count = 0;
    private ArrayList<Product> displayedProducts = new ArrayList<Product>();

    public ListOfProductsFragment(ArrayList<Product> products) {
        super();
        this.displayedProducts = products;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_of_products, container, false);


        LinearLayoutManager layoutManager =  new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.listViewOfProducts);
        recyclerView.setLayoutManager(layoutManager);
        RecycleViewProductAdapter adapter = new RecycleViewProductAdapter(displayedProducts, getContext());
        recyclerView.setAdapter(adapter);
        /*

        ProductAdapter adapter = new ProductAdapter(getContext(), displayedProducts);
        listViewOfProducts.setAdapter(adapter);

        listViewOfProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayedProducts.get(position).displayInfo(getContext());
            }
        });*/
/*
        adapter.setOnItemClickListener(new RecycleViewProductAdapter.OnItemClickListener() {

            @Override
            public void onDeleteClick(int position) {
                displayedProducts.remove(position);
                RecycleViewProductAdapter adapter = new RecycleViewProductAdapter(displayedProducts, getContext());
                recyclerView.setAdapter(adapter);

            }
        });*/

        return v;
    }


}