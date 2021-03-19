package zineb.epokamp.profilapp.sofienePart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import java.util.ArrayList;

import zineb.epokamp.profilapp.R;

public class ListFragment extends Fragment // implements View.OnClickListener
{
    private CardView switchToRecette;
    private ImageButton addNewListe;
    private ArrayList<ListProduct> listlistOfProducts = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();

    /*
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("fr", "test");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            Log.e("jejejze",savedInstanceState.getString("fr") );
        }else
            Log.e("fff", "!!!");
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_liste, container, false);


        switchToRecette = (CardView) (v.findViewById(R.id.switchToRecette));
        // switchToRecette.setOnClickListener(this::onClick);

        addNewListe = (ImageButton)v.findViewById(R.id.addNewList);
        // addNewListe.setOnClickListener(this::onClick);

        initList();

        ListView listViewOflistOfProducts = (ListView) v.findViewById(R.id.listOflistOfProducts);

        listlistOfProducts.add(new ListProduct("Premiere liste",products));
        listlistOfProducts.add(new ListProduct("Deuxieme liste",products));
        listlistOfProducts.add(new ListProduct("Troisieme liste",products));

        ListAdapter adapter = new ListAdapter(getContext(), listlistOfProducts);
        listViewOflistOfProducts.setAdapter(adapter);

        // !!!!!!!!!!!!!!!!!!!!!!!!!!!
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!
        /*listViewOflistOfProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                listlistOfProducts.get(position).displayProductsInTheList(getContext());
            }
        });*/

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        listlistOfProducts.clear();
    }

    private void initList(){

        products.add(new Product("Pomme", "pomme", 0.5, true, "La pomme c'est bon pour la santé !"));
        products.add(new Product("Pates", "pates",0.7, false, "Les pâtes c'est pas cher !"));
        products.add(new Product("Beurre", "beurre", 1));
        products.add(new Product("Riz", "riz", 1.3));
        products.add(new Product("Nutella", "nutella", 3, false, "Le nutella c'est pas bon pour la santé !"));
        products.add(new Product("Pates", "pates",0.7));
        products.add(new Product("Riz", "riz", 1.3));
        products.add(new Product("Pomme", "pomme", 0.5));
        products.add(new Product("Nutella", "nutella", 3));
        products.add(new Product("Beurre", "ic_baseline_check_24", 0.5));
        products.add(new Product("Pates", "pates",0.7));
        products.add(new Product("Riz", "riz", 1.3));

    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!

    /*
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.addNewList:
                if(!BuildingListFragment.getInstance().isAdded())
                    FragmentController.swapFragmentInMainContainer(BuildingListFragment.getInstance(), getContext());
                break;
            case R.id.switchToRecette:
                FragmentController.swapFragmentInMainContainer(new RecetteFragment(), getContext());
                break;
        }
    }*/
}