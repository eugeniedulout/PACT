package com.example.testmenu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.testmenu.Ingredient;
import com.example.testmenu.R;

import java.util.ArrayList;

public class RecetteAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Ingredient> ingredients;
    private LayoutInflater inflater;

    public RecetteAdapter(Context context, ArrayList<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
        this.inflater  = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Ingredient getItem(int position) {
        return ingredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_view_recette, null);

        Ingredient ingredient = getItem(position);
        String ingredientName  = ingredient.getName();
        String ingredientImageUrl  = ingredient.getImageUrl();

        TextView nameProduct = convertView.findViewById(R.id.recetteNameText);
        nameProduct.setText(ingredientName);

        ImageView iconIngredient= convertView.findViewById(R.id.iconRecette);

        Glide.with(context)
                .load(ingredient.getImageUrl())
                .into(iconIngredient);

        return convertView;
    }
}
