package com.example.testmenu.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.testmenu.Controller;
import com.example.testmenu.FragmentController;
import com.example.testmenu.Ingredient;
import com.example.testmenu.ListProduct;
import com.example.testmenu.Product;
import com.example.testmenu.R;
import com.example.testmenu.adapters.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ListFragment extends Fragment implements View.OnClickListener   {
    private CardView switchToRecette;
    private ImageButton addNewListe;
    private ArrayList<ListProduct> listlistOfProducts = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private DialogMarket dialogMarket ;

    private  ListView listViewOflistOfProducts;
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


        View v = inflater.inflate(R.layout.fragment_liste, container, false);




        switchToRecette = (CardView) (v.findViewById(R.id.switchToRecette));
        switchToRecette.setOnClickListener(this::onClick);

        addNewListe = (ImageButton)v.findViewById(R.id.addNewList);
        addNewListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DialogMarket().show(getParentFragmentManager(),"Dialog !");

            }
        });

        initList();

        listViewOflistOfProducts = (ListView) v.findViewById(R.id.listOflistOfProducts);

        /*listlistOfProducts.add(new ListProduct("Deuxieme liste",products));
        listlistOfProducts.add(new ListProduct("Troisieme liste",products));*/
        ListAdapter adapter = new ListAdapter(getContext(), listlistOfProducts);
        listViewOflistOfProducts.setAdapter(adapter);
        //loadLists loadLists = new loadLists();
        //loadLists.execute();

       ArrayList<ListProduct> listProducts = Controller.getUserLists(1);
        for(ListProduct listProduct : listProducts)
            listlistOfProducts.add(listProduct);
        adapter = new ListAdapter(getContext(), listlistOfProducts);
        listViewOflistOfProducts.setAdapter(adapter);



        listViewOflistOfProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Log.e("click" , " a" + listlistOfProducts.get(position).getListName());
                listlistOfProducts.get(position).displayProductsInTheList(getContext());
            }
        });

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
            case R.id.addNewList:
               /* if(!BuildingListFragment.getInstance().isAdded())
                    FragmentController.swapFragmentInMainContainer(BuildingListFragment.getInstance(), getContext());*/
                break;
            case R.id.switchToRecette:
                FragmentController.swapFragmentInMainContainer(new RecetteFragment(), getContext());
                break;
        }
    }



    private class loadLists extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {


            return null;
        }
    }

}