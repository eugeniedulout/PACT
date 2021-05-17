package com.example.testmenu.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testmenu.Controller;
import com.example.testmenu.FragmentController;
import com.example.testmenu.Market;
import com.example.testmenu.Product;
import com.example.testmenu.R;
import com.example.testmenu.adapters.RecycleViewImportProductAdapter;
import com.example.testmenu.fragments.BuildingListFragment;

import java.util.ArrayList;

public class DialogSuggestedProduct extends DialogFragment {

    private RecyclerView recycleViewproductSuggested;
    private ArrayList<Market> markets = new ArrayList<>();

    private ArrayList<String> ingredientsName = new ArrayList<>();
    private ArrayList<ArrayList<Product>>  arrayOfProducts = new ArrayList<>();
    private ArrayList<ArrayList<Product>>  arrayOfProductsSelected = new ArrayList<>();
    private ArrayList<ArrayList<Boolean>> selectedProductPositionArray = new ArrayList<>();
    private RecycleViewImportProductAdapter adapter;
    private  AddProductToRecycleViewListener listener;
    ArrayList<Product> productsToAdd = new ArrayList<>();
    ArrayList<Product> currentProductInTheList = new ArrayList<>();
    private String listName;
    private  int marketId;
    private ImageView imageDroit;
    public void setListener( AddProductToRecycleViewListener listener){
        this.listener = listener;
    }

    public interface AddProductToRecycleViewListener {
        void addProductToRecycleViewListener(ArrayList<ArrayList<Product>> products);
    }

    private  int compteur = 0;
    private CardView buttonGauche;
    private CardView buttonDroit;

    public interface OnInputListener {
        void sendInput(ArrayList<ArrayList<Product>> input);
    }

    public OnInputListener onInputListener;


