package com.example.testmenu.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testmenu.FragmentController;
import com.example.testmenu.Product;
import com.example.testmenu.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddProductFragment extends Fragment {
    private ImageButton addButton;
    private ImageButton minusButtton;
    private TextView quantityNumberText;
    private AutoCompleteTextView searchProduct;
    private Product foundProduct;
    private  Fragment fragmentProductInfo;
    private int count = 1;
    private FloatingActionButton addToListButton;

    private ArrayList<String> arrayProductsName = new ArrayList<String>();

    private int dataSentCounter = 0;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_product, container, false);
        addButton = (ImageButton) (v.findViewById(R.id.addNewRecette));
        minusButtton = (ImageButton) (v.findViewById(R.id.minusButton));
        quantityNumberText = (TextView)v.findViewById(R.id.quantiteNumberText);
        searchProduct = (AutoCompleteTextView)v.findViewById(R.id.serachProduct);
        addToListButton = (FloatingActionButton)v.findViewById(R.id.addToListButton);

        addToListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Bundle result = new Bundle();

                    Product productToAdd = new Product(searchProduct.getText().toString() + " x" + quantityNumberText.getText().toString(),searchProduct.getText().toString().toLowerCase(),1.2,"La pomme c'est cool");
                    result.putSerializable("productToAdd", productToAdd);

                    getParentFragmentManager().setFragmentResult("requestProductToAdd", result);
                    if(!BuildingListFragment.getInstance().isAdded()) {
                        FragmentController.swapFragmentInMainContainer(BuildingListFragment.getInstance(), getContext());
                    }

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              count++;
                                              quantityNumberText.setText(String.valueOf(count));

                                          }
                                      }
        );
        minusButtton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if(count > 1)
                                                 count--;
                                             quantityNumberText.setText(String.valueOf(count));
                                         }
                                     }
        );
        //Fragment fragmentProductInfo = (Fragment)v.findViewById(R.id.fragmentProductInfo);

        builderProductsName();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_selectable_list_item, arrayProductsName);
        searchProduct.setAdapter(adapter);
        searchProduct.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String name = searchProduct.getText().toString();
                if( name != null)
                    foundProduct = new Product(name, name.toLowerCase(),18.2, getResources().getString(R.string.bigString));
                    FragmentController.swapFragment(new ProductInfoFragment(foundProduct),R.id.containerProductInfo,getContext() );
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        return v;
    }

    private void  builderProductsName() {

        arrayProductsName.add("Pomme");
        arrayProductsName.add("Nutella");
        arrayProductsName.add("Pates");
        arrayProductsName.add("Riz");
        arrayProductsName.add("Beurre");

    }
}