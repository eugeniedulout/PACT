package com.example.testmenu.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.testmenu.Controller;
import com.example.testmenu.ImportRecipe;
import com.example.testmenu.Ingredient;
import com.example.testmenu.MainActivity;
import com.example.testmenu.Product;
import com.example.testmenu.ProductOnSpecialOffer;
import com.example.testmenu.R;
import com.example.testmenu.Recette;

import java.util.ArrayList;

public class DialogRecettes extends DialogFragment {
    private ArrayList<String> ingredientsName = new ArrayList<>();
    private ArrayList<Product> products;
    private  ArrayList<Recette> recettes;
    private  ArrayList<Product> currentProduct = new ArrayList<>();
    private  String listName;
    private int marketId;
    public DialogRecettes(int marketId, ArrayList<Product> currentProduct, String listName){
        this.marketId = marketId;
        this.currentProduct = currentProduct;
        this.listName = listName;

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_list, null);

        recettes = Controller.getUserRecettes(MainActivity.user.getId());
        products = Controller.getAllProducts(marketId);

        ArrayList<ProductOnSpecialOffer> productsFromMarketPromotions = Controller.getMarketOffers(marketId);

        for(int i =0; i<productsFromMarketPromotions.size();i++){
            products.add(productsFromMarketPromotions.get(i));
        }
        String[] listNames = new String[recettes.size()];
        for(int i =0 ; i<recettes.size(); i++) {
            listNames[i] = recettes.get(i).getRecetteName();
        }
        builder.setView(view).setTitle("Recettes");
        builder.setItems(listNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getDialog().dismiss();
                showDialog(which);
                Log.e("hehehe",listNames[which]);

            }
        });

        return builder.create();
    }

    private void showDialog(int which) {

        ArrayList<Ingredient> ingredients = recettes.get(which).getListOfIngredients();
        for(int i =0 ; i< ingredients.size(); i++) {
            ingredientsName.add(ingredients.get(i).getName());
        }

        ImportRecipe importRecipe = new ImportRecipe(ingredientsName, products);

        ArrayList<ArrayList<Product>> arrayProductImpoerted = new ArrayList<>();
        for(int i=0; i< ingredientsName.size(); i++) {
            arrayProductImpoerted.add(importRecipe.getImportProduct(i));
        }

        for(int i=0; i<arrayProductImpoerted.size(); i++) {
            for(int j=0; j<arrayProductImpoerted.get(i).size(); j++) {
                Log.e("Products for ingredient " + ingredients.get(i).getName(), arrayProductImpoerted.get(i).get(j).getName());
                Log.e("Products for ingredient " + ingredients.get(i).getName(), "eee");
            }

        }
        DialogSuggestedProduct dialogSuggestedProduct = new DialogSuggestedProduct(ingredientsName, arrayProductImpoerted, currentProduct, listName, marketId);
        dialogSuggestedProduct.setTargetFragment(getParentFragment(), 1);

        dialogSuggestedProduct.show(getParentFragmentManager(),"Dialog !");
    }


}
