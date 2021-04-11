package zineb.epokamp.profilapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import zineb.epokamp.profilapp.sofienePart.ListFragment;
import zineb.epokamp.profilapp.sofienePart.OffresFragment;
import zineb.epokamp.profilapp.sofienePart.PlanFragment;

public class MainActivity extends AppCompatActivity {


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

        /*fm.beginTransaction().add(R.id.container, profilFragment, "4").hide(profilFragment).commit();
        fm.beginTransaction().add(R.id.container, offresFragment, "3").hide(offresFragment).commit();
        fm.beginTransaction().add(R.id.container, planFragment, "2").hide(planFragment).commit();
        fm.beginTransaction().add(R.id.container,listFragment, "1").commit();*/


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
                            selectedFragment = new MainActivity2();
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


}