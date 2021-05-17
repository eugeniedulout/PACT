package com.example.testmenu.plan_dynamique;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testmenu.ListProduct;
import com.example.testmenu.R;
import com.example.testmenu.adapters.RecycleViewConsigneRecetteAdapter;
import com.example.testmenu.algorithmie.point.Point;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {


    public static ArrayList<Point> pointsProduits = new ArrayList<>();
    public static ListProduct listeProduit;

    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        getSupportActionBar().hide();

        pointsProduits = (ArrayList<Point>) getIntent().getSerializableExtra("produits coordonnees");
        listeProduit = (ListProduct) getIntent().getSerializableExtra("produitsOject");

        ArrayList<String> productsName = new ArrayList<>();

        for(int i=0; i< listeProduit.getListOfProducts().size(); i++)
            productsName.add(listeProduit.getListOfProducts().get(i).getName());

        LinearLayoutManager layoutManager =  new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycleProduit);
        recyclerView.setLayoutManager(layoutManager);
        RecycleViewConsigneRecetteAdapter adapterConsigne = new RecycleViewConsigneRecetteAdapter(productsName, getApplicationContext());
        recyclerView.setAdapter(adapterConsigne);


        FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
        fr.replace(R.id.containerGL,new MainFragment2());
        fr.commit();
        Log.e("message", "hehuuuuu");


        /*voirListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragment(new DisplayedProductsFromAListFragment(listeProduit.getListOfProducts(), listeProduit.getListName()), R.id.containerGL, getApplicationContext());

            }
        });*/
        Button btnScanCode = (Button)findViewById(R.id.scanBttn);
        btnScanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanButton(v);
            }
        });

        textView = findViewById(R.id.codeBarText);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);


    }

    public void ScanButton(View view){
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult != null){
            if (intentResult.getContents() == null){
                textView.setText("Cancelled");
            }
            else {
                textView.setText(intentResult.getContents());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
        //placeHolder(i,j,k,Integer.getInteger(textView.getText().toString()));
    }

   /* private boolean detectOpenGLES30() {
        ActivityManager am =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x30000);
    }*/

    /*@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }*/



}
