package com.example.testmenu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testmenu.Product;
import com.example.testmenu.R;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<Product> products;
    private LayoutInflater inflater;
    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
        this.inflater  = LayoutInflater.from(context);
    }

    
    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_view_layout, null);

        Product currentProduct = getItem(position);
        String productName  = currentProduct.getName();
        double productPrice  = currentProduct.getPrice();
        String productImage = currentProduct.getProductImageUrl();
        TextView nameProduct = convertView.findViewById(R.id.nameProduct);
        nameProduct.setText(productName);
        TextView priceProduct = convertView.findViewById(R.id.priceProduct);
        priceProduct.setText(String.valueOf(productPrice ) + " €");

        int imageId = context.getResources().getIdentifier(productImage, "drawable", context.getPackageName());

        ImageView iconProduct= convertView.findViewById(R.id.iconRecette);
        iconProduct.setImageResource(imageId);


        return convertView;
    }
}
