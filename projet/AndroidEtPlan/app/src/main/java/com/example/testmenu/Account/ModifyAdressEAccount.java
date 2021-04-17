package com.example.testmenu.Account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testmenu.Controller;
import com.example.testmenu.FragmentController;
import com.example.testmenu.R;

public class ModifyAdressEAccount extends Fragment {

    TextView nom;
    TextView prénom;
    TextView identifiant;
    EditText newEmail;
    String newMail;

    Button btnMdp;

    Button btnValid;



    @Override
    @Nullable
  /*  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_adress_e_account);
    }*/

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_modify_adress_e_account, container, false);
        this.identifiant=v.findViewById(R.id.txtIdentifiant);
        this.nom=v.findViewById(R.id.txtNom);
        this.prénom=v.findViewById(R.id.txtPrenom);
        this.btnValid=v.findViewById(R.id.btnValid);
        this.btnMdp=v.findViewById(R.id.btnMdpAccount);

        newMail=newEmail.getText().toString();

        btnMdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new ModifyMdpAccount(), getContext());
            }
        });


        btnValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controller.setEmail(1, newMail);
                Toast.makeText(getContext(),"E-mail updated",Toast.LENGTH_LONG).show();

            }
        });


        return v;
    }

}