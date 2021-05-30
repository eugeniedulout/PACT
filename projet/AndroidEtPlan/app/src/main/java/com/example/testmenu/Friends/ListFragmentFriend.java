package com.example.testmenu.Friends;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testmenu.Controller;
import com.example.testmenu.FragmentController;
import com.example.testmenu.ListProduct;
import com.example.testmenu.MainActivity;
import com.example.testmenu.Product;
import com.example.testmenu.R;
import com.example.testmenu.User;
import com.example.testmenu.adapters.RecycleViewListAdapter;
import com.example.testmenu.dialogs.DialogMarket;
import com.example.testmenu.fragments.RecetteFragment;

import java.util.ArrayList;
import java.util.Collections;

public class ListFragmentFriend extends Fragment implements View.OnClickListener   {
    private CardView switchToRecette;
    private ImageButton addNewListe;
    private ArrayList<ListProduct> listlistOfProducts = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private DialogMarket dialogMarket ;
    private TextView textWelcomeToAddList;
    private  ListView listViewOflistOfProducts;
    private int userId;
    public  ListFragmentFriend(int userId)
    {
        super();
        this.userId = userId;
    }
    /*
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("fr", "test");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            Log.e("jejejze",savedInstanceState.getString("fr") );
        }else
            Log.e("fff", "!!!");
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        View v = inflater.inflate(R.layout.fragment_liste_friend, container, false);
        Button returnToFriend = (Button)v.findViewById(R.id.returnToFriend);

        returnToFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new FriendsActivity(), getContext());
            }
        });

        TextView listeDeFriend = (TextView)v.findViewById(R.id.listeDeFriend);
        User us = Controller.getUser(userId);
        if(us != null) {
            listeDeFriend.setText("Liste de " + us.getFirstname() + " " + us.getLastname());
        }


        switchToRecette = (CardView) (v.findViewById(R.id.switchToRecette));
        switchToRecette.setOnClickListener(this::onClick);

        initList();

       /* listViewOflistOfProducts = (ListView) v.findViewById(R.id.listOflistOfProducts);

        listlistOfProducts.add(new ListProduct("Deuxieme liste",products));
        listlistOfProducts.add(new ListProduct("Troisieme liste",products));
        ListAdapter adapter = new ListAdapter(getContext(), listlistOfProducts);
        listViewOflistOfProducts.setAdapter(adapter);*/
        /*loadLists loadLists = new loadLists();
        loadLists.execute();*/



       ArrayList<ListProduct> listProducts = Controller.getUserLists(userId);
        Log.e("SIze", ""+listProducts.isEmpty());

        LinearLayoutManager layoutManager =  new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.listOflistOfProducts);
        recyclerView.setLayoutManager(layoutManager);
        Collections.reverse(listProducts);
        RecycleViewListAdapter adapter = new RecycleViewListAdapter(listProducts, false, getContext());
        recyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        listlistOfProducts.clear();
    }

    private void openDialog() {

    }
    private void initList(){

        products.add(new Product("Pomme", "pomme", 0.5, "La pomme c'est bon pour la santé !"));
        products.add(new Product("Pates", "pates",0.7,  "Les pâtes c'est pas cher !"));
        products.add(new Product("Beurre", "beurre", 1));
        products.add(new Product("Riz", "riz", 1.3));
        products.add(new Product("Nutella", "nutella", 3, "Le nutella c'est pas bon pour la santé !"));
        products.add(new Product("Pates", "pates",0.7));
        products.add(new Product("Riz", "riz", 1.3));
        products.add(new Product("Pomme", "pomme", 0.5));
        products.add(new Product("Nutella", "nutella", 3));
        products.add(new Product("Beurre", "ic_baseline_check_24", 0.5));
        products.add(new Product("Pates", "pates",0.7));
        products.add(new Product("Riz", "riz", 1.3));

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {

            case R.id.switchToRecette:
                FragmentController.swapFragmentInMainContainer(new RecetteFragmentFriend(userId), getContext());
                break;
        }
    }



    private class loadLists extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<ListProduct> listProducts = Controller.getUserLists(MainActivity.user.getId());
            for(ListProduct listProduct : listProducts)
                listlistOfProducts.add(listProduct);

            return null;
        }
    }

}