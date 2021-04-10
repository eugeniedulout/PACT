package com.example.serverapi.Server;

import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;

import com.example.serverapi.ExternClasses.ListProduct;
import com.example.serverapi.ExternClasses.Market;
import com.example.serverapi.ExternClasses.Product;
import com.example.serverapi.ExternClasses.ProductOnSpecialOffer;
import com.example.serverapi.ExternClasses.Recette;
import com.example.serverapi.ExternClasses.User;

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




    /*
    FONCTIONS DE CONNECTION
     */

    /**
     *
     * @param mail
     * @param password
     * @return a boolean : true if the connexion success or false if it fail
     */
    public static User connect(String mail, String password) {
        addParam("action", "connect");
        addParam("mail",mail);
        addParam("password",password);

        JSONObject answer = null;
        try {
            answer = new JSONObject(post(SERVER_URL+USER_FONCTIONS));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        User user = null;
        try {
            if(answer.getBoolean("valid")) {
                user = new User(answer.getJSONObject("user"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }


    /**
     * Get a user from his id
     * @param userId
     * @return the User
     */
    public static User getUser(int userId) {
        addParam("action", "get_user");
        addParam("user_id", String.valueOf(userId));

        try {
            JSONObject answer = new JSONObject(post(SERVER_URL+USER_FONCTIONS));
            if(answer.getBoolean("valid")) {
                User user = new User(answer.getJSONObject("user"));
                return user;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Create an account
     * @param firstname
     * @param lastname
     * @param mail
     * @param password
     * @return null if the mail is already used and the User object if the account was created
     */
    public static User signUp(String firstname, String lastname, String mail, String password) {
        addParam("action", "sign_up");
        addParam("mail",mail);
        addParam("password",password);
        addParam("firstname",firstname);
        addParam("lastname",lastname);

        try {
            JSONObject answer = new JSONObject(post(SERVER_URL+USER_FONCTIONS));
            if(answer.getBoolean("valid")) {
                return new User(answer.getJSONObject("user"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }


    public static void updatePassword(int userId, String newPassword) {
        addParam("action", "update_password");
        addParam("user_id", String.valueOf(userId));
        addParam("new_password", newPassword);

        String result = post(SERVER_URL+USER_FONCTIONS);
    }

    public static void setEmail(int userId, String newMail) {
        addParam("action", "set_email");
        addParam("user_id", String.valueOf(userId));
        addParam("new_mail", newMail);

        String result = post(SERVER_URL+USER_FONCTIONS);
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
    FONCTIONS RELATIVES AUX RECETTES
     */

    /**
     * Add a new recipe for user user_id
     *
     * @param user_id
     * @param recipe
     */
    public static void addNewRecette(int user_id, Recette recipe) {
        try {
            JSONObject json_recipe = recipe.toJSON();
            addParam("action","add_new_recipe");
            addParam("user_id",String.valueOf(user_id));
            addParam("recipe_name", recipe.getRecetteName());
            addParam("recipe",json_recipe.toString());

            String result = post(SERVER_URL+USER_FONCTIONS);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Get all the recipes of the user with id user_id
     * @param user_id
     * @return an ArrayList of all recipes
     */
    public static ArrayList<Recette> getUserRecettes(int user_id) {
        addParam("action","get_user_recipes");
        addParam("user_id",String.valueOf(user_id));

        JSONArray answer = null;
        try {
            answer = new JSONArray(post(SERVER_URL+USER_FONCTIONS));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<Recette> recipes = new ArrayList<Recette>();

        try {
            for (int i = 0; i < answer.length(); i++) {
                JSONObject recipe = answer.getJSONObject(i);
                recipes.add(new Recette(recipe));
            }

            return recipes;
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


    /**
     * Get all the offers in the market with id market_id
     * @param market_id
     * @return an ArrayList of all products and the offer
     */
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


    /**
     * Get the list of all the friends of the user user_id
     * @param user_id
     * @return an ArrayList with the friends id
     */
    public static ArrayList<Integer> getUserFriends(int user_id) {
        addParam("action", "get_user_friends");
        addParam("user_id", String.valueOf(user_id));

        String answer = post(SERVER_URL+USER_FONCTIONS);

        String[] friendList = null;
        ArrayList<Integer> friends = new ArrayList<Integer>();

        if(answer.contains("%")) {
           friendList = answer.split("%");
            for(String num : friendList) {
                friends.add(Integer.parseInt(num));
            }
       }
       return friends;
    }


    public static String getUsername(int id) {
        addParam("action","get_username");
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
}
