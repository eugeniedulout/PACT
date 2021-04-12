
package com.example.testmenu.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testmenu.Ingredient;
import com.example.testmenu.R;
import com.example.testmenu.Recette;
import com.example.testmenu.adapters.RecetteAdapter;
import com.example.testmenu.adapters.RecycleViewConsigneRecetteAdapter;

import java.util.ArrayList;

public class DisplayIngredientsFromRecetteFragment extends Fragment {

    private TextView recetteName;
    private ListView listViewOfIngredients;
    private ArrayList<Ingredient> listOfIngredients = new ArrayList<>();
    private ArrayList<String> consignes = new ArrayList<>();
    private Recette recette;

    public DisplayIngredientsFromRecetteFragment(Recette recette) {
        this.recette = recette;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.display_ingredients_from_a_recette_fragment, container, false);

        recetteName = (TextView) v.findViewById(R.id.rName);

        listViewOfIngredients = (ListView) v.findViewById(R.id.listViewIngre);

        recetteName.setText(recette.getRecetteName());

        listOfIngredients = recette.getListOfIngredients();
        consignes = recette.getCookingInstructions();

        Log.e("RecetteName", "e" + recette.getRecetteName());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.consigneRecycle);
        recyclerView.setLayoutManager(layoutManager);
        RecycleViewConsigneRecetteAdapter adapterConsigne = new RecycleViewConsigneRecetteAdapter(consignes, getContext());
        recyclerView.setAdapter(adapterConsigne);

        RecetteAdapter adapter = new RecetteAdapter(getContext(), listOfIngredients);
        listViewOfIngredients.setAdapter(adapter);
        return v;
    }
}