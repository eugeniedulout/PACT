package com.example.testmenu;


import androidx.annotation.NonNull;

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

public class Controller {

    //URLS
    public static final String SERVER_URL = "https://foodgps.r2.enst.fr/";
    public static final String USER_FONCTIONS = "http/user_functions.php";

    //ArrayList declaration
    private static ArrayList<String> keys = new ArrayList<String>();
    private static ArrayList<String> values = new ArrayList<String>();




    /*
    FONCTIONS DE CONNECTION
     */

    /**
     *
     * @param mail
     * @param password
     * @return a boolean : true if the connexion success or false if it fail
     */
    public static boolean connect(String mail, String password) {
        addParam("action", "connect");
        addParam("mail",mail);
        addParam("password",password);

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



    /*
    FONCTIONS CONCERNANT LES PRODUITS
     */

    /**
     *
     * @param marketId
     * @return the list of all the products in the market with the id marketId
     */
    public static ArrayList<Product> getAllProducts(int marketId) {
        addParam("action","get_all_products");
        addParam("market_id", String.valueOf(marketId));

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


                products.add(new Product(json_product));
            }
            return products;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*
    FONCTIONS CONCERNANT LES LISTES
     */


    /**
     *
     * @param userId
     * @return all the lists of userId
     */
    public static ArrayList<ListProduct> getUserLists(int userId) {
        addParam("action", "get_user_lists");
        addParam("user_id", String.valueOf(userId));

        JSONArray answer = null;
        try {
            answer = new JSONArray(post(SERVER_URL + USER_FONCTIONS));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<ListProduct> listProducts = new ArrayList<ListProduct>();
        try {
            for(int i=0; i<answer.length(); i++) {
                JSONObject json_list = answer.getJSONObject(i);
                listProducts.add(new ListProduct(json_list));
            }
            return listProducts;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Ajoute une nouvelle liste listProduct sur le serveur pour userId
     *
     * @param userId
     * @param listProduct
     *
     */
    public static void addNewListOfProducts(int userId, ListProduct listProduct) {
        addParam("action","add_new_list");
        addParam("user_id", String.valueOf(userId));
        JSONObject json_list = listProduct.toJSON();
        addParam("list",json_list.toString());
        addParam("list_name",listProduct.getListName());
        String result = post(SERVER_URL+USER_FONCTIONS);
    }



    /**
     *
     * @param friendId
     * @param userId
     * @return All the lists of friendId shared with userId
     */
    public static ArrayList<ListProduct> getFriendLists(int friendId, int userId) {
        addParam("action", "get_friend_lists");
        addParam("friend_id", String.valueOf(friendId));
        addParam("user_id",String.valueOf(userId));

        JSONArray answer = null;
        try {
            answer = new JSONArray(post(SERVER_URL+USER_FONCTIONS));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<ListProduct> listProducts = new ArrayList<ListProduct>();
        try {
            for(int i=0; i < answer.length(); i++) {
                JSONObject json_list = answer.getJSONObject(i);
                listProducts.add(new ListProduct(json_list));

            }
            return listProducts;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }



    /*
    FONCTIONS CONCERNANT LES MAGASINS
     */

    /**
     *
     * @return the list of all the market in the database
     */
    public static ArrayList<Market> getAllMarkets() {
        addParam("action", "get_all_markets");

        JSONArray answer = null;
        try {
            answer = new JSONArray(post(SERVER_URL+USER_FONCTIONS));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<Market> markets = new ArrayList<Market>();


        try {
            for(int i=0; i < answer.length(); i++) {
                JSONObject json_market = answer.getJSONObject(i);

                int marketId = json_market.getInt("market_id");
                String marketName = json_market.getString("name");
                String logoUrl = json_market.getString("logo");
                String openHours = json_market.getString("open_hours");
                String closeHours = json_market.getString("close_hours");

                markets.add(new Market(marketId, marketName, logoUrl, openHours, closeHours));
            }

            return markets;
        } catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }


    public static ArrayList<ProductOnSpecialOffer> getMarketOffers(int market_id) {
        addParam("action", "get_market_offers");
        addParam("market_id", String.valueOf(market_id));

        JSONArray answer = null;
        try {
            answer = new JSONArray(post(SERVER_URL+USER_FONCTIONS));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<ProductOnSpecialOffer> offers = new ArrayList<ProductOnSpecialOffer>();

        try {
            for(int i =0; i< answer.length(); i++) {
                JSONObject json_product = answer.getJSONObject(i);
                ProductOnSpecialOffer onSpecialOffer = new ProductOnSpecialOffer(json_product);

                offers.add(onSpecialOffer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return offers;
    }








    public static String getUsername(int id) {
        addParam("action","username");
        addParam("id", String.valueOf(id));
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


    private static void addParam(String key, String value) {
        keys.add(key);
        values.add(value);
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

    public static class User {

        private int id;
        private String mail;
        private String firstname;
        private String lastname;

        public User(int id, String mail, String firstname, String lastname) {
            this.id = id;
            this.mail = mail;
            this.firstname = firstname;
            this.lastname = lastname;
        }

        public User(JSONObject json_user) {
            try {
                this.id = json_user.getInt("id");
                this.mail = json_user.getString("mail");
                this.firstname = json_user.getString("mail");
                this.lastname = json_user.getString("lastname");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public JSONObject toJSON() {
            JSONObject json_user = new JSONObject();
            try {
                json_user.put("id", this.getId());
                json_user.put("mail", this.getMail());
                json_user.put("firstname", this.getFirstname());
                json_user.put("lastname", this.getLastname());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json_user;
        }

        @NonNull
        @Override
        public String toString() {
            String str = "";
            str += "user_id: " + this.getId() + "\n";
            str += "mail: " + this.getMail() +"\n";
            str += "firstname: " + this.getFirstname() + "\n";
            str += "lastname: " + this.getLastname();

            return str;
        }
    }
}
