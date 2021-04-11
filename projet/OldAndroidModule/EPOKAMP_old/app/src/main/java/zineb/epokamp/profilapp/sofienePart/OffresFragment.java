package zineb.epokamp.profilapp.sofienePart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import zineb.epokamp.profilapp.R;

public class OffresFragment extends Fragment {
    private ArrayList<String> marketLogoUrlArray = new ArrayList<String>() ;
    private ArrayList<String> marketNameArray = new ArrayList<String>() ;

    private ArrayList<String> marketLogoUrlArrayTwo = new ArrayList<String>() ;
    private ArrayList<String> marketNameArrayTwo = new ArrayList<String>() ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_offres, container, false);
        initImages();
        initImages2();

        LinearLayoutManager layoutManager =  new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.recycleViewMarkets);
        recyclerView.setLayoutManager(layoutManager);
        RecycleViewMarketsAdapter adapter = new RecycleViewMarketsAdapter(marketLogoUrlArray, marketNameArray, getContext());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManagerTwo =  new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerViewTwo = (RecyclerView)v.findViewById(R.id.recycleViewMarketsTwo);
        recyclerViewTwo.setLayoutManager(layoutManagerTwo);
        RecycleViewMarketsAdapter adapterTwo = new RecycleViewMarketsAdapter(marketLogoUrlArrayTwo, marketNameArrayTwo, getContext());

        recyclerViewTwo.setAdapter(adapterTwo);

        return v ;
    }

    private void initImages() {

        marketLogoUrlArray.add("auchan");
        marketNameArray.add("Auchan");

        marketLogoUrlArray.add("carrefour");
        marketNameArray.add("Carrefour");

        marketLogoUrlArray.add("franprix");
        marketNameArray.add("Franprix");

        marketLogoUrlArray.add("carrefour");
        marketNameArray.add("Carrefour");

        marketLogoUrlArray.add("auchan");
        marketNameArray.add("Auchan");

        marketLogoUrlArray.add("franprix");
        marketNameArray.add("Franprix");

    }

    private void initImages2() {

        marketLogoUrlArrayTwo.add("carrefour");
        marketNameArrayTwo.add("Carrefour");

        marketLogoUrlArrayTwo.add("carrefour");
        marketNameArrayTwo.add("Carrefour");

        marketLogoUrlArrayTwo.add("auchan");
        marketNameArrayTwo.add("Auchan");

        marketLogoUrlArrayTwo.add("franprix");
        marketNameArrayTwo.add("Franprix");

        marketLogoUrlArrayTwo.add("auchan");
        marketNameArrayTwo.add("Auchan");

        marketLogoUrlArrayTwo.add("lidl");
        marketNameArrayTwo.add("Lidl");

    }

}