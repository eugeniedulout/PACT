package com.example.testmenu.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testmenu.Product;
import com.example.testmenu.R;

public class ProductInfoFragment extends Fragment {
    private Product product;


    public ProductInfoFragment(Product product) {
        super();
        this.product = product;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_info, container, false);

        ImageView productImage = (ImageView)v.findViewById(R.id.productImage);
        TextView productPrice = (TextView) v.findViewById(R.id.productPriceText);
        TextView productName = (TextView) v.findViewById(R.id.productName);
        TextView productDescription = (TextView) v.findViewById(R.id.productDescriptionText);
        TextView environmentalScore = (TextView) v.findViewById(R.id.environmentalScoreText);

        productPrice.setText(String.valueOf(product.getPrice()/100) + " €");
        productName.setText(product.getName());
        //productImage.setImageResource(getResources().getDrawable(R.id.));
        productDescription.setText(product.getDescription());

        

        int id = getResources().getIdentifier(product.getProductImageUrl(), "drawable", getContext().getPackageName());
        productImage.setImageResource(id);



        return v;
    }

}