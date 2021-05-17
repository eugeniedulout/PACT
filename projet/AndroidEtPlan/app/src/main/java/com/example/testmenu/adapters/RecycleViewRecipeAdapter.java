package com.example.testmenu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testmenu.R;
import com.example.testmenu.Recette;

import java.util.ArrayList;

public class RecycleViewRecipeAdapter extends  RecyclerView.Adapter<RecycleViewRecipeAdapter.ViewHolder> {
    private ArrayList<String> RecetteNames = new ArrayList<>();
    private Context mContext;
    private ArrayList<Recette> recipes = new ArrayList<>();

    public RecycleViewRecipeAdapter(ArrayList<Recette> recipes, Context mContext) {
        this.recipes = recipes;
        this.mContext = mContext;
        int size= recipes.size();

        for(int i =0; i< size; i++) {
            RecetteNames.add(recipes.get(i).getRecetteName());
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
        holder.listNameTextView.setText(RecetteNames.get(position));
        //holder.marketLogo.setImageResource(imageId);
        int numberOfBackgroundCooking = 9;
        if (position == 1) {
            int colorId = mContext.getResources().getColor(R.color.colorm);
            holder.listNameTextView.setTextColor(colorId);
        }
        else
        {
            int colorId = mContext.getResources().getColor(R.color.white);
            holder.listNameTextView.setTextColor(colorId);
        }
        int indice = ((position+7) % numberOfBackgroundCooking)+1;


        int imageId = mContext.getResources().getIdentifier("test_background_" + ""+indice, "drawable", mContext.getPackageName());
        holder.cookingBackground.setBackgroundResource(imageId);

        holder.cookingBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipes.get(position).displayIngredientsInTheRecette(mContext);
            }
        });

    }


    @Override
    public int getItemCount() {
        return RecetteNames.size();
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
