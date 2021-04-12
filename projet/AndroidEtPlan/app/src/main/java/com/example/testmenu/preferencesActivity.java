package com.example.testmenu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class preferencesActivity extends Fragment {

        private Button retour;
        private CheckBox bio;
        private CheckBox halal;
        private CheckBox végétarien;
        private Switch rBtnPP;
        private Switch rBtnM;
        private Switch rBtnPC;
        private Switch A;
        private Switch B;
        private Switch C;
        private Switch D;
        private Switch E;


        String Halal="Halal";
        String Bio="Bio";
        String Végétarien="Végétarien";
        String BudgetPP="BudgetPP";
        String BudgetPM="BudgetPM";
        String BudgetPC="BudgetPC";
        String QualitéA="QualitéA";
        String QualitéB="QualitéB";
        String QualitéC="QualitéC";
        String QualitéD="QualitéD";
        String QualitéE="QualitéE";



        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.activity_preferences, container, false);



            SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("SHARED_Prefs", Context.MODE_PRIVATE);
            String TestBio=sharedPreferences.getString(Bio,"False");
            String TestHalal=sharedPreferences.getString(Halal, "False");
            String TestVégétarien=sharedPreferences.getString(Végétarien, "False");
            String TestBudgetPP=sharedPreferences.getString(BudgetPP, null);
            String TestBudgetPM=sharedPreferences.getString(BudgetPM, null);
            String TestBudgetPC=sharedPreferences.getString(BudgetPC, null);
            String TestQualitéA=sharedPreferences.getString(QualitéA, null);
            String TestQualitéB=sharedPreferences.getString(QualitéB, null);
            String TestQualitéC=sharedPreferences.getString(QualitéC, null);
            String TestQualitéD=sharedPreferences.getString(QualitéD, null);
            String TestQualitéE=sharedPreferences.getString(QualitéE, null);
            this.retour= v.findViewById(R.id.retour);
            this.bio=v.findViewById(R.id.cbBio);
            this.halal=v.findViewById(R.id.cbHalal);
            this.végétarien=v.findViewById(R.id.cbVégétarien);
            this.A=v.findViewById(R.id.btnA);
            this.B=v.findViewById(R.id.btnB);
            this.C=v.findViewById(R.id.btnC);
            this.D=v.findViewById(R.id.btnD);
            this.E=v.findViewById(R.id.btnE);
            this.rBtnPP=v.findViewById(R.id.rBtnPP);
            this.rBtnM=v.findViewById(R.id.rBtnM);
            this.rBtnPC=v.findViewById(R.id.rBtnPC);
            retour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentController.swapFragmentInMainContainer(new ProfilActivity(), getContext());
                }
            });


            bio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveData();
                }
            });
            halal.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                   saveData();
                }
            });
            végétarien.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveData();
                }
            });
            rBtnPP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveData();
                }
            });
            rBtnM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveData();
                }
            });
            rBtnPC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveData();
                }
            });
            A.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveData();
                }
            });
            B.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveData();
                }
            });
            C.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveData();
                }
            });
            D.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveData();
                }
            });
            E.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveData();
                }
            });
            if(TestBio=="True"){
                bio.setChecked(true);
            }
            if(TestHalal=="True"){
                halal.setChecked(true);
            }
            if(TestVégétarien=="True"){
                végétarien.setChecked(true);
            }
            if(TestBudgetPP=="Petit prix"){
                rBtnPP.setChecked(true);
            }
            if(TestBudgetPC=="Plus cher"){
                rBtnPC.setChecked(true);
            }
            if(TestBudgetPM=="Prix moyen"){
                rBtnM.setChecked(true);
            }
            if(TestQualitéA=="A"){
                A.setChecked(true);
            }
            if(TestQualitéB=="B"){
                B.setChecked(true);
            }
            if(TestQualitéC=="C"){
                C.setChecked(true);
            }
            if(TestQualitéD=="D"){
                D.setChecked(true);
            }
            if(TestQualitéE=="E"){
                E.setChecked(true);
            }
            return v;
        }

        public void saveData() {
            SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("SHARED_Prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            //editor.putString(Bio, "False");
            //editor.putString(Halal, "False");
            //editor.putString(Végétarien, "False");
            //editor.putString(Qualité, "False");
            //editor.putString(Budget, "False");
            if (bio.isChecked()) {
                editor.putString(Bio, "True");
                editor.commit();
            }
            if (halal.isChecked()) {
                editor.putString(Halal, "True");
                editor.commit();
            }
            if (végétarien.isChecked()) {
                editor.putString(Végétarien, "True");
                editor.commit();
            }
            if (rBtnPP.isChecked()) {
                editor.putString(BudgetPP, "Petit prix");
                editor.commit();
            }
            if (rBtnPC.isChecked()) {
                editor.putString(BudgetPC, "Plus cher");
                editor.commit();
            }
            if (rBtnM.isChecked()) {
                editor.putString(BudgetPM, "Prix moyen");
                editor.commit();
            }
            if (A.isChecked()) {
                editor.putString(QualitéA, "A");
                editor.commit();
            }
            if (B.isChecked()) {
                editor.putString(QualitéB, "B");
                editor.commit();
            }
            if (C.isChecked()) {
                editor.putString(QualitéC, "C");
                editor.commit();
            }
            if (D.isChecked()) {
                editor.putString(QualitéD, "D");
                editor.commit();
            }
            if (E.isChecked()) {
                editor.putString(QualitéE, "E");
                editor.commit();
            }
            if (!bio.isChecked()) {
                editor.putString(Bio, "False");
                editor.commit();
            }
            if (!halal.isChecked()) {
                editor.putString(Halal, "False");
                editor.commit();
            }
            if (!végétarien.isChecked()) {
                editor.putString(Végétarien, "False");
                editor.commit();
            }
            if (!rBtnPP.isChecked()) {
                editor.putString(BudgetPP, null);
                editor.commit();
            }
            if (!rBtnPC.isChecked()) {
                editor.putString(BudgetPC, null);
                editor.commit();
            }
            if(!rBtnM.isChecked()) {
                editor.putString(BudgetPM, null);
                editor.commit();
            }

            if (!A.isChecked()) {
                editor.putString(QualitéA, null);
                editor.commit();
            }
            if (!B.isChecked()) {
                editor.putString(QualitéB, null);
                editor.commit();
            }
            if (!C.isChecked()) {
                editor.putString(QualitéC, null);
                editor.commit();
            }
            if (!D.isChecked()) {
                editor.putString(QualitéD, null);
                editor.commit();
            }
            if (!E.isChecked()) {
                editor.putString(QualitéE, null);
                editor.commit();
            }

        }




    }
