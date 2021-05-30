package com.example.testmenu.Friends;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;


public class AddFriendActivity extends Fragment {
    private Button retour;
    private Button mesdemandes;
    private ListView listesamis;
    private ArrayList<Integer> listFriends;
    ArrayList<String> listNameOfFriend=new ArrayList<String>();
    EditText newFriend;
    Button buttonValid;

    @Override
    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View v = inflater.inflate(R.layout.activity_add_friend, container, false);

        this.retour= v.findViewById(R.id.retour);
        this.mesdemandes= v.findViewById(R.id.mesdemandes);
        this.listesamis= v.findViewById(R.id.listesamis);
        this.buttonValid=v.findViewById(R.id.btnValider);
        this.newFriend=v.findViewById(R.id.editTextAddFriend);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new ProfilActivity(), getContext());
            }
        });

        mesdemandes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new mesdemandesActivityPrime(), getContext());
            }
        });
        buttonValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controller.sendDemand(MainActivity.user.getId(), Integer.getInteger(newFriend.getText().toString()));
            }
        });

        listFriends=  Controller.getUserFriends(MainActivity.user.getId());
        for(Integer e: listFriends){
            if(!listNameOfFriend.contains((Controller.getUser(e).getFirstname()+" "+ Controller.getUser(e).getLastname()))) {
                listNameOfFriend.add(Controller.getUser(e).getFirstname() + " " + Controller.getUser(e).getLastname());
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