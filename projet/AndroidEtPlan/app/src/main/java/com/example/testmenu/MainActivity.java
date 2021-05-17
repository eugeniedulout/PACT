package com.example.testmenu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.testmenu.fragments.ListFragment;
import com.example.testmenu.fragments.OffresFragment;
import com.example.testmenu.fragments.PlanFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static User user;

    /*static Fragment listFragment = new ListFragment();
    static Fragment planFragment = new PlanFragment();
    static  Fragment offresFragment = new OffresFragment();
    static Fragment profilFragment = new ProfilFragment();

    final FragmentManager fm = getSupportFragmentManager();
    static Fragment active = listFragment;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("connectionState", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("userValue", null);

        Type type = new TypeToken<ArrayList<User>>() {}.getType();
        user = gson.fromJson(json, User.class);
        // in below line we are getting data from gson
        // and saving it to our array list
        //user = (User) getIntent().getSerializableExtra("userValue");
         Log.e("jejeje", ""+user);
        // checking below if the array list is empty or not
        if (user == null) {
            // if the array list is empty
            // creating a new array list.
            user = new User(9999, "error","error","error");
        }

        /*fm.beginTransaction().add(R.id.container, profilFragment, "4").hide(profilFragment).commit();
        fm.beginTransaction().add(R.id.container, offresFragment, "3").hide(offresFragment).commit();
        fm.beginTransaction().add(R.id.container, planFragment, "2").hide(planFragment).commit();
        fm.beginTransaction().add(R.id.container,listFragment, "1").commit();*/
        Log.e("heyyy","heyyy");

        getSupportActionBar().hide();


        BottomNavigationView bottomNav = findViewById(R.id.bottom_menu);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    new ListFragment()).commit();
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_liste:


                            selectedFragment = new ListFragment();
                            break;
                        case R.id.nav_plan:
                            selectedFragment = new PlanFragment();
                            break;
                        case R.id.nav_offres:
                            selectedFragment = new OffresFragment();
                            break;
                        case R.id.nav_profil:
                            selectedFragment = new ProfilActivity();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,
                            selectedFragment).commit();
                    return true;
                }
            };
    /*
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_liste:
                            fm.beginTransaction().hide(active).show(listFragment).commit();
                            active = listFragment;
                            return true;

                        case R.id.nav_plan:
                            Log.d("ddffd", "nav_plzn");
                            fm.beginTransaction().hide(active).commit();

                            fm.beginTransaction().show(planFragment).commit();
                            active = planFragment;
                            return true;

                        case R.id.nav_offres:
                            fm.beginTransaction().hide(active).show(offresFragment).commit();
                            active = offresFragment;
                            return true;
                        case R.id.nav_profil:
                            fm.beginTransaction().hide(active).show(profilFragment).commit();
                            active = profilFragment;
                            return true;
                    }
                    return false;
                }
            };*/

    public void setText(final TextView text, final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }



}