package com.example.testmenu.fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testmenu.ProductOnSpecialOffer;
import com.example.testmenu.R;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductOnSpecialOfferInfoFragment extends Fragment {
    private ProductOnSpecialOffer productOnSpecialOffer;


    public ProductOnSpecialOfferInfoFragment(ProductOnSpecialOffer productOnSpecialOffer) {
        super();
        this.productOnSpecialOffer = productOnSpecialOffer;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_on_special_offer_info, container, false);

        ImageView productImage = (ImageView)v.findViewById(R.id.productOnSpecialOfferImage);
        TextView productPrice = (TextView) v.findViewById(R.id.productOnSpecialOfferPriceText);
        TextView productName = (TextView) v.findViewById(R.id.productOnSpecialOfferName);
        TextView productDescription = (TextView) v.findViewById(R.id.productOnSpecialOfferDescriptionText);
        TextView environmentalScore = (TextView) v.findViewById(R.id.environmentalScoreText);
        TextView productNewPrice = (TextView) v.findViewById(R.id.productOnSpecialOfferNewPriceText);
        TextView expirationDateText = (TextView) v.findViewById(R.id.expirationDateText);

        BigDecimal bd = new BigDecimal(productOnSpecialOffer.getPrice()/100).setScale(2, RoundingMode.HALF_UP);
        double salary = bd.doubleValue();
        productPrice.setText(String.valueOf(salary + " €"));
        productPrice.setPaintFlags(productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        productName.setText(productOnSpecialOffer.getName());

        productDescription.setText(productOnSpecialOffer.getDescription());

        bd = new BigDecimal(productOnSpecialOffer.getNewPrice()/100).setScale(2, RoundingMode.HALF_UP);
        salary = bd.doubleValue();

        productNewPrice.setText(String.valueOf(salary) + " €");

        expirationDateText.setText("Promotion expire le " + productOnSpecialOffer.getExpirationDate());


        int id = getResources().getIdentifier(productOnSpecialOffer.getProductImageUrl(), "drawable", getContext().getPackageName());
        productImage.setImageResource(id);



        return v;
    }

}