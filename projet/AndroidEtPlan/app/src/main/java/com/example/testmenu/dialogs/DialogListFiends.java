package com.example.testmenu.dialogs;

import android.app.Dialog;
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
import com.example.testmenu.ListProduct;
import com.example.testmenu.MainActivity;
import com.example.testmenu.R;

import java.util.ArrayList;

public class DialogListFiends extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_list, null);

        ArrayList<ListProduct>  lists = Controller.getUserLists(MainActivity.user.getId());

        String[] listNames = new String[lists.size()];

        for(int i =0 ; i<lists.size(); i++)
            listNames[i] = lists.get(i).getListName();

        builder.setView(view).setTitle("Recettes");
        builder.setItems(listNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("hehehe",listNames[which]);

            }
        });

        return builder.create();
    }


}
