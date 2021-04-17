package com.example.testmenu.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.testmenu.Controller;
import com.example.testmenu.FragmentController;
import com.example.testmenu.Ingredient;
import com.example.testmenu.MainActivity;
import com.example.testmenu.R;
import com.example.testmenu.Recette;
import com.example.testmenu.adapters.ListOfRecettesAdapter;

import java.util.ArrayList;

public class RecetteFragment extends Fragment implements  View.OnClickListener {
    private CardView switchToListe;
    private ImageButton addNewRecette;
    private ArrayList<Recette> listOfRecette = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recette, container, false);

        switchToListe = (CardView) (v.findViewById(R.id.switchToListe));
        switchToListe.setOnClickListener(this::onClick);

        addNewRecette = (ImageButton)v.findViewById(R.id.addNewRecette);
        addNewRecette.setOnClickListener(this::onClick);

        ListView listViewRecettes = (ListView) v.findViewById(R.id.listOflistOfRecette);
        //initRecette();

        ListOfRecettesAdapter adapter = new ListOfRecettesAdapter(getContext(), listOfRecette);
        listViewRecettes.setAdapter(adapter);

        ArrayList<Recette> recettes = Controller.getUserRecettes(MainActivity.user.getId());
        for (Recette recette : recettes) {
            listOfRecette.add(recette);
        }

        listViewRecettes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Log.e("click" , " a" + listOfRecette.get(position).getRecetteName());

                listOfRecette.get(position).displayIngredientsInTheRecette(getContext());
            }
        });
        return v;
    }
   /* private void initRecette(){
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            ingredients.add(new Ingredient("oeufs","https://e7.pngegg.com/pngimages/997/205/png-clipart-egg-white-caesar-salad-chicken-scrambled-eggs-egg-food-recipe.png" ));
            ingredients.add(new Ingredient("beuure","https://img2.freepng.fr/20180810/use/kisspng-clarified-butter-portable-network-graphics-cheese-butter-name-png-ready-made-logo-effect-images-png-5b6d4bffb2ba80.3878457015338895357321.jpg" ));
            ingredients.add(new Ingredient("ingredient 3","https://e7.pngegg.com/pngimages/997/205/png-clipart-egg-white-caesar-salad-chicken-scrambled-eggs-egg-food-recipe.png" ));


        for (int k = 0; k < 8; k++){
                Recette recette = new Recette("Recette" + String.valueOf(k), ingredients, new ArrayList<String>());
                listOfRecette.add(recette);
            }
    }*/

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

    @Override
    public void onPause() {
        super.onPause();
        listOfRecette.clear();
            }
}