package com.example.testmenu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testmenu.ListProduct;
import com.example.testmenu.R;
import com.example.testmenu.Recette;

import java.util.ArrayList;

public class RecycleViewListAdapter extends  RecyclerView.Adapter<RecycleViewListAdapter.ViewHolder> {
    private ArrayList<String> listNames = new ArrayList<>();
    private ArrayList<ListProduct> listProducts = new ArrayList<>();
    private Context mContext;
    private ArrayList<Recette> recipes = new ArrayList<>();
    private boolean canEdit;
    public RecycleViewListAdapter(ArrayList<ListProduct> listProducts, boolean canEdit, Context mContext) {
        this.listProducts = listProducts;
        this.mContext = mContext;
        int size= listProducts.size();
        this.canEdit = canEdit;
        for(int i =0; i< size; i++) {
            listNames.add(listProducts.get(i).getListName());
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_main, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Glide.with(mContext).asBitmap().load(marketLogoUrlArray.get(position)).into(holder.marketLogo);
        holder.listNameTextView.setText(listNames.get(position));
        //holder.marketLogo.setImageResource(imageId);
        int numberOfBackgroundCooking = 7;

        int indice = (position  % numberOfBackgroundCooking)+1;
        int imageId = mContext.getResources().getIdentifier("test_background_" + ""+indice, "drawable", mContext.getPackageName());
        holder.cookingBackground.setBackgroundResource(imageId);

        holder.cookingBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listProducts.get(position).displayProductsInTheList(mContext, canEdit);
            }
        });

    }


    @Override
    public int getItemCount() {
        return listNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView listNameTextView;
        RelativeLayout cookingBackground;
        public ViewHolder(View itemView) {
            super(itemView);
            listNameTextView = (TextView) itemView.findViewById(R.id.listeNameW);
            cookingBackground = (RelativeLayout) itemView.findViewById(R.id.cookingBackground);

        }
    }
}
