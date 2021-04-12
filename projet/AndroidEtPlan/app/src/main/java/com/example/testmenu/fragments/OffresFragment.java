package com.example.testmenu.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testmenu.Controller;
import com.example.testmenu.Ingredient;
import com.example.testmenu.Market;
import com.example.testmenu.Product;
import com.example.testmenu.R;
import com.example.testmenu.adapters.RecycleViewMarketsAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import static com.example.testmenu.Controller.getAllMarkets;

public class OffresFragment extends Fragment {
    private ArrayList<String> marketLogoUrlArray = new ArrayList<String>() ;
    private ArrayList<String> marketNameArray = new ArrayList<String>() ;

    private ArrayList<String> marketLogoUrlArrayTwo = new ArrayList<String>() ;
    private ArrayList<String> marketNameArrayTwo = new ArrayList<String>() ;
    private  ArrayList<Market> markets = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View v = inflater.inflate(R.layout.fragment_offres, container, false);

        markets = Controller.getAllMarkets();
        for (int i =0; i<markets.size(); i++) {
            Log.e("Logo", " " + markets.get(i).getMarketId());
            Log.e("eeeeeeeeeeeeeeeeeee", "eeeeee");

            marketLogoUrlArray.add(markets.get(i).getMarketLogoUrl());
        }
        for (int i =0; i<markets.size(); i++) {
            marketNameArray.add(markets.get(i).getMarketName());

        }

       // ArrayList<Product> products = Controller.getAllProducts(markets.get(2).getMarketId());

        /*for(Product product : products)
            Log.e("ProductName : ", product.getName());*/

        // initImages();
        initImages2();



        /*loadMarkets loadMarkets = new loadMarkets();
        loadMarkets.execute();*/


        LinearLayoutManager layoutManager =  new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.recycleViewMarkets);
        recyclerView.setLayoutManager(layoutManager);
        RecycleViewMarketsAdapter adapter = new RecycleViewMarketsAdapter(marketLogoUrlArray, marketNameArray, getContext());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManagerTwo =  new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerViewTwo = (RecyclerView)v.findViewById(R.id.recycleViewMarketsTwo);
        recyclerViewTwo.setLayoutManager(layoutManagerTwo);
        RecycleViewMarketsAdapter adapterTwo = new RecycleViewMarketsAdapter(marketLogoUrlArrayTwo, marketNameArrayTwo, getContext());

        recyclerViewTwo.setAdapter(adapterTwo);

        return v ;
    }

    private void initImages() {

        marketLogoUrlArray.add("auchan");
        marketNameArray.add("Auchan");

        marketLogoUrlArray.add("carrefour");
        marketNameArray.add("Carrefour");

        marketLogoUrlArray.add("franprix");
        marketNameArray.add("Franprix");

        marketLogoUrlArray.add("carrefour");
        marketNameArray.add("Carrefour");

        marketLogoUrlArray.add("auchan");
        marketNameArray.add("Auchan");

        marketLogoUrlArray.add("franprix");
        marketNameArray.add("Franprix");

    }

    private void initImages2() {

        marketLogoUrlArrayTwo.add("https://upload.wikimedia.org/wikipedia/en/thumb/1/12/Carrefour_logo_no_tag.svg/1280px-Carrefour_logo_no_tag.svg.png");
        marketNameArrayTwo.add("Carrefour");

        marketLogoUrlArrayTwo.add("https://upload.wikimedia.org/wikipedia/en/thumb/1/12/Carrefour_logo_no_tag.svg/1280px-Carrefour_logo_no_tag.svg.png");
        marketNameArrayTwo.add("Carrefour");

        marketLogoUrlArrayTwo.add("https://upload.wikimedia.org/wikipedia/en/thumb/1/12/Carrefour_logo_no_tag.svg/1280px-Carrefour_logo_no_tag.svg.png");
        marketNameArrayTwo.add("Auchan");

        marketLogoUrlArrayTwo.add("https://upload.wikimedia.org/wikipedia/en/thumb/1/12/Carrefour_logo_no_tag.svg/1280px-Carrefour_logo_no_tag.svg.png");
        marketNameArrayTwo.add("Franprix");

        marketLogoUrlArrayTwo.add("https://upload.wikimedia.org/wikipedia/en/thumb/1/12/Carrefour_logo_no_tag.svg/1280px-Carrefour_logo_no_tag.svg.png");
        marketNameArrayTwo.add("Auchan");

        marketLogoUrlArrayTwo.add("https://upload.wikimedia.org/wikipedia/en/thumb/1/12/Carrefour_logo_no_tag.svg/1280px-Carrefour_logo_no_tag.svg.png");
        marketNameArrayTwo.add("Lidl");

    }


    private class loadMarkets extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            markets = Controller.getAllMarkets();
            for (int i =0; i<markets.size(); i++) {
                Log.e("Logo", " " + markets.get(i).getMarketId());
                Log.e("eeeeeeeeeeeeeeeeeee", "eeeeee");

                marketLogoUrlArray.add("https://upload.wikimedia.org/wikipedia/en/thumb/1/12/Carrefour_logo_no_tag.svg/1280px-Carrefour_logo_no_tag.svg.png");
            }
            for (int i =0; i<markets.size(); i++) {
                marketNameArray.add(markets.get(i).getMarketName());

            }

            return null;
        }
    }
}