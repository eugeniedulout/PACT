package zineb.epokamp.profilapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mesdemandesActivity extends AppCompatActivity {
    private Button retour;
    private Button mesamis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesdemandes);

        this.retour=findViewById(R.id.retour);
        this.mesamis=findViewById(R.id.mesamis);

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });
        mesamis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(otherActivity);
                finish();

            }
        });
    }

}