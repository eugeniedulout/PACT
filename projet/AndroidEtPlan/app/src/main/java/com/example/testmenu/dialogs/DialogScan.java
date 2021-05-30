package com.example.testmenu.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.testmenu.Controller;
import com.example.testmenu.FragmentController;
import com.example.testmenu.R;
import com.example.testmenu.fragments.ListFragment;

public class DialogScan extends DialogFragment {

    private String codeBar;

    public DialogScan( String codeBar ) {
        super();
        this.codeBar = codeBar;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_scan, null);

        TextView scantResultTextView = (TextView)view.findViewById(R.id.scantResultTextView);
        scantResultTextView.setText(codeBar);

        Button updateProductLocation = (Button)view.findViewById(R.id.updateProductLocation);

        updateProductLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///////////// //////////////////////////////////
                //////////// Fonction à inserer //////////////
                ////////////////////////////////////////////////
                //Controller.updateProductLocation();
                getDialog().dismiss();
            }
        });


        builder.setView(view);
        return builder.create();
    }


}
