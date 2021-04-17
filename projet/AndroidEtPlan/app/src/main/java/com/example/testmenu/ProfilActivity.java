package com.example.testmenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testmenu.Friends.FriendsActivity;
import com.example.testmenu.connection.Reception;

import static android.content.Context.MODE_PRIVATE;

public class ProfilActivity extends Fragment {
    private Button preferences;
    private Button friends;
    private Button parameters;
    private Button deconnexion;


    TextView txtProfil;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_profil, container, false);

        this.preferences = v.findViewById(R.id.preferences);
        this.friends = v.findViewById(R.id.friends);
        this.parameters = v.findViewById(R.id.parameters);
        this.txtProfil=v.findViewById(R.id.txtProfil);
        deconnexion = (Button)v.findViewById(R.id.parameters2);
        txtProfil.setText("Bonjour "+ MainActivity.user.getFirstname()+" "+ MainActivity.user.getLastname());
        preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new preferencesActivity(), getContext());

            }
        });

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new FriendsActivity(), getContext());


            }
        });

        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("connectionState", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putBoolean("isConnected", false);
                editor.apply();


                Intent intent = new Intent(getContext(), Reception.class);
                startActivity(intent);
            }
        });

        parameters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new SettingsActivity(), getContext());

            }
        });
        return v;
    }
}