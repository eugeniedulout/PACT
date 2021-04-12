package com.example.newwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    private TextView ee;
    private CheckBox checkPomme;
    private CheckBox checkNutella;
    private CheckBox checkBeurre;

    private Button button2;
    public final static String pommeTag = "POMME";
    public final static String nutellaTag = "NUTELLA";
    public final static String beurreTag = "BEURRE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);
        checkPomme = (CheckBox)findViewById(R.id.checkPomme);
        checkNutella = (CheckBox)findViewById(R.id.checkNutella);
        checkBeurre = (CheckBox)findViewById(R.id.checkBeurre);

        button2 = (Button)findViewById(R.id.revenir);
        Intent premier = new Intent(SecondActivity.this, MainActivity.class);
        Intent i = getIntent();

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                premier.putExtra(pommeTag, checkPomme.isChecked() ? true : false );
                premier.putExtra(nutellaTag, checkNutella.isChecked() ? true : false );
                premier.putExtra(beurreTag, checkBeurre.isChecked() ? true : false );

                startActivity(premier);
            }
        });

    }
}