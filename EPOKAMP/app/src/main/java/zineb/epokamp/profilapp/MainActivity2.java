package zineb.epokamp.profilapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity2 extends Fragment {
    private Button preferences;
    private Button friends;
    private Button parameters;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_main2, container, false);

        this.preferences = v.findViewById(R.id.preferences);
        this.friends = v.findViewById(R.id.friends);
        this.parameters = v.findViewById(R.id.parameters);

        preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentController.swapFragmentInMainContainer(new preferencesActivity(), getContext());

            }
        });

        /*

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), FriendsActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });
        parameters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });*/

        return v;
    }
}