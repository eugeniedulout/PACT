package com.example.testmenu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Test extends Fragment {
    private Button btnScanCode;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_test, container, false);
        this.btnScanCode = v.findViewById(R.id.btnScanCode);

        btnScanCode.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null){
            if (intentResult.getContents() == null){
                Log.e("eoeo","cancelled");

            }
            else {
                Log.e("code bar", intentResult.getContents());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    int i=0;//position à récupérer
    int j=0;//position à récupérer
    int k=0;//position à récupérer
    public void placeHolder(int i ,int j, int k, int resultCode ){
        //Envoi de la position d'un produit en scannant son code
    }

}