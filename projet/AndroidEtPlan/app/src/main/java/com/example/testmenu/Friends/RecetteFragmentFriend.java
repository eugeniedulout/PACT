package com.example.testmenu.Friends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testmenu.Controller;
import com.example.testmenu.FragmentController;
import com.example.testmenu.MainActivity;
import com.example.testmenu.R;
import com.example.testmenu.Recette;
import com.example.testmenu.User;
import com.example.testmenu.adapters.RecycleViewRecipeAdapter;
import com.example.testmenu.fragments.ListFragment;
import com.example.testmenu.fragments.WebViewFragment;

import java.util.ArrayList;
import java.util.Collections;

public class RecetteFragmentFriend extends Fragment implements  View.OnClickListener {
    private CardView switchToListe;
    private ImageButton addNewRecette;
    private ArrayList<Recette> listOfRecette = new ArrayList<>();
    private TextView textWelcomeToAddRecette;
    private int userId;
    public  RecetteFragmentFriend(int userId)
    {
        super();
        this.userId = userId;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recette_friend, container, false);

        switchToListe = (CardView) (v.findViewById(R.id.switchToListe));
        switchToListe.setOnClickListener(this::onClick);
        Button returnToFriend = (Button)v.findViewById(R.id.returnFriend);

        returnToFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new FriendsActivity(), getContext());
            }
        });

        TextView recetteDeFriend = (TextView)v.findViewById(R.id.recetteDeFriend);
        User us = Controller.getUser(userId);
        if(us != null) {
            recetteDeFriend.setText("Recette de " + us.getFirstname() + " " + us.getLastname());
        }
        //initRecette();

        ArrayList<Recette> recettes = Controller.getUserRecettes(userId);

        LinearLayoutManager layoutManager =  new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);


        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.listOflistOfRecette);
        recyclerView.setLayoutManager(layoutManager);
        Collections.reverse(recettes);

        RecycleViewRecipeAdapter adapter = new RecycleViewRecipeAdapter(recettes, getContext());
        recyclerView.setAdapter(adapter);
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
            default:
                destinationFragment = new ListFragmentFriend(userId);
        }
        FragmentController.swapFragmentInMainContainer(destinationFragment, getContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        listOfRecette.clear();
            }
}