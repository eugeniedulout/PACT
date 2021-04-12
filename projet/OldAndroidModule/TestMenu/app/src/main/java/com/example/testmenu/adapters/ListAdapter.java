package com.example.testmenu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.testmenu.ListProduct;
import com.example.testmenu.Product;
import com.example.testmenu.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<ListProduct> listOflistOfProducts;
    private LayoutInflater inflater;
    public ListAdapter(Context context, List<ListProduct> listOflistOfProducts) {
        this.context = context;
        this.listOflistOfProducts = listOflistOfProducts;
        this.inflater  = LayoutInflater.from(context);
    }

    
    @Override
    public int getCount() {
        return listOflistOfProducts.size();
    }

    @Override
    public ListProduct getItem(int position) {
        return listOflistOfProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_view_list, null);

        ListProduct currentListOfProduct = getItem(position);
        String listName  = currentListOfProduct.getListName();

        ArrayList<Product> products = currentListOfProduct.getListOfProducts();

        TextView listNameText = (TextView)convertView.findViewById(R.id.listName);
        listNameText.setText(listName);

        return convertView;
    }
}
