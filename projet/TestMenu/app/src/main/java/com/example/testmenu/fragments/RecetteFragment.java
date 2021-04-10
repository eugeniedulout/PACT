package com.example.testmenu.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.testmenu.FragmentController;
import com.example.testmenu.Ingredient;
import com.example.testmenu.R;
import com.example.testmenu.Recette;
import com.example.testmenu.adapters.ListOfRecettesAdapter;

import java.util.ArrayList;

public class RecetteFragment extends Fragment implements  View.OnClickListener {
    private CardView switchToListe;
    private ImageButton addNewRecette;
    private ArrayList<Recette> listOfRecette = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String titreRecette = pref.getString("titre_recette", "empty");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        listOfRecette.add(new Recette(titreRecette, ingredients, new ArrayList<String>()));
        Log.e("onCreate", "On rentre dans le fragment");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recette, container, false);

        switchToListe = (CardView) (v.findViewById(R.id.switchToListe));
        switchToListe.setOnClickListener(this::onClick);

        addNewRecette = (ImageButton)v.findViewById(R.id.addNewRecette);
        addNewRecette.setOnClickListener(this::onClick);

        ListView listViewRecettes = (ListView) v.findViewById(R.id.listOflistOfRecette);
        initRecette();

        ListOfRecettesAdapter adapter = new ListOfRecettesAdapter(getContext(), listOfRecette);
        listViewRecettes.setAdapter(adapter);


        return v;
    }
    private void initRecette(){
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            for (int k = 0; k < 8; k++){
                Recette recette = new Recette("Recette" + String.valueOf(k), ingredients, new ArrayList<String>());
                listOfRecette.add(recette);
            }
    }

    @Override
    public void onClick(View v) {
        Fragment destinationFragment;
        switch(v.getId()) {
            case R.id.addNewRecette:
                destinationFragment = new WebViewFragment();
                break;
            default:
                destinationFragment = new ListFragment();
        }
        FragmentController.swapFragmentInMainContainer(destinationFragment, getContext());
    }
}