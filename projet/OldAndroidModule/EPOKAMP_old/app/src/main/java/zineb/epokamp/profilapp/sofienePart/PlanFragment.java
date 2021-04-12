package zineb.epokamp.profilapp.sofienePart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import zineb.epokamp.profilapp.R;

public class PlanFragment extends Fragment {
    private AutoCompleteTextView searchMarketName;
    private AutoCompleteTextView searchListName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_plan, container, false);

        searchMarketName = (AutoCompleteTextView)v.findViewById(R.id.serachMarketName);
        searchListName = (AutoCompleteTextView)v.findViewById(R.id.serachListName);
        TextView tv = (TextView)v.findViewById(R.id.planText);

        ArrayList<String> marketsName = builderMarketsName();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, marketsName);
        searchMarketName.setAdapter(adapter);
        /*
        searchMarketName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String result = searchMarketName.getText().toString();
                if( result != null)
                    tv.setText(result);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });*/

        return v;
    }


    private ArrayList<String> builderMarketsName (){
        ArrayList<String> marketsName = new ArrayList<String>();
        marketsName.add("Auchan");
        marketsName.add("Auphan");

        marketsName.add("Auchann");
        marketsName.add("Auchannn");
        marketsName.add("Auchannnn");
        marketsName.add("Auchannnnn");
        marketsName.add("Auchannnnnn");
        marketsName.add("Auchannnnnnn");
        marketsName.add("Auchannnnnnnnn");
        marketsName.add("Auchannnnnnnnnn");
        marketsName.add("Auchannnnnnnnnnnnnn");
        marketsName.add("Auchannnnnnnnnnnnnnnnnnnn");
        marketsName.add("Auchannnnnnnnnnnnnnnnnnnnnnn");

        marketsName.add("Aachann");
        marketsName.add("Lidl");
        marketsName.add("Monoprix");
        marketsName.add("Franprix");
        marketsName.add("Carrefour");
        return marketsName;

    }
}