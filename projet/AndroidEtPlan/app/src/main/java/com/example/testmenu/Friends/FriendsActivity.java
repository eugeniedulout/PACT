package com.example.testmenu.Friends;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testmenu.Controller;
import com.example.testmenu.FragmentController;
import com.example.testmenu.R;
import com.example.testmenu.fragments.ProfilFragment;

import java.util.ArrayList;



public class FriendsActivity extends Fragment {
    private Button retour;
    private Button ajoutami;
    private Button demandes;

    private ArrayList<Integer> listFriends;
    public String textName;
    ArrayList<String> listNameOfFriend=new ArrayList<String>();
    private ListView listeamis;
    @Override
    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View v = inflater.inflate(R.layout.activity_friends, container, false);

       // Récuperation de la bdd
       // ListView list = (ListView) v.findViewById(R.id.list_links);
        //list.setAdapter(new ArrayAdapter<String>(this, R.layout.list_result, friends));

        this.retour= v.findViewById(R.id.retour);
        this.ajoutami= v.findViewById(R.id.ajoutami);
        this.demandes= v.findViewById(R.id.demandes);
        this.listeamis= v.findViewById(R.id.listeamis);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new ProfilFragment(), getContext());
            }
        });
        ajoutami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new AddFriendActivity(), getContext());
            }
        });
        demandes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new mesdemandesActivityPrime(), getContext());
            }
        });




       listFriends=  Controller.getUserFriends(1);
       //for(Integer e: listFriends){
        for(int i=0;i<listFriends.size();i++)  {
            listNameOfFriend.add(Controller.getUser(listFriends.get(i)).getFirstname()+" "+Controller.getUser(listFriends.get(i)).getLastname());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext() , android.R.layout.simple_list_item_1, listNameOfFriend);

        listeamis.setAdapter(arrayAdapter);

        listeamis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), parent.getItemAtPosition(position)+" is selected", Toast.LENGTH_LONG).show();
                FragmentController.swapFragmentInMainContainer(new AmiActivity(listFriends.get(position)),getContext());

            }
        });


        return v;
    }
}