    public DialogSuggestedProduct(ArrayList<String> ingredientsName, ArrayList<ArrayList<Product>>  arrayOfProducts, ArrayList<Product> currentProductInTheList, String listeName, int markerId) {
        super();
        this.arrayOfProducts = arrayOfProducts;
        this.ingredientsName = ingredientsName;
        this.listName = listeName;
        this.marketId = markerId;
        this.currentProductInTheList = currentProductInTheList;
        for(int i=0; i< arrayOfProducts.size(); i++) {
            selectedProductPositionArray.add(new ArrayList<Boolean>());
            for(int j=0; j<arrayOfProducts.get(i).size(); j++) {
                selectedProductPositionArray.get(i).add(false);
            }
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_suggested_product, null);
        buttonGauche = (CardView) view.findViewById(R.id.gaucheButton);
        buttonDroit = (CardView) view.findViewById(R.id.droiteButton);

        imageDroit = (ImageView)view.findViewById(R.id.imageDroit);

        for(int i=0; i<ingredientsName.size(); i++) {
            arrayOfProductsSelected.add(null);
        }

        TextView ingredientNameSugg = (TextView)view.findViewById(R.id.ingredientNameSugg);

        buttonGauche.setVisibility(View.INVISIBLE);



        ingredientNameSugg.setText(ingredientsName.get(compteur));


        ArrayList<String> marketsNames = new ArrayList<String>();

        markets = Controller.getAllMarkets();

        LinearLayoutManager layoutManager =  new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycleViewproductSuggested = (RecyclerView)view.findViewById(R.id.ListViewproductSuggested);

        recycleViewproductSuggested.setLayoutManager(layoutManager);
        adapter = new RecycleViewImportProductAdapter(arrayOfProducts.get(0),selectedProductPositionArray.get(0), getContext());
        recycleViewproductSuggested.setAdapter(adapter);

        /*listlistOfProducts.add(new ListProduct("Deuxieme liste",products));
        listlistOfProducts.add(new ListProduct("Troisieme liste",products));*/

        /*productAdapter = new ProductAdapter(getContext(), arrayOfProducts.get(0));
        ListViewproductSuggested.setAdapter(productAdapter);*/
        /*loadLists loadLists = new loadLists();
        loadLists.execute();*/

        buttonGauche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Boolean> areProductSelected = adapter.getProductSelected();
                selectedProductPositionArray.set(compteur, areProductSelected);
                imageDroit.setImageResource(R.drawable.ic_baseline_arrow_right_24);

                arrayOfProductsSelected.set(compteur,getListProductSelected(areProductSelected, compteur));
                for(int i=0; i<arrayOfProductsSelected.get(compteur).size(); i++) {
                    Log.e("produo", arrayOfProducts.get(compteur).get(i).getName());
                }

                if(compteur != 0)
                    compteur--;
                buttonGauche.setVisibility(View.VISIBLE);
                buttonDroit.setVisibility(View.VISIBLE);

                ingredientNameSugg.setText(ingredientsName.get(compteur));
                adapter = new RecycleViewImportProductAdapter(arrayOfProducts.get(compteur), selectedProductPositionArray.get(compteur), getContext());
                recycleViewproductSuggested.setAdapter(adapter);
                if(compteur == 0) {
                    buttonGauche.setVisibility(View.INVISIBLE);
                    buttonDroit.setVisibility(View.VISIBLE);


                }

            }
        });


        buttonDroit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (compteur == ingredientsName.size() - 1) {

                    for(int i=0; i< arrayOfProductsSelected.size(); i++) {
                        if(arrayOfProductsSelected.get(i)!= null) {
                            for (int j = 0; j < arrayOfProductsSelected.get(i).size(); j++) {
                                Log.e("TAGG", "Les produits selectionnés sont : " + arrayOfProductsSelected.get(i).get(j).getName());
                                currentProductInTheList.add(arrayOfProductsSelected.get(i).get(j));
                            }
                        }
                    }
                    //listener.addProductToRecycleViewListener(arrayOfProductsSelected);


                    FragmentController.swapFragmentInMainContainer(new BuildingListFragment(currentProductInTheList, listName, marketId), getContext());
                    getDialog().dismiss();

                } else {

                    ArrayList<Boolean> areProductSelected = adapter.getProductSelected();
                    selectedProductPositionArray.set(compteur, areProductSelected);


                    arrayOfProductsSelected.set(compteur, getListProductSelected(areProductSelected, compteur));
                    for (int i = 0; i < arrayOfProductsSelected.get(compteur).size(); i++) {
                        Log.e("produo", arrayOfProducts.get(compteur).get(i).getName());
                    }
                    compteur++;
                    ingredientNameSugg.setText(ingredientsName.get(compteur));
                    buttonGauche.setVisibility(View.VISIBLE);
                    buttonDroit.setVisibility(View.VISIBLE);
                    adapter = new RecycleViewImportProductAdapter(arrayOfProducts.get(compteur), selectedProductPositionArray.get(compteur), getContext());
                    recycleViewproductSuggested.setAdapter(adapter);
                    if (compteur == ingredientsName.size() - 1) {
                        imageDroit.setImageResource(R.drawable.ic_baseline_add_24);

                        buttonGauche.setVisibility(View.VISIBLE);
                    }
                }
            }
        });




        /*ListViewproductSuggested.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Log.e("click" , " a" + markets.get(position).getMarketName());
                FragmentController.swapFragmentInMainContainer(BuildingListFragment.getInstance() ,getContext());
                getDialog().dismiss();
            }
        });*/

        builder.setView(view);

        /*builder.setItems(nameMarket, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("hehehe",nameMarket[which]);
                FragmentController.swapFragmentInMainContainer(BuildingListFragment.getInstance() ,getContext());
            }
        });*/

        return builder.create();
    }
    private ArrayList<Product> getListProductSelected(ArrayList<Boolean> productSelected, int compteur) {

        ArrayList<Product> listProductSelected = new ArrayList<>();
        for(int i=0; i<arrayOfProducts.get(compteur).size(); i++) {
            if(productSelected.get(i))
                listProductSelected.add(arrayOfProducts.get(compteur).get(i));
        }
        return listProductSelected;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onInputListener = (OnInputListener) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e("TAG", "onAttach: " + e.getMessage());
        }
    }
}
