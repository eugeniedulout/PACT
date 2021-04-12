package com.example.testmenu.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.testmenu.Controller;
import com.example.testmenu.FragmentController;
import com.example.testmenu.ListProduct;
import com.example.testmenu.MainActivity;
import com.example.testmenu.Product;
import com.example.testmenu.R;

import java.util.ArrayList;

public class BuildingListFragment extends Fragment implements View.OnClickListener{
    private Button testButton;
    private ImageView addProduct;
    private ArrayList<Product> displayedListOfProducts = new ArrayList<Product>();
    private ImageView addTheList;
    private EditText nameOfTheList;
    private ImageView returnTodisplayList;
    private int marketId;

   /* public BuildingListFragment(int marketId){
        this.marketId = marketId;
    }*/
    private static final BuildingListFragment buildingListFragment = new BuildingListFragment();

    private BuildingListFragment(){
        setArguments(new Bundle());

    }

    public static BuildingListFragment getInstance() {
        return buildingListFragment;
    }

    public void setDisplayedListOfProducts(ArrayList<Product> displayedListOfProducts) {
        this.displayedListOfProducts = displayedListOfProducts;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.e("TAG", ""+marketId);

        getParentFragmentManager().setFragmentResultListener("requestProductToAdd", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {

                Product productToAdd = (Product) bundle.getSerializable("productToAdd");
                if(productToAdd != null) displayedListOfProducts.add(productToAdd);
            }
        });

        View v = inflater.inflate(R.layout.fragment_building_list, container, false);

        FragmentController.swapFragment(new ListOfProductsFragment(displayedListOfProducts),R.id.listOfProductsContainer,getContext());

        nameOfTheList = v.findViewById(R.id.editNameListe);

        returnTodisplayList = (ImageView)v.findViewById(R.id.returnToDisplayList);
        returnTodisplayList.setOnClickListener(this::onClick);

        addTheList = (ImageView)v.findViewById(R.id.addTheList);
        addTheList.setOnClickListener(this::onClick);


        addProduct = (ImageView) (v.findViewById(R.id.addProductButton));
        addProduct.setOnClickListener(this::onClick);


        return v;
    }


    @Override
    public void onClick(View v) {
        Fragment fragment;
        switch (v.getId()) {
            case R.id.addProductButton:
                fragment = new AddProductFragment(marketId);
                break;
            default:

                // ##################################
                // #################################
                Controller.addNewListOfProducts(1, new ListProduct(nameOfTheList.getText().toString(), displayedListOfProducts, new ArrayList<Integer>(), 2 ));
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
        getArguments().putSerializable("displayedProducts", displayedListOfProducts);
    }


}