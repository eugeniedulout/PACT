package zineb.epokamp.profilapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity3 extends AppCompatActivity {
    private Button retour;
    private Button mesdemandes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        this.retour=findViewById(R.id.retour);
        this.mesdemandes=findViewById(R.id.mesdemandes);

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(otherActivity);
                finish();
            }
        });
        mesdemandes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),mesdemandesActivity.class);
                startActivity(otherActivity);
                finish();

            }
        });
    }

}