package com.example.testmenu.Friends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testmenu.Controller;
import com.example.testmenu.FragmentController;
import com.example.testmenu.ProfilActivity;
import com.example.testmenu.R;

import java.util.ArrayList;


public class mesdemandesActivityPrime extends Fragment {
    private Button retour;
    private Button mesamis;
    private Button btnAccept;
    private Button btnRefuse;
    ArrayList<String> demandes;
    ListView listdemandes;
    ArrayList<Integer> listofDemands;
    private ListView listesamis;
    private ArrayList<Integer> listFriends;
    ArrayList<String> listNameOfFriend=new ArrayList<String>();
    @Override
    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_mesdemandes_prime, container, false);

        this.listesamis= v.findViewById(R.id.listesamis);
        this.retour= v.findViewById(R.id.retour);
        this.mesamis= v.findViewById(R.id.mesamis);
        this.listdemandes=v.findViewById(R.id.listdemandes);
        this.btnAccept=v.findViewById(R.id.btnAccept);
        this.btnRefuse=v.findViewById(R.id.btnRefuse);
        ArrayList<Integer> listDemands= Controller.getDemandsOfUser(1);
        for ( Integer i : listDemands){
            demandes.add(i+": Demande de "+Controller.getUser(i).getFirstname()+Controller.getUser(i).getLastname());
        }
        listdemandes.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> arrayadapter=new ArrayAdapter<String>(getContext(), R.layout.rowlayout,R.id.txt_lan, demandes);
        listdemandes.setAdapter(arrayadapter);


        listdemandes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer idOfDemander=Integer.getInteger(((TextView)view).getText().toString().substring(0, demandes.indexOf(": Demande de")));
                if( listofDemands.contains(idOfDemander)){
                    listofDemands.remove(idOfDemander);
                }
                else{
                    listofDemands.add(idOfDemander);
                }

            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Integer id: listofDemands){
                    Controller.addFriend(1,id);
                }
            }
        });
        btnRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Integer id:listofDemands){
                    Controller.refuseDemand(1, id);
                }
            }
        });

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new ProfilActivity(), getContext());
            }
        });

        mesamis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new AddFriendActivity(), getContext());
            }
        });

        listFriends= Controller.getUserFriends(1);
        //for(Integer e: listFriends){
        for(int i=0;i<listFriends.size();i++)  {
            listNameOfFriend.add(Controller.getUser(listFriends.get(i)).getFirstname()+" "+Controller.getUser(listFriends.get(i)).getLastname());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext() , android.R.layout.simple_list_item_1, listNameOfFriend);

        listesamis.setAdapter(arrayAdapter);

        listesamis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), parent.getItemAtPosition(position)+" is selected", Toast.LENGTH_LONG).show();
                FragmentController.swapFragmentInMainContainer(new AmiActivity(listFriends.get(position)),getContext());

            }
        });

        return v;
    }




}