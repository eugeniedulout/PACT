package com.example.testmenu.fragments;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.testmenu.Controller;
import com.example.testmenu.Market;
import com.example.testmenu.ProductOnSpecialOffer;
import com.example.testmenu.R;
import com.example.testmenu.adapters.PromotionMarketAdapter;

import java.util.ArrayList;

public class ListOfMarketPromotionsFragment extends Fragment {
    private Market market;
    private ArrayList<ProductOnSpecialOffer> products = new ArrayList<ProductOnSpecialOffer>();



    public ListOfMarketPromotionsFragment(Market market) {
        super();
        if(market != null)
            this.market = market;
        else
            market = new Market(1,"Auchan", "auchan", "", "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View v = inflater.inflate(R.layout.fragment_list_of_market_promotions, container, false);

        ImageView marketImage = (ImageView)v.findViewById(R.id.imageMarket);
        TextView marketNameText = (TextView) v.findViewById(R.id.marketNameText);

        marketNameText.setText("Promotions chez " + market.getMarketName());

        ArrayList<ProductOnSpecialOffer> productOnSpecialOffers = Controller.getMarketOffers(1);

       /* int id = getResources().getIdentifier(market.getMarketLogo(), "drawable", getContext().getPackageName());
        marketImage.setImageResource(id);*/
        Glide.with(getContext()).load(market.getMarketLogo()).into(marketImage);

        ListView liste = (ListView) v.findViewById(R.id.listViewOfPromotions);

        for (ProductOnSpecialOffer product : productOnSpecialOffers)
            products.add(product);

      /*  products.add(new ProductOnSpecialOffer("Pomme", "pomme", 0.5,  "La pomme c'est bon pour la santé !", 0.2, "28/02/2021"));
        products.add(new ProductOnSpecialOffer("Pates", "pates",0.7,  "Les pâtes c'est pas cher !",0.5, "29/03/2021"));
        products.add(new ProductOnSpecialOffer("Beurre", "beurre", 1,0.3, "18/04/2021"));
        products.add(new ProductOnSpecialOffer("Riz", "riz", 1.3,0.8, "25/03/2021"));
        products.add(new ProductOnSpecialOffer("Nutella", "nutella", 3,  "Le nutella c'est pas bon pour la santé !",2.2,"05/03/2021" ));*/


        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                products.get(position).displayInfo(getContext());
            }
        });
        PromotionMarketAdapter adapter = new PromotionMarketAdapter(getContext(), products);
        liste.setAdapter(adapter);
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        products.clear();
    }
}