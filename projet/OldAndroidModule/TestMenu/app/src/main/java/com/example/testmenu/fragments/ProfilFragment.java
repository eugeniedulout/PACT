package com.example.testmenu.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.testmenu.R;

public class ProfilFragment extends Fragment {
    private static final String PERSISTENT_VARIABLE_BUNDLE_KEY = "persistentVariable";
    private EditText persistentVariableEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, null);

        return view;
    }


}