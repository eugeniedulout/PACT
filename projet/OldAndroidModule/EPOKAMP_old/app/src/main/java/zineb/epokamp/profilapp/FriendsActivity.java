package zineb.epokamp.profilapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FriendsActivity extends AppCompatActivity {
    private Button retour;
    private Button ajoutami;
    private Button demandes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        this.retour=findViewById(R.id.retour);
        this.ajoutami=findViewById(R.id.ajoutami);
        this.demandes=findViewById(R.id.demandes);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(otherActivity);
                finish();
            }
        });
        ajoutami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(), MainActivity3.class);
                startActivity(otherActivity);
                finish();
            }
        });
        demandes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),mesdemandesActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });
    }
}