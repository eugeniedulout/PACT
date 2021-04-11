package com.example.testmenu.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.testmenu.MainActivity;
import com.example.testmenu.R;
import com.example.testmenu.algorithmie.PlusCourtChemin;
import com.example.testmenu.algorithmie.point.Point;
import com.example.testmenu.algorithmie.point.ProductPoint;

import java.util.ArrayList;

public class TestPlan extends AppCompatActivity {

    private ArrayList<Point> coordonnesPlusCourtChemin;
    private ArrayList<Point> pointsProduits;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_plan);
        pointsProduits = (ArrayList<Point>) getIntent().getSerializableExtra("produits coordonnees");
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        calculPlusCourtChemin calculPlusCourtChemin = new calculPlusCourtChemin();
        calculPlusCourtChemin.execute();

    }
    private class calculPlusCourtChemin extends AsyncTask<Integer,Integer,String> {

        @Override
        protected String doInBackground(Integer... integers) {

            coordonnesPlusCourtChemin = PlusCourtChemin.getCoordonnesChemin(pointsProduits);


            return "finished";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}