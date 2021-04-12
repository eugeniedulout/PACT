package com.example.testmenu;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;



public class FriendsActivity extends Fragment {
    private Button retour;
    private Button ajoutami;
    private Button demandes;

    private ArrayList<String> listFriends;
    ArrayList<String> listName=new ArrayList<String>();
    private ListView listeamis;
    @Override
    @Nullable
   /* protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        this.retour=findViewById(R.id.retour);
        this.ajoutami=findViewById(R.id.ajoutami);
        this.demandes=findViewById(R.id.demandes);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });
        ajoutami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(otherActivity);
                finish();
            }
        });
        demandes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),mesdemandesActivityPrime.class);
                startActivity(otherActivity);
                finish();
            }
        });
    }
    */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View v = inflater.inflate(R.layout.activity_friends, container, false);

        // RÃ©cuperation de la bdd
        // ListView list = (ListView) v.findViewById(R.id.list_links);
        //list.setAdapter(new ArrayAdapter<String>(this, R.layout.list_result, friends));

        this.retour= v.findViewById(R.id.retour);
        this.ajoutami= v.findViewById(R.id.ajoutami);
        this.demandes= v.findViewById(R.id.demandes);
        this.listeamis= v.findViewById(R.id.listeamis);
        /*
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new MainActivity(), getContext());
            }
        });
        ajoutami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new MainActivity2(), getContext());
            }
        });
        demandes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new mesdemandesActivityPrime(), getContext());
            }
        });*/

        // listeamis.setOnItemClickListener((AdapterView.OnItemClickListener) (parent, view, position, id) -> {
        //});
        //ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, Controller.listFriends);//
        //listeamis.setAdapter(arrayAdapter);
       /* listeamis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), parent.getItemAtPosition(position)+" is selected", Toast.LENGTH_LONG).show();
                FragmentController.swapFragmentInMainContainer(new AmiActivity(),getContext());
            }
        });*/

        ArrayList<ListProduct> listProd=  Controller.getUserLists(1);
        for(ListProduct e: listProd){
            listName.add(e.getListName());
        }
       /* listName.add("liste de x");
        listName.add("liste de y");
        listName.add("liste de z");*/
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext() , android.R.layout.simple_list_item_1, listName);

        listeamis.setAdapter(arrayAdapter);
        return v;
    }
}