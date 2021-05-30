package com.example.testmenu.Account;

import android.os.Bundle;
import android.util.Log;
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
import com.example.testmenu.MainActivity;
import com.example.testmenu.R;

public class ModifyMdpAccount extends Fragment {
    TextView nom;
    TextView prénom;
    TextView identifiant;
    TextView email;
    EditText newMdp;

    Button btnEmail;

    Button btnValid;



    @Override
    @Nullable


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_modify_mdp_account, container, false);

        this.btnEmail=v.findViewById(R.id.btnAdressEAccount);
        this.btnValid=v.findViewById(R.id.btnValid2);

        this.nom=v.findViewById(R.id.txtNom);
        this.prénom=v.findViewById(R.id.txtPrenom);
        this.email=v.findViewById(R.id.txtEmail);
        this.newMdp=v.findViewById(R.id.editTextTextPassword);

        nom.setText(MainActivity.user.getLastname());
        prénom.setText(MainActivity.user.getFirstname());
        email.setText(MainActivity.user.getMail());



        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new ModifyAdressEAccount(), getContext());
            }
        });


        btnValid.setOnClickListener(new View.OnClickListener() {
            @Override
          public void onClick(View v) {
                //   public void change(View v)
                Log.e("MDP", newMdp.getText().toString());
                Controller.updatePassword(MainActivity.user.getId(),newMdp.getText().toString());

                FragmentController.swapFragmentInMainContainer(new AccountActivity(), getContext());
                 // Toast.makeText(getContext(),"Your password has been changed", Toast.LENGTH_SHORT).show();
                   /* FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        user.updatePassword(editMdp.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Your password has been changed", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), "failed operation", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }*/
            }
        });




        return v;
    }
}