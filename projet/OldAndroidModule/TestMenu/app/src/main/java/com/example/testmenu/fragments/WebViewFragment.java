package com.example.testmenu.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.testmenu.FragmentController;
import com.example.testmenu.Ingredient;
import com.example.testmenu.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class WebViewFragment extends Fragment {
    private static final String PERSISTENT_VARIABLE_BUNDLE_KEY = "persistentVariable";
    private EditText persistentVariableEdit;

    private TextView recetteName;
    private String titleHtmlPage;
    private Button importButton;
    private ArrayList<Ingredient> listOfIngredients = new ArrayList<>();
    private FloatingActionButton addRecetteButton;
    private  ArrayList<String> consignes = new ArrayList<>();
    private String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, null);

        WebView w = (WebView)view.findViewById(R.id.webVIew);
        w.getSettings().setJavaScriptEnabled(true);
        w.getSettings().setBuiltInZoomControls(true);
        w.getSettings().setDisplayZoomControls(false);
        w.getSettings().setDomStorageEnabled(true);
        w.setWebViewClient(new WebViewClient());
        w.loadUrl("https://marmiton.org");

        w.getSettings().setBuiltInZoomControls(true);
        w.getSettings().setUseWideViewPort(true);
        w.getSettings().setLoadWithOverviewMode(true);

        Button importButton = (Button)view.findViewById(R.id.importBtn);
        Button returnButton = (Button)view.findViewById(R.id.returnButton);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(w.canGoBack())
                    w.goBack();
            }
        });

        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = w.getUrl();

                FragmentController.swapFragment(new DisplayRecetteFragment(url), R.id.container, getContext());
            }
        });

        return view;
    }



}