package com.example.testmenu.Friends;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testmenu.ListProduct;
import com.example.testmenu.Product;
import com.example.testmenu.R;

import java.util.ArrayList;

public class ListAcivity extends Fragment {

    ListProduct l;
    public ListAcivity(ListProduct l){
        super();
        this.l=l;
    }

    ArrayList<String> listName=new ArrayList<String>();
    @Nullable
    ListView listViewdeproducts;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View v = inflater.inflate(R.layout.activity_list_acivity, container, false);

        this.listViewdeproducts = v.findViewById(R.id.listViewdeproducts);
        ArrayList<Product> listProd=  l.getListOfProducts();
        for(Product e: listProd){
            listName.add(e.getName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext() , android.R.layout.simple_selectable_list_item, listName);

        listViewdeproducts.setAdapter(arrayAdapter);

        return v;
    }

}