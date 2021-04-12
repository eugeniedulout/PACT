package com.example.testmenu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.testmenu.Ingredient;
import com.example.testmenu.R;
import com.example.testmenu.Recette;

import java.util.ArrayList;
import java.util.List;

public class ListOfRecettesAdapter extends BaseAdapter {
    private Context context;
    private List<Recette> listOfRecettes;
    private LayoutInflater inflater;
    public ListOfRecettesAdapter(Context context, List<Recette> listOfRecettes) {
        this.context = context;
        this.listOfRecettes = listOfRecettes;
        this.inflater  = LayoutInflater.from(context);
    }

    
    @Override
    public int getCount() {
        return listOfRecettes.size();
    }

    @Override
    public Recette getItem(int position) {
        return listOfRecettes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_view_recette, null);

        Recette currentListOfProduct = getItem(position);
        String recetteName  = currentListOfProduct.getRecetteName();

        ArrayList<Ingredient> listOfIngredients = currentListOfProduct.getListOfIngredients();

        TextView recetteNameText = (TextView)convertView.findViewById(R.id.recetteNameText);
        recetteNameText.setText(recetteName);

        return convertView;
    }
}
