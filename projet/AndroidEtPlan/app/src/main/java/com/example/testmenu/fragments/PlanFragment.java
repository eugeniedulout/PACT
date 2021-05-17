package com.example.testmenu.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.testmenu.Controller;
import com.example.testmenu.ListProduct;
import com.example.testmenu.MainActivity;
import com.example.testmenu.Market;
import com.example.testmenu.Product;
import com.example.testmenu.R;
import com.example.testmenu.algorithmie.point.Point;
import com.example.testmenu.algorithmie.point.ProductPoint;
import com.example.testmenu.plan_dynamique.MainActivity2;

import java.util.ArrayList;

public class PlanFragment extends Fragment {
    private AutoCompleteTextView searchMarketName;
    private AutoCompleteTextView searchListName;
    private Button goButton;
    private Button scanButton;
    private ArrayList<Point> pointsProduits = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_plan, container, false);
        ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

        searchMarketName = (AutoCompleteTextView)v.findViewById(R.id.serachMarketName);
        searchListName = (AutoCompleteTextView)v.findViewById(R.id.serachListName);

        ArrayList<String> marketsName =new ArrayList<String>();
        ArrayList<String> listnames =new ArrayList<String>();


        TextView tv = (TextView)v.findViewById(R.id.planText);


        //################################################################
        //################################################################
        ArrayList<Market> marketArrayList =  Controller.getAllMarkets();
        for(int i=0; i< marketArrayList.size(); i++){
             marketsName.add(marketArrayList.get(i).getMarketName());
        }
        // ###############################################################
        // ###############################################################

        //################################################################
        //################################################################
        ArrayList<ListProduct> listProductsUser =  Controller.getUserLists(MainActivity.user.getId());
        for(int i=0; i< listProductsUser.size(); i++){
            listnames.add(listProductsUser.get(i).getListName());
        }

        // ###############################################################
        // ###############################################################

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, marketsName);
        searchMarketName.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listnames);
        searchListName.setAdapter(adapter2);

        goButton = (Button)v.findViewById(R.id.goButton);

        // ##########################################
        // ########## Requete#####################


        /*
        searchMarketName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String result = searchMarketName.getText().toString();
                if( result != null)
                    tv.setText(result);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });*/

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity2.class);
                Bundle b = new Bundle();
                // ################################################
                // ################################################
                ListProduct listSelected = new ListProduct();
                for(ListProduct listProduct : listProductsUser ) {
                    if(listProduct.getListName().equals(searchListName.getText().toString()))
                        listSelected = listProduct;
                }
                for(Product product : listSelected.getListOfProducts()) {
                    pointsProduits.add(new ProductPoint(product.getX(), product.getY()));
                }

                for(Point productPoint : pointsProduits)
                    Log.e("hheuu", productPoint.getLabel());

                // ################################################
                // ################################################

                /*pointsProduits.add(new ProductPoint(2,3));
                pointsProduits.add(new ProductPoint(4,10));
                pointsProduits.add(new ProductPoint(7,12));
                pointsProduits.add(new ProductPoint(8,11));
                pointsProduits.add(new ProductPoint(7,2));*/
                //pointsProduits.add(new ProductPoint(8,2));
                b.putSerializable("produits coordonnees",pointsProduits);
                b.putSerializable("produitsOject" , listSelected);
                intent.putExtras(b);
                startActivity(intent);
            }
        });



        return v;
    }
}