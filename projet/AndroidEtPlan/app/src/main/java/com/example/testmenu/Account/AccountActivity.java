package com.example.testmenu.Account;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testmenu.Controller;
import com.example.testmenu.FragmentController;
import com.example.testmenu.R;

public class AccountActivity extends Fragment {

    TextView nom;
    TextView prénom;
    TextView identifiant;
    TextView email;

    Button btnAdressEAccount;

    Button btnMdpAccount;

    @Override
    @Nullable
    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }*/
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View v = inflater.inflate(R.layout.activity_account, container, false);


        this.identifiant=v.findViewById(R.id.txtIdentifiant);
        this.nom=v.findViewById(R.id.txtNom);
        this.prénom=v.findViewById(R.id.txtPrénom);
        this.email=v.findViewById(R.id.txtEmail);

        this.btnAdressEAccount= v.findViewById(R.id.btnAdressEAccount);
        this.btnMdpAccount= v.findViewById(R.id.btnMdpAccount);
        nom.setText((Controller.getUser(1).getLastname()));
        prénom.setText((Controller.getUser(1).getFirstname()));
        identifiant.setText((Controller.getUser(1).getId()));
        btnAdressEAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new ModifyAdressEAccount(), getContext());
            }
        });


        btnMdpAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new ModifyMdpAccount(), getContext());
            }
        });

        return v;
    }
}