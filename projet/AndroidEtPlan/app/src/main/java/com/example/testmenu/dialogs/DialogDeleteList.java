package com.example.testmenu.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.testmenu.Controller;
import com.example.testmenu.FragmentController;
import com.example.testmenu.Market;
import com.example.testmenu.R;
import com.example.testmenu.adapters.MarketNameAdapter;
import com.example.testmenu.fragments.BuildingListFragment;
import com.example.testmenu.fragments.ListFragment;
import com.example.testmenu.fragments.RecetteFragment;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.ArrayList;

public class DialogDeleteList extends DialogFragment {

    private ListView marketNameListV;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_delete, null);


        Button deleteButtonList = (Button)view.findViewById(R.id.deleteButtonList);
        Button notDeleteButtonList = (Button)view.findViewById(R.id.notDeleteButtonList);

        deleteButtonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///////////// //////////////////////////////////
                //////////// Fonction à inserer //////////////
                ////////////////////////////////////////////////

                FragmentController.swapFragmentInMainContainer(new ListFragment(), getContext());
                getDialog().dismiss();
            }
        });

        notDeleteButtonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        builder.setView(view);

        /*builder.setItems(nameMarket, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("hehehe",nameMarket[which]);
                FragmentController.swapFragmentInMainContainer(BuildingListFragment.getInstance() ,getContext());
            }
        });*/

        return builder.create();
    }


}
