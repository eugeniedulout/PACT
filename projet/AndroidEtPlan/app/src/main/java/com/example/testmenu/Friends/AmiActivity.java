package com.example.testmenu.Friends;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testmenu.Controller;
import com.example.testmenu.FragmentController;
import com.example.testmenu.ListProduct;
import com.example.testmenu.MainActivity;
import com.example.testmenu.R;

import java.util.ArrayList;

public class AmiActivity extends Fragment {



    Integer id;
    ListView listes;
    TextView txtAmi;

    public AmiActivity(int id){
        super();
        this.id=id;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View v = inflater.inflate(R.layout.activity_ami, container, false);
        this.listes = v.findViewById(R.id.listes);
        this.txtAmi = v.findViewById(R.id.txtAmi);

       txtAmi.setText(Controller.getUser(id).getFirstname()+Controller.getUser(id).getLastname());
        ArrayList<String> sesListes=new ArrayList<String>();
        ArrayList<ListProduct> lists= Controller.getUserLists(MainActivity.user.getId());
        for(ListProduct l: lists){
            sesListes.add(l.getListName());
        }
       ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_selectable_list_item, sesListes);
        listes.setAdapter(arrayAdapter);
        listes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), parent.getItemAtPosition(position) + " is selected", Toast.LENGTH_LONG).show();
                FragmentController.swapFragmentInMainContainer(new ListAcivity(lists.get(position)), getContext());
            }
        });
        return v;
    }

}
