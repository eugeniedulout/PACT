package com.example.testmenu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.example.testmenu.R;

import java.util.ArrayList;

public class DemandeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> demandes = new ArrayList<>();
    private LayoutInflater inflater;
    private ArrayList<Boolean> areChecked = new ArrayList<>();
    public DemandeAdapter(Context context, ArrayList<String> demandes) {
        this.context = context;
        this.demandes = demandes;
        this.inflater  = LayoutInflater.from(context);
        for(int i = 0; i< demandes.size(); i++) {
            areChecked.add(false);
        }

    }

    
    @Override
    public int getCount() {
        return demandes.size();
    }

    @Override
    public String getItem(int position) {
        return demandes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setAreCheckedIndex(int index, boolean value) {
        this.areChecked.set(index, value );
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.rowlayout, null);

        String demandeName = getItem(position);


        CheckedTextView listNameText = (CheckedTextView) convertView.findViewById(R.id.txt_lan);
        if(listNameText.isChecked())
            areChecked.set(position, true);
        listNameText.setText(demandeName);

        return convertView;
    }

    public ArrayList<Boolean> getAreChecked() {

        return areChecked;
    }
}
