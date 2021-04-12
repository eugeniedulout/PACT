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

import com.example.testmenu.FragmentController;
import com.example.testmenu.R;

public class DialogMarket extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_markets, null);

        String[] marketsNames = new String[] {"Franprix", "Auchan "};

        builder.setView(view).setTitle("Markets");
        builder.setItems(marketsNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("hehehe",marketsNames[which] );
                FragmentController.swapFragmentInMainContainer(new BuildingListFragment(1) ,getContext());
            }
        });

        return builder.create();
    }


}
