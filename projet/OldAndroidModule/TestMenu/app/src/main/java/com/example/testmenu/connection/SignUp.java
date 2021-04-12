package com.example.testmenu.connection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testmenu.MainActivity;
import com.example.testmenu.R;
import com.example.testmenu.User;
import com.google.gson.Gson;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView goConnect = (TextView)findViewById(R.id.goConnect);

        TextView signUp = (TextView)findViewById(R.id.signUp);

        EditText email = (EditText)findViewById(R.id.email) ;
        EditText nom = (EditText)findViewById(R.id.nomUser) ;
        EditText prenom = (EditText)findViewById(R.id.prenomUser) ;
        EditText password = (EditText)findViewById(R.id.password) ;


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               User user = null;
                if(user != null) {

                    SharedPreferences sharedPreferences = getSharedPreferences("connectionState", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putBoolean("isConnected", true);
                    Gson gson = new Gson();
                    String userJson = gson.toJson(user);
                    editor.putString("userValue", userJson);
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }


            }
        });
        goConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}