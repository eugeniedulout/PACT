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
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.Glide;
import com.example.testmenu.fragments.ListOfMarketPromotionsFragment;
import com.example.testmenu.Market;
import com.example.testmenu.R;

import java.util.ArrayList;

public class RecycleViewMarketsAdapter extends  RecyclerView.Adapter<RecycleViewMarketsAdapter.ViewHolder> {
    private ArrayList<String> marketLogoUrlArray;
    private ArrayList<String> marketNameArray;
    private ArrayList<Integer> marketIdArray;

    private Context mContext;

    public RecycleViewMarketsAdapter(ArrayList<String> marketLogoUrlArray, ArrayList<String> marketNameArray,ArrayList<Integer> marketIdArray, Context mContext) {
        this.marketLogoUrlArray = marketLogoUrlArray;
        this.marketNameArray = marketNameArray;
        this.marketIdArray = marketIdArray;

        this.mContext = mContext;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_offres_market, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Glide.with(mContext).asBitmap().load(marketLogoUrlArray.get(position)).into(holder.marketLogo);
        holder.marketName.setText(marketNameArray.get(position));
        int imageId = mContext.getResources().getIdentifier(marketLogoUrlArray.get(position), "drawable", mContext.getPackageName());
        //holder.marketLogo.setImageResource(imageId);
        Glide.with(mContext).load(marketLogoUrlArray.get(position)).into(holder.marketLogo);
        holder.marketLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("hey", marketNameArray.get(position));
                Log.e("hey2", marketLogoUrlArray.get(position));

                Market market = new Market(marketIdArray.get(position),marketNameArray.get(position), marketLogoUrlArray.get(position), "", "");
                /*FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new ListOfMarketPromotions(market) );
                fr.addToBackStack(null).commit();*/
                FragmentTransaction fr = ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new ListOfMarketPromotionsFragment(market));
                fr.addToBackStack(null).commit();


            }
        });

    }


    @Override
    public int getItemCount() {
        return marketNameArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView marketLogo;
        TextView marketName;

        public ViewHolder(View itemView) {
            super(itemView);
            marketLogo = (ImageView) itemView.findViewById(R.id.marketLogo);
            marketName = (TextView) itemView.findViewById(R.id.marketName);

        }
    }
}
