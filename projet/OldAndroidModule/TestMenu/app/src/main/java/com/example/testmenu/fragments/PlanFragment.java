package com.example.testmenu.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testmenu.R;
import com.example.testmenu.algorithmie.PlusCourtChemin;
import com.example.testmenu.algorithmie.point.Point;
import com.example.testmenu.algorithmie.point.ProductPoint;

import java.util.ArrayList;

public class PlanFragment extends Fragment {
    private AutoCompleteTextView searchMarketName;
    private AutoCompleteTextView searchListName;
    private Button goButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_plan, container, false);

        searchMarketName = (AutoCompleteTextView)v.findViewById(R.id.serachMarketName);
        searchListName = (AutoCompleteTextView)v.findViewById(R.id.serachListName);
        TextView tv = (TextView)v.findViewById(R.id.planText);

        ArrayList<String> marketsName = builderMarketsName();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, marketsName);
        searchMarketName.setAdapter(adapter);


        goButton = (Button)v.findViewById(R.id.goButton);
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
                Intent intent = new Intent(getContext(), TestPlan.class);
                Bundle b = new Bundle();
                ArrayList<Point> pointsProduits = new ArrayList<>();
                pointsProduits.add(new ProductPoint(2,3));
                pointsProduits.add(new ProductPoint(4,11));
                pointsProduits.add(new ProductPoint(5,12));
                pointsProduits.add(new ProductPoint(8,11));
                pointsProduits.add(new ProductPoint(7,11));
                pointsProduits.add(new ProductPoint(8,2));
                b.putSerializable("produits coordonnees",pointsProduits);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        return v;
    }


    private ArrayList<String> builderMarketsName (){
        ArrayList<String> marketsName = new ArrayList<String>();
        marketsName.add("Auchan");
        marketsName.add("Auphan");

        marketsName.add("Auchann");
        marketsName.add("Auchannn");
        marketsName.add("Auchannnn");
        marketsName.add("Auchannnnn");
        marketsName.add("Auchannnnnn");
        marketsName.add("Auchannnnnnn");
        marketsName.add("Auchannnnnnnnn");
        marketsName.add("Auchannnnnnnnnn");
        marketsName.add("Auchannnnnnnnnnnnnn");
        marketsName.add("Auchannnnnnnnnnnnnnnnnnnn");
        marketsName.add("Auchannnnnnnnnnnnnnnnnnnnnnn");

        marketsName.add("Aachann");
        marketsName.add("Lidl");
        marketsName.add("Monoprix");
        marketsName.add("Franprix");
        marketsName.add("Carrefour");
        return marketsName;

    }
}