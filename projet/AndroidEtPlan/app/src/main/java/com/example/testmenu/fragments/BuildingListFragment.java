package com.example.testmenu.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testmenu.Controller;
import com.example.testmenu.FragmentController;
import com.example.testmenu.ImportRecipe;
import com.example.testmenu.Ingredient;
import com.example.testmenu.ListProduct;
import com.example.testmenu.MainActivity;
import com.example.testmenu.Product;
import com.example.testmenu.R;
import com.example.testmenu.adapters.RecycleViewProductAdapter;
import com.example.testmenu.dialogs.DialogRecettes;
import com.example.testmenu.dialogs.DialogSuggestedProduct;

import java.util.ArrayList;

public class BuildingListFragment extends Fragment implements View.OnClickListener, DialogSuggestedProduct.OnInputListener {

    private CardView addProduct;
    private ArrayList<Product> displayedListOfProducts = new ArrayList<Product>();
    private CardView addTheList;
    private EditText nameOfTheList;
    private ImageView returnTodisplayList;
    private int marketId;
    private CardView importRecipeBtn;
    private TextView totalPriceText;
    private String listeNameTexte;
    private double totalPrice = 0;
    public BuildingListFragment(int marketId) {
        super();
        this.marketId = marketId;
    }


    public BuildingListFragment(ArrayList<Product> displayedListOfProducts, String listeNameTexte, int marketId ) {
        super();
        this.displayedListOfProducts = displayedListOfProducts;
        this.listeNameTexte = listeNameTexte;
        this.marketId = marketId;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.e("TAG", ""+marketId);

        View v = inflater.inflate(R.layout.fragment_building_list, container, false);
        totalPriceText = (TextView)v.findViewById(R.id.totalPriceText);
        Log.e("zjzjzjzjzj" , ""+displayedListOfProducts.size());

        if(totalPriceText != null) {
            for (int i = 0; i < displayedListOfProducts.size(); i++) {
                totalPrice += displayedListOfProducts.get(i).getPrice();
            }
            String value = String.valueOf(totalPrice / 100);
            totalPriceText.setText(value + " €");
        }

        //FragmentController.swapFragment(new ListOfProductsFragment(displayedListOfProducts),R.id.listOfProductsContainer,getContext());

        LinearLayoutManager layoutManager =  new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.listViewOfProducts);
        recyclerView.setLayoutManager(layoutManager);
        RecycleViewProductAdapter adapter = new RecycleViewProductAdapter(displayedListOfProducts, getContext());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecycleViewProductAdapter.OnItemClickListener() {


            @Override
            public void onDeleteClick(int position) {
              //  Log.e("jeuuu", "VLIIIIIVKKKKKKK");
                totalPrice = totalPrice - displayedListOfProducts.get(position).getPrice();
                String value = String.valueOf(totalPrice / 100);
                totalPriceText.setText(value + " €");

                displayedListOfProducts.remove(position);
               /* RecycleViewProductAdapter adapter = new RecycleViewProductAdapter(displayedListOfProducts, getContext());
                recyclerView.setAdapter(adapter);*/
                adapter.notifyItemRemoved(position);


            }

            @Override
            public void onItemClick(int position) {
                Log.e("Clicckk From Displayed ", " "+position);
                displayedListOfProducts.get(position).displayInfo( getContext(), R.id.container);
            }
        });

        nameOfTheList = v.findViewById(R.id.editNameListe);
        nameOfTheList.setText(listeNameTexte);

        returnTodisplayList = (ImageView)v.findViewById(R.id.returnToDisplayList);
        returnTodisplayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new ListFragment(), getContext());

            }
        });

        addTheList = (CardView)v.findViewById(R.id.addTheListCardView);
        addTheList.setOnClickListener(this::onClick);


        addProduct = (CardView) (v.findViewById(R.id.cardViewAddProductButton));
        addProduct.setOnClickListener(this::onClick);


        ArrayList<Product> products = Controller.getAllProducts(marketId);

        /*ArrayList<Ingredient> ingredients = Controller.getUserRecettes(MainActivity.user.getId()).get(2).getListOfIngredients();


        ArrayList<String> ingredientsName= new ArrayList<>();

        for(int i=0; i< ingredients.size(); i++)
            ingredientsName.add(ingredients.get(i).getName());

        ImportRecipe importRecipe = new ImportRecipe(ingredientsName, products);

        ArrayList<ArrayList<Product>> arrayProductImpoerted = new ArrayList<>();
        for(int i=0; i< ingredients.size(); i++) {
            arrayProductImpoerted.add(importRecipe.getImportProduct(i));
        }

        for(int i=0; i<arrayProductImpoerted.size(); i++) {
            for(int j=0; j<arrayProductImpoerted.get(i).size(); j++) {
                Log.e("Products for ingredient " + ingredients.get(i).getName(), arrayProductImpoerted.get(i).get(j).getName());
                Log.e("Products for ingredient " + ingredients.get(i).getName(), "eee");
            }

        }*/

        importRecipeBtn = (CardView)v.findViewById(R.id.cardViewImportRecipe);
        importRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DialogRecettes(marketId, displayedListOfProducts, nameOfTheList.getText().toString()).show(getParentFragmentManager(),"Dialog Recette !");

            }
        });

        return v;
    }


    @Override
    public void onClick(View v) {
        Fragment fragment;
        switch (v.getId()) {
            case R.id.cardViewAddProductButton:
                fragment = new AddProductFragment(marketId, nameOfTheList.getText().toString(), displayedListOfProducts, true);
                break;
            default:

                Controller.addNewListOfProducts(MainActivity.user.getId(), new ListProduct(nameOfTheList.getText().toString(), displayedListOfProducts, new ArrayList<Integer>(), marketId ));
                fragment = new ListFragment();
                if (v.getId() == R.id.addTheList) {
                    displayedListOfProducts.clear();
                    nameOfTheList.setText("");
                }
                break;
        }
        FragmentController.swapFragmentInMainContainer(fragment, getContext());
    }


    @Override
    public void onPause() {
        super.onPause();
        totalPrice  = 0;
    }


    @Override
    public void sendInput(ArrayList<ArrayList<Product>> input) {
        for(int i=0; i< input.size(); i++) {
            if(input.get(i)!= null) {
                for (int j = 0; j < input.get(i).size(); j++) {
                    displayedListOfProducts.add(input.get(i).get(j));
                }
            }
        }
    }
}