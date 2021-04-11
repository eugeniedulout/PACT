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

import com.example.testmenu.R;
import com.example.testmenu.ScanActivity;
import com.example.testmenu.algorithmie.PlusCourtChemin;
import com.example.testmenu.algorithmie.point.Point;
import com.example.testmenu.algorithmie.point.ProductPoint;
import com.example.testmenu.plan_dynamique.MainActivity2;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class PlanFragment extends Fragment {
    private AutoCompleteTextView searchMarketName;
    private AutoCompleteTextView searchListName;
    private Button goButton;
    private Button scanButton;
    private TextView resultScan;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_plan, container, false);
        ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

        searchMarketName = (AutoCompleteTextView)v.findViewById(R.id.serachMarketName);
        searchListName = (AutoCompleteTextView)v.findViewById(R.id.serachListName);
        TextView tv = (TextView)v.findViewById(R.id.planText);

        ArrayList<String> marketsName = builderMarketsName();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, marketsName);
        searchMarketName.setAdapter(adapter);

        goButton = (Button)v.findViewById(R.id.goButton);
        scanButton = (Button)v.findViewById(R.id.scanButton);

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
                ArrayList<Point> pointsProduits = new ArrayList<>();

                pointsProduits.add(new ProductPoint(2,3));
                pointsProduits.add(new ProductPoint(4,10));
                pointsProduits.add(new ProductPoint(7,12));
                pointsProduits.add(new ProductPoint(8,11));
                pointsProduits.add(new ProductPoint(7,2));
                //pointsProduits.add(new ProductPoint(8,2));
                b.putSerializable("produits coordonnees",pointsProduits);
                intent.putExtras(b);
                startActivity(intent);
            }
        });


        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanButton(v);
            }
        });
        return v;
    }

    public void ScanButton(View view){
        IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
        intentIntegrator.initiateScan();

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