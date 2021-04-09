package com.example.serverapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.net.Network;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import com.example.serverapi.ExternClasses.ListProduct;
import com.example.serverapi.ExternClasses.Market;
import com.example.serverapi.ExternClasses.Product;
import com.example.serverapi.Server.Controller;

public class MainActivity extends AppCompatActivity {

    private TextView result;
    private String logs_tests="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);

        logs_tests += "getUsername: " + Controller.getUsername(1)+"\n";
        logs_tests += "connect (bad pass): " + Controller.connect("quentin.audinet@telecom-paris.fr","badpass")+ "\n";
        logs_tests += "connect (bad mail): " + Controller.connect("quentin.audinet@badmail.com","pass")+ "\n";
        logs_tests += "connect (good logs): " + Controller.connect("quentin.audinet@telecom-paris.fr","pass")+ "\n";
        logs_tests += "get_all_products: \n";
        for(Product p : Controller.getAllProducts(1)) {
            logs_tests += p.toString()+"\n--------\n";
        }
        logs_tests += "get_all_markets: \n";
        for(Market m : Controller.getAllMarkets()) {
            logs_tests += "\tid: " + m.getMarketId() +"\n\tname: "+m.getMarketName()+"\n\tlogo: " + m.getMarketLogoUrl() + "\n\topen_hours: "+ m.getOpenHour() + "\n\tclose_hours: "+m.getCloseHour()+"\n--------\n";
        }
        logs_tests += "get_friend_lists: \n";
        for(ListProduct listProduct : Controller.getFriendLists(1,2)) {
            logs_tests += listProduct.toString() + "\n--------\n";
        }


        result.setText(logs_tests);
    }
}