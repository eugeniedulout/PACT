package com.example.serverapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.net.Network;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import com.example.serverapi.ExternClasses.Ingredient;
import com.example.serverapi.ExternClasses.ListProduct;
import com.example.serverapi.ExternClasses.Market;
import com.example.serverapi.ExternClasses.Product;
import com.example.serverapi.ExternClasses.ProductOnSpecialOffer;
import com.example.serverapi.ExternClasses.Recette;
import com.example.serverapi.Server.Controller;

import java.util.ArrayList;
import java.util.List;

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
        logs_tests += "***************************\n";
        logs_tests += "get_all_products: \n";
        for(Product p : Controller.getAllProducts(1)) {
            logs_tests += p.toString()+"\n--------\n";
        }
        logs_tests += "***************************\n";
        logs_tests += "get_all_markets: \n";
        for(Market m : Controller.getAllMarkets()) {
            logs_tests += "\tid: " + m.getMarketId() +"\n\tname: "+m.getMarketName()+"\n\tlogo: " + m.getMarketLogoUrl() + "\n\topen_hours: "+ m.getOpenHour() + "\n\tclose_hours: "+m.getCloseHour()+"\n--------\n";
        }
        logs_tests += "***************************\n";
        logs_tests += "get_user_lists (user_id=0): \n";
        for(ListProduct listProduct : Controller.getUserLists(0)) {
            logs_tests += listProduct.toString() + "\n--------\n";
        }
        logs_tests += "***************************\n";
        logs_tests += "get_user_lists (user_id=1): \n";
        for(ListProduct listProduct : Controller.getUserLists(1)) {
            logs_tests += listProduct.toString() + "\n--------\n";
        }
        logs_tests += "***************************\n";
        logs_tests += "get_friend_lists: \n";
        for(ListProduct listProduct : Controller.getFriendLists(1,2)) {
            logs_tests += listProduct.toString() + "\n--------\n";
        }

        Product p1 = new Product("feutre", "feutre.png", 80, "Un feutre");
        Product p2 = new Product("feuille", "feuille.png", 10, "Une feuille");
        Product p3 = new Product("eau", "eau.png", 300, "De l'eau");

        ArrayList<Product> productsList = new ArrayList<Product>();
        productsList.add(p1);
        productsList.add(p2);
        productsList.add(p3);

        ListProduct new_list = new ListProduct("Liste de test", productsList);

        Log.d("[INFO]", new_list.toJSON().toString());

        Controller.addNewListOfProducts(6, new_list);
        logs_tests += "[ADDING NEW LIST TO USER_ID 6]: \n";
        logs_tests += "get_user_lists (user_id=6): \n";
        for(ListProduct listProduct : Controller.getUserLists(6)) {
            logs_tests += listProduct.toString() + "\n--------\n";
        }
        logs_tests += "***************************\n";
        logs_tests += "get_market_offers (market_id=1):\n";
        for (ProductOnSpecialOffer p : Controller.getMarketOffers(1)) {
            logs_tests+= p.toString() + "\n";
        }

        logs_tests += "***************************\n";
        logs_tests += "get_user_friends (user_id=2):\n";
        for(Integer i : Controller.getUserFriends(1)) {
            logs_tests += "\t" + i + "\n";
        }

        logs_tests += "***************************\n";
        logs_tests += "[ADDING NEW RECIPE TO USER_ID 4]: \n";

        Ingredient i1 = new Ingredient("pate sablée", "pate.png");
        Ingredient i2 = new Ingredient("pommes", "pomme.png");
        Ingredient i3 = new Ingredient("compote", "compote.png");

        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        ingredients.add(i1);
        ingredients.add(i2);
        ingredients.add(i3);

        ArrayList<String> instructions = new ArrayList<String>();
        instructions.add("Faire cuire la pate");
        instructions.add("Couper les pommes");
        instructions.add("Etaler la compote");
        instructions.add("Faire à nouveau cuire");

        Recette recipe = new Recette("Tarte aux pommes", ingredients, instructions);

        Controller.addNewRecette(4, recipe);

        logs_tests += "***************************\n";
        logs_tests += "get_user_recipes: (user_id=4)\n";

        for(Recette recette : Controller.getUserRecettes(4)) {
            logs_tests += recette.toString() + "\n------------\n";
        }


        result.setText(logs_tests);
    }
}