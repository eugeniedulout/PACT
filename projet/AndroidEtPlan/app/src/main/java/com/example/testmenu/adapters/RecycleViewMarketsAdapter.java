package com.example.testmenu.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testmenu.Market;
import com.example.testmenu.R;
import com.example.testmenu.fragments.ListOfMarketPromotionsFragment;

import java.util.ArrayList;

public class RecycleViewMarketsAdapter extends  RecyclerView.Adapter<RecycleViewMarketsAdapter.ViewHolder> {
    private ArrayList<String> marketLogoUrlArray = new ArrayList<>();
    private ArrayList<String> marketNameArray= new ArrayList<>();
    private ArrayList<Integer> marketIdArray= new ArrayList<>();
    private ArrayList<String> marketOpenHour= new ArrayList<>();
    private ArrayList<String> marketCloseHour= new ArrayList<>();

    private ArrayList<Market> markets = new ArrayList<>();;
    private Context mContext;

    public RecycleViewMarketsAdapter(ArrayList<Market> markets, Context mContext) {


        this.mContext = mContext;
        this.markets = markets;

        for(int i = 0; i<markets.size(); i++) {
            marketLogoUrlArray.add(markets.get(i).getMarketLogoUrl());
            marketNameArray.add(markets.get(i).getMarketName());
            marketIdArray.add(markets.get(i).getMarketId());
            marketOpenHour.add(markets.get(i).getOpenHour());
            marketCloseHour.add(markets.get(i).getCloseHour());


        }
        if(marketCloseHour.isEmpty()) {
            for(int i = 0; i<markets.size(); i++) {

                marketCloseHour.add("17");
            }
        }

        if(marketOpenHour.isEmpty()) {
            for(int i = 0; i<markets.size(); i++) {

                marketOpenHour.add("17");
            }
        }
    }

    public RecycleViewMarketsAdapter(ArrayList<String> marketLogoUrlArray, ArrayList<String> marketNameArray, Context mContext) {
        this.marketLogoUrlArray = marketLogoUrlArray;
        this.marketNameArray = marketNameArray;
        this.marketIdArray =  new ArrayList<Integer>();
        for (int i =0; i<marketLogoUrlArray.size(); i++)
            marketIdArray.add(1);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_offres_market_better, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.marketName.setText(marketNameArray.get(position));

        Glide.with(mContext).load(marketLogoUrlArray.get(position)).into(holder.marketLogo);

    }


    @Override
    public int getItemCount() {
        return marketNameArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView marketLogo;
        TextView marketName;
        TextView marketHour;
        public ViewHolder(View itemView) {
            super(itemView);
            marketLogo = (ImageView) itemView.findViewById(R.id.marketLogo);
            marketName = (TextView) itemView.findViewById(R.id.marketName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Log.e("hey", marketNameArray.get(position));
                    Log.e("hey2", marketLogoUrlArray.get(position));

                    Market market = new Market(marketIdArray.get(position),marketNameArray.get(position), marketLogoUrlArray.get(position), "", "");
                    FragmentTransaction fr = ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction();
                    fr.replace(R.id.container, new ListOfMarketPromotionsFragment(market));
                    fr.addToBackStack(null).commit();


                }
            });


        }
    }
}
