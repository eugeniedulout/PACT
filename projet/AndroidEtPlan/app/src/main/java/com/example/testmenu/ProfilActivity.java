package com.example.testmenu;

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

public class ProfilActivity extends Fragment {
    private Button preferences;
    private Button friends;
    private Button parameters;

    TextView txtProfil;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_profil, container, false);

        this.preferences = v.findViewById(R.id.preferences);
        this.friends = v.findViewById(R.id.friends);
        this.parameters = v.findViewById(R.id.parameters);
        this.txtProfil=v.findViewById(R.id.txtProfil);

       //   txtProfil.setText("Bonjour "+ Controller.getUser(1).getLastname()+" "+ Controller.getUser(1).getFirstname());
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

        parameters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new SettingsActivity(), getContext());

            }
        });
        return v;
    }
}