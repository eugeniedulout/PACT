package com.example.testmenu.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.testmenu.Controller;
import com.example.testmenu.FragmentController;
import com.example.testmenu.Market;
import com.example.testmenu.R;

import java.util.ArrayList;

public class DialogMarket extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_markets, null);

        ArrayList<String> marketsNames = new ArrayList<String>();

        ArrayList<Market> markets = new ArrayList<>();
        markets = Controller.getAllMarkets();
        String[] nameMarket = new String[markets.size()];

        for(int i =0 ; i<markets.size(); i++)
            nameMarket[i] = markets.get(i).getMarketName();

        builder.setView(view).setTitle("Markets");
        builder.setItems(nameMarket, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("hehehe",nameMarket[which]);
                FragmentController.swapFragmentInMainContainer(BuildingListFragment.getInstance() ,getContext());
            }
        });

        return builder.create();
    }


}
