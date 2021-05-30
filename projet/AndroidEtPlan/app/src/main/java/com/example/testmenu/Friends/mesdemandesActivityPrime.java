package com.example.testmenu.Friends;

import android.os.Bundle;
import android.util.Log;
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
import com.example.testmenu.MainActivity;
import com.example.testmenu.ProfilActivity;
import com.example.testmenu.R;
import com.example.testmenu.User;
import com.example.testmenu.adapters.DemandeAdapter;

import java.util.ArrayList;


public class mesdemandesActivityPrime extends Fragment {
    private Button retour;
    private Button mesamis;
    private Button btnAccept;
    private Button btnRefuse;
    ArrayList<String> demandes = new ArrayList<>();
    ListView listdemandes;
    ArrayList<Integer> listofDemands = new ArrayList<>();
    private ListView listesamis;
    private ArrayList<Integer> listFriends = new ArrayList<>();
    ArrayList<String> listNameOfFriend=new ArrayList<String>();
    int id= MainActivity.user.getId();
    User user= MainActivity.user;
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
        ArrayList<Integer> listDemands= Controller.getDemandsOfUser(id);
        Log.e("TAG", "test" );

        for(int i=0;i<listDemands.size();i++){
            Log.e("TAG", ""+listDemands );
        }

        for ( Integer i : listDemands){
            demandes.add(i.toString()+": Demande de "+ Controller.getUser(i).getFirstname()+ Controller.getUser(i).getLastname());
        }
      /*  for (Integer i:listDemands){
            demandes.add(i+": Demande de "+user.getFirstname()+user.getLastname());
        }*/

    /*  ArrayList<Integer> listDemands=new ArrayList<Integer>();
       listDemands.add(2);
       listDemands.add(22);
       for(Integer e:listDemands){
           demandes.add(e+" : Demande de "+ Controller.getUser(e).getFirstname()+" "+Controller.getUser(e).getLastname());
       } */

        // listdemandes.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        DemandeAdapter arrayadapter=new DemandeAdapter(getContext(), demandes);
        listdemandes.setAdapter(arrayadapter);



        listdemandes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Integer idOfDemander=Integer.getInteger(((TextView)view).getText().toString().substring(0, demandes.indexOf(": Demande de")));
                if( listofDemands.contains(idOfDemander)){
                    listofDemands.remove(idOfDemander);
                }
                else{
                    listofDemands.add(idOfDemander);
                }*/
                if(arrayadapter.getAreChecked().get(position))
                    arrayadapter.setAreCheckedIndex(position, false);
                else
                    arrayadapter.setAreCheckedIndex(position, true);


            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Boolean> checked = arrayadapter.getAreChecked();

                for(int i = 0; i< demandes.size(); i++){
                    //
                    if(checked.get(i)) {
                        Controller.addFriend(MainActivity.user.getId(), listDemands.get(i));

                    }
                }
            }
        });
        btnRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Boolean> checked = arrayadapter.getAreChecked();

                for (int i = 0; i < demandes.size(); i++) {
                    //
                    if (checked.get(i))
                        Controller.refuseDemand(MainActivity.user.getId(), listDemands.get(i));
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

        listFriends= Controller.getUserFriends(MainActivity.user.getId());
        //for(Integer e: listFriends){
        for(int i=0;i<listFriends.size();i++)  {
            if(!listNameOfFriend.contains((Controller.getUser(listFriends.get(i)).getFirstname()+" "+ Controller.getUser(listFriends.get(i)).getLastname()))) {

                listNameOfFriend.add(Controller.getUser(listFriends.get(i)).getFirstname() + " " + Controller.getUser(listFriends.get(i)).getLastname());
            }
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