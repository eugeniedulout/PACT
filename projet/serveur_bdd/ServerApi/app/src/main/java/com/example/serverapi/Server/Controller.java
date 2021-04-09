package com.example.serverapi.Server;

import android.os.AsyncTask;
import android.util.Log;

import com.example.serverapi.ExternClasses.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {

    //URLS
    public static final String SERVER_URL = "https://foodgps.r2.enst.fr/";
    public static final String USER_FONCTIONS = "http/user_functions.php";

    //ArrayList declaration
    private static ArrayList<String> keys = new ArrayList<String>();
    private static ArrayList<String> values = new ArrayList<String>();


    public static boolean connect(String mail, String password) {
        keys.add("action");
        values.add("connect");
        keys.add("mail");
        values.add(mail);
        keys.add("password");
        values.add(password);

        JSONObject answer = null;
        try {
            answer = new JSONObject(post(SERVER_URL+USER_FONCTIONS));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            return answer.getString("valid").equals("true");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<Product> getAllProducts(int marketId) {
        keys.add("action");
        values.add("get_all_products");
        keys.add("market_id");
        values.add(String.valueOf(marketId));

        JSONArray answer = null;
        try {
            answer = new JSONArray(post(SERVER_URL+USER_FONCTIONS));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<Product> products = new ArrayList<Product>();
        try {
            for(int i =0; i< answer.length(); i++) {
                JSONObject json_product = answer.getJSONObject(i);

                String name = json_product.getString("name");
                String imgUrl = json_product.getString("img_url");
                double price = json_product.getDouble("price");
                String description = json_product.getString("description");

                Log.d("[DEBUG]", name+" "+imgUrl+" "+price+ " "+description);

                products.add(new Product(name,imgUrl,price,description));
            }
            return products;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getUsername(int id) {
        keys.add("action");
        values.add("get_username");
        keys.add("id");
        values.add(String.valueOf(id));
        JSONObject answer = null;
        try {
            answer = new JSONObject(post(SERVER_URL+USER_FONCTIONS));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            return answer.getString("username");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * Send a POST request to the server
     * @param address   the server address
     *
     * @return the server's answer
     */
    public static String post(String address) {
        String result = "";
        OutputStreamWriter writer = null;
        BufferedReader reader = null;

        try {
            //Request creation
            String data = "";
            for(int i=0; i < keys.size(); i++) {
                if(i!=0) data+="&";
                data+= URLEncoder.encode(keys.get(i), "UTF-8")+"="+URLEncoder.encode(values.get(i), "UTF-8");
            }

            //Connexion to the Server
            URL url = new URL(address);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            //Send request
            writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(data);
            writer.flush();

            //Read answer
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = reader.readLine()) != null)
                result+=line;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {writer.close();}catch (Exception e){}
            try {reader.close();}catch (Exception e){}
        }

        keys.clear();
        values.clear();

        return result;
    }
}
