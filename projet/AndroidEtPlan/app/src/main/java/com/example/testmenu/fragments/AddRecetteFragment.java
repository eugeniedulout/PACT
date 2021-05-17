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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testmenu.FragmentController;
import com.example.testmenu.Ingredient;
import com.example.testmenu.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class AddRecetteFragment extends Fragment {

    private TextView recetteName;
    private String titleHtmlPage;
    private Button importButton;
    private ArrayList<Ingredient> listOfIngredients = new ArrayList<>();
    private FloatingActionButton addRecetteButton;
    private  ArrayList<String> consignes = new ArrayList<>();
    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_recette, container, false);

        recetteName = (TextView) v.findViewById(R.id.recetteNameText);

        importButton = (Button) v.findViewById(R.id.importButton);
        addRecetteButton= (FloatingActionButton)v.findViewById(R.id.addRecetteButton);

        addRecetteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor edt = pref.edit();
                edt.putString("titre_recette", titleHtmlPage);
                edt.commit();
                FragmentController.swapFragmentInMainContainer(new RecetteFragment(), getContext());
            }
        });

        FragmentController.swapFragment(new WebViewFragment(), R.id.containerWebView, getContext());

        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consignes.clear();
                getSiteData site = new getSiteData();
                site.execute();


            }
        });


        return v;
    }

    private class getSiteData extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                listOfIngredients.clear();

                Document doc;
                Elements ingredients;
                Elements quantite;
                Elements imageUrls;
                Elements unitQuantity;
                Elements consigne;
                try {
                     doc = Jsoup.connect(url).get();
                     titleHtmlPage = getOnlyTitle(doc.title());
                         ingredients = doc.select("span.ingredient-name");
                         quantite = doc.select("span.quantity");
                         unitQuantity = doc.select("span.unit");

                         imageUrls = doc.select("img.item__icon");
                         consigne = doc.select("div.recipe-step-list__head"); // Faire un .next() pour avoir les ingredients


                        Log.e("size", String.valueOf(consigne.size()));
                        // recetteName.setText(string);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recetteName.setText(titleHtmlPage);
                            }
                        });

                        for(int i =0; i < ingredients.size(); i++) {
                            Log.e("Iteration " + String.valueOf(i), quantite.eq(i).text() + unitQuantity.eq(i).text() + " " +  ingredients.eq(i).text());
                            String ingredientName = quantite.eq(i).text() + unitQuantity.eq(i).text() +  " " + ingredients.eq(i).text();
                            String imageUrlIngredient = imageUrls.eq(i).attr("data-src");
                            listOfIngredients.add(new Ingredient( ingredientName, imageUrlIngredient));
                        }

                        for(int i =0; i < consigne.size(); i++) {
                            Log.e("Iteration " + String.valueOf(i), consigne.eq(i).next().text());
                            consignes.add(consigne.eq(i).next().text());
                        }

                        for(int i =0; i < imageUrls.size(); i++)
                            Log.e("Iteration " + String.valueOf(i), imageUrls.eq(i).attr("data-src"));
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "Wrong Url !", Toast.LENGTH_LONG).show();

                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Method which allows to get the title
     * @param title : title getting from __________
     * @return the title
     */
    private String getOnlyTitle(String title){

        String result;
        int index = title.indexOf(":");
        result = title.substring(0,index);
        Log.e("ee",result);
        return result;

    }
}