package com.example.testmenu.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testmenu.Controller;
import com.example.testmenu.FragmentController;
import com.example.testmenu.MainActivity;
import com.example.testmenu.Product;
import com.example.testmenu.R;
import com.example.testmenu.User;
import com.example.testmenu.adapters.RecycleViewProductAdapter;
import com.example.testmenu.dialogs.DialogDeleteList;
import com.example.testmenu.dialogs.DialogMarket;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class DisplayedProductsFromAListFragment extends Fragment {

    private ArrayList<Product> productsToDisplay = new ArrayList<>();
    private String nameOfTheListSelected;
    private TextView nameOfTheListSelectedTextView;
    private FloatingActionButton editListButton;
    private FloatingActionButton addProductFloatingActionButton;
    private FloatingActionButton shareListToFriendActionButton;
    private  FloatingActionButton deleteTheListButton;
    private int marketId;
    private RecycleViewProductAdapter adapter;
    private boolean canEditList = true;
    public DisplayedProductsFromAListFragment() {
            super();
    }
    public DisplayedProductsFromAListFragment(boolean canEditList) {
        super();
        this.canEditList = canEditList;
    }

    private  int compteur;
    public DisplayedProductsFromAListFragment(ArrayList<Product> productsToDisplay, String nameOfTheListSelected, int marketId){
        super();
        this.productsToDisplay = productsToDisplay;
        this.nameOfTheListSelected= nameOfTheListSelected;
        this.marketId = marketId;
        this.compteur = 0;
    }
    public DisplayedProductsFromAListFragment(ArrayList<Product> productsToDisplay, String nameOfTheListSelected, int marketId,boolean canEditList ){
        super();
        this.productsToDisplay = productsToDisplay;
        this.nameOfTheListSelected= nameOfTheListSelected;
        this.marketId = marketId;
        this.compteur = 0;
        this.canEditList = canEditList;

    }
    public DisplayedProductsFromAListFragment(ArrayList<Product> productsToDisplay, String nameOfTheListSelected, int marketId, int compteur){
        super();
        this.productsToDisplay = productsToDisplay;
        this.nameOfTheListSelected= nameOfTheListSelected;
        this.marketId = marketId;
        this.compteur = compteur;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_displayed_products_from_a_list, container, false);
        nameOfTheListSelectedTextView = (TextView)v.findViewById(R.id.nameOfTheListSelected);
        nameOfTheListSelectedTextView.setText(nameOfTheListSelected);

        Log.e("compteur" ,""+compteur);

        LinearLayoutManager layoutManager =  new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.productListView);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecycleViewProductAdapter(productsToDisplay,  getContext());
        recyclerView.setAdapter(adapter);





        adapter.setOnItemClickListener(new RecycleViewProductAdapter.OnItemClickListener() {


            @Override
            public void onDeleteClick(int position) {
                productsToDisplay.remove(position);
                adapter.notifyItemRemoved(position);

            }

            @Override
            public void onItemClick(int position) {
                productsToDisplay.get(position).displayInfo( getContext(), R.id.container);
            }
        });


         /*
         LinearLayoutManager layoutManager =  new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.listViewOfProducts);
        recyclerView.setLayoutManager(layoutManager);
        RecycleViewProductAdapter adapter = new RecycleViewProductAdapter(displayedListOfProducts, getContext());
        recyclerView.setAdapter(adapter);
        /*

        ProductAdapter adapter = new ProductAdapter(getContext(), displayedProducts);
        listViewOfProducts.setAdapter(adapter);

        listViewOfProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayedProducts.get(position).displayInfo(getContext());
            }
        });*/


        deleteTheListButton =(FloatingActionButton)v.findViewById(R.id.deleteTheListButton) ;
        deleteTheListButton.setVisibility(View.INVISIBLE);

        deleteTheListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogDeleteList().show(getParentFragmentManager(),"Dialog delete list !");

            }
        });



        addProductFloatingActionButton = (FloatingActionButton)v.findViewById(R.id.addProductFloatingActionButton);
        shareListToFriendActionButton = (FloatingActionButton)v.findViewById(R.id.shareListToFriendActionButton);

        shareListToFriendActionButton.setVisibility(View.INVISIBLE);

        addProductFloatingActionButton.setVisibility(View.INVISIBLE);

        addProductFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new AddProductFragment(marketId, nameOfTheListSelected, productsToDisplay, false, compteur), getContext());
            }
        });

        editListButton = (FloatingActionButton)v.findViewById(R.id.editListButton);
        if(!canEditList)
            editListButton.setVisibility(View.INVISIBLE);
        if(compteur % 2 != 0) {

            adapter.hideDeleteButton(false);
            addProductFloatingActionButton.setVisibility(View.VISIBLE);
            shareListToFriendActionButton.setVisibility(View.INVISIBLE);
            editListButton.setImageResource(R.drawable.ic_baseline_check_24);
        }
        else
        {

            adapter.hideDeleteButton(true);

            addProductFloatingActionButton.setVisibility(View.INVISIBLE);
           // shareListToFriendActionButton.setVisibility(View.VISIBLE);

            editListButton.setImageResource(R.drawable.ic_baseline_edit_24);

        }

        editListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(compteur % 2 == 0) {
                    adapter.hideDeleteButton(false);

                    deleteTheListButton.setVisibility(View.VISIBLE);

                    addProductFloatingActionButton.setVisibility(View.VISIBLE);
                    shareListToFriendActionButton.setVisibility(View.INVISIBLE);

                    editListButton.setImageResource(R.drawable.ic_baseline_check_24);
                }
                else
                {
                    adapter.hideDeleteButton(true);
                    deleteTheListButton.setVisibility(View.INVISIBLE);

                    addProductFloatingActionButton.setVisibility(View.INVISIBLE);
                    //shareListToFriendActionButton.setVisibility(View.VISIBLE);

                    editListButton.setImageResource(R.drawable.ic_baseline_edit_24);

                }
                compteur++;

            }
        });

        shareListToFriendActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> friendsId = Controller.getUserFriends(MainActivity.user.getId());
                for (Integer i : friendsId) {
                    Log.e("ID friend", i.toString());
                    User user = Controller.getUser(i);
                    if(user!= null)
                        Log.e("Name : ", user.toString());
                }
            }
        });
        return v;
    }

}
