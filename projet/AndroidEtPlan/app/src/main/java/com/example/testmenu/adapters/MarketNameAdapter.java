package com.example.testmenu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.testmenu.Market;
import com.example.testmenu.R;

import java.util.ArrayList;

public class MarketNameAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Market> markets;
    private LayoutInflater inflater;
    public MarketNameAdapter(Context context, ArrayList<Market> markets) {
        this.context = context;
        this.markets = markets;
        this.inflater  = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return markets.size();
    }

    @Override
    public Market getItem(int position) {
        return markets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_market_names, null);

        Market marketSelected = getItem(position);
        String marketName  = marketSelected.getMarketName();
        String marketUrl = marketSelected.getMarketLogoUrl();

        TextView marketNameTextView = (TextView)convertView.findViewById(R.id.marketName);
        marketNameTextView.setText(marketName);

        ImageView marketImageView = (ImageView) convertView.findViewById(R.id.marketLogo);

        Glide.with(context).load(marketUrl).into(marketImageView);

        return convertView;
    }
}
