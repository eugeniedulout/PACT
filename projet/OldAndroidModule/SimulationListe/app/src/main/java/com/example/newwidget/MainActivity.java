package com.example.newwidget;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView liste = null;
    private Button recherchez;
    ArrayList<Product> products = new ArrayList<>();

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        liste = (ListView) findViewById(R.id.spinner);
        recherchez = (Button)findViewById(R.id.button2);



        Intent mainAc = new Intent(MainActivity.this, SecondActivity.class);
        Intent i = getIntent();
        boolean pommeState = i.getBooleanExtra(SecondActivity.pommeTag, false);
        boolean nutellaState = i.getBooleanExtra(SecondActivity.nutellaTag, false);
        boolean beurreState = i.getBooleanExtra(SecondActivity.beurreTag, false);
        if(pommeState)
             products.add(new Product("Pomme", "pomme", 1));
        if(nutellaState)
            products.add(new Product("Nutella", "nutella",2));
        if(beurreState)
            products.add(new Product("Beurre", "beurre", 0.5));

        ProductAdapter adapter = new ProductAdapter(this, products);
        liste.setAdapter(adapter);


        recherchez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent secondeActivite = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(secondeActivite);
            }
        });
    }
}