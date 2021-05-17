package com.example.testmenu.connection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testmenu.MainActivity;
import com.example.testmenu.R;

public class Reception extends AppCompatActivity {
    private TextView connectButton;
    private TextView signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception);

        getSupportActionBar().hide();

        SharedPreferences sharedPreferences = getSharedPreferences("connectionState", MODE_PRIVATE);

        boolean isConnected = sharedPreferences.getBoolean("isConnected", false);

        if(isConnected) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        signUpButton = (TextView)findViewById(R.id.buttonSignUp);
        signUpButton.setOnClickListener(accesOthersActivities);

        connectButton = (TextView)findViewById(R.id.buttonConnect);
        connectButton.setOnClickListener(accesOthersActivities);


    }

    private View.OnClickListener accesOthersActivities = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.buttonConnect:
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    break;
                default:
                    Intent intent2 = new Intent(getApplicationContext(), SignUp.class);
                    startActivity(intent2);
                    break;
            }
        }
    };

}