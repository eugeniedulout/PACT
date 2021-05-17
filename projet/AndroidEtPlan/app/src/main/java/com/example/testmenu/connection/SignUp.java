package com.example.testmenu.connection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testmenu.Controller;
import com.example.testmenu.MainActivity;
import com.example.testmenu.R;
import com.example.testmenu.User;
import com.google.gson.Gson;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView goConnect = (TextView)findViewById(R.id.goConnect);

        TextView signUp = (TextView)findViewById(R.id.signUp);

        TextView emailAlreadyExistTextView = (TextView)findViewById(R.id.emailAlreadyExistTextView);
        emailAlreadyExistTextView.setVisibility(View.INVISIBLE);

        EditText email = (EditText)findViewById(R.id.email) ;
        EditText nom = (EditText)findViewById(R.id.nomUser) ;
        EditText prenom = (EditText)findViewById(R.id.prenomUser) ;
        EditText password = (EditText)findViewById(R.id.password) ;

        String firstName = prenom.getText().toString();
        String  lastName = nom.getText().toString();
        String mail  = email.getText().toString();
        String passw = password.getText().toString();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               User user = Controller.signUp(firstName, lastName, mail, passw);
               Log.e("user",""+user);
                if(user != null) {

                    SharedPreferences sharedPreferences = getSharedPreferences("connectionState", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Log.e("user", ""+user);

                    editor.putBoolean("isConnected", true);
                    Gson gson = new Gson();
                    String userJson = gson.toJson(user);
                    editor.putString("userValue", userJson);
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    emailAlreadyExistTextView.setVisibility(View.VISIBLE);

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