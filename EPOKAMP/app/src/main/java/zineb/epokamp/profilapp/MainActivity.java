package zineb.epokamp.profilapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button preferences;
    private Button friends;
    private Button parameters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.preferences=findViewById(R.id.preferences);
        this.friends=findViewById(R.id.friends);
        this.parameters=findViewById(R.id.parameters);
        preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),preferencesActivity.class);
                startActivity(otherActivity);
                finish();

            }
        });
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),FriendsActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });
        parameters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity=new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });
    }
}