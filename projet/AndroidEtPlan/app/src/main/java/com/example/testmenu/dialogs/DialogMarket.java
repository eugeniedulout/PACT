package com.example.testmenu.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.ArrayList;

public class DialogMarket extends DialogFragment {

    private ListView marketNameListV;
    private ArrayList<Market> markets = new ArrayList<>();
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_markets, null);

        ArrayList<String> marketsNames = new ArrayList<String>();

        markets = Controller.getAllMarkets();
        String[] nameMarket = new String[markets.size()];


        marketNameListV = (ListView)view.findViewById(R.id.marketNameListV);

        /*listlistOfProducts.add(new ListProduct("Deuxieme liste",products));
        listlistOfProducts.add(new ListProduct("Troisieme liste",products));*/

        MarketNameAdapter marketNameAdapter = new MarketNameAdapter(getContext(), markets);
        marketNameListV.setAdapter(marketNameAdapter);
        /*loadLists loadLists = new loadLists();
        loadLists.execute();*/




        marketNameListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Log.e("click" , " a" + markets.get(position).getMarketName());
                FragmentController.swapFragmentInMainContainer(new BuildingListFragment(markets.get(position).getMarketId()) ,getContext());
                getDialog().dismiss();
            }
        });




        for(int i =0 ; i<markets.size(); i++)
            nameMarket[i] = markets.get(i).getMarketName();

        TextView textView = view.findViewById(R.id.textView9);

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
