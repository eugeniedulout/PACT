package com.example.testmenu.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.testmenu.FragmentController;
import com.example.testmenu.R;

public class WebViewFragment extends Fragment {

    private Button importButton;
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
        w.loadUrl("https://www.marmiton.org/recettes/recherche.aspx?aqt=chocolat");

        w.getSettings().setBuiltInZoomControls(true);
        w.getSettings().setUseWideViewPort(true);
        w.getSettings().setLoadWithOverviewMode(true);

        importButton = (Button)view.findViewById(R.id.importBtn);


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