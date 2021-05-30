package com.example.testmenu;

import android.util.ArrayMap;
import android.util.Log;

import com.example.testmenu.bluetooth.Point;

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
    private static final String SERVER_URL = "https://foodgps.r2.enst.fr/";
    private static final String USER_FONCTIONS = "http/user_functions.php";

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
     * @return the User object if the connexion successes or null if it fails
     */
    public static User connect(String mail, String password) {
        addParam("action", "connect");
        addParam("mail",mail);
        addParam("password",password);

        User user = null;
        try {
            JSONObject answer = new JSONObject(post(SERVER_URL+USER_FONCTIONS));
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


    /**
     * Change the password
     * @param userId
     * @param newPassword
     */
    public static void updatePassword(int userId, String newPassword) {
        addParam("action", "update_password");
        addParam("user_id", String.valueOf(userId));
        addParam("new_password", newPassword);

        String result = post(SERVER_URL+USER_FONCTIONS);
    }

    /**
     * Change the mail
     * @param userId
     * @param newMail
     */
    public static void setEmail(int userId, String newMail) {
        addParam("action", "set_email");
        addParam("user_id", String.valueOf(userId));
        addParam("new_mail", newMail);

        String result = post(SERVER_URL+USER_FONCTIONS);
    }



    /*
    FONCTIONS CONCERNANT LES AMIS
     */


    /**
     * Accept a friend request
     * @param userId
     * @param friendId
     */
    public static void addFriend(int userId, int friendId) {
        addParam("action","add_friend");
        addParam("user_id", String.valueOf(userId));
        addParam("friend_id", String.valueOf(friendId));

        String result = post(SERVER_URL+USER_FONCTIONS);
    }


    /**
     * Send a friend request
     * @param userId
     * @param friendId
     */
    public static void sendDemand(int userId, int friendId) {
        addParam("action", "send_demand");
        addParam("user_id", String.valueOf(userId));
        addParam("friend_id", String.valueOf(friendId));

        String result = post(SERVER_URL+USER_FONCTIONS);
    }


    /**
     * Get all friend requests
     * @param userId
     * @return an ArrayList with all the id of the users who send a friend request
     */
    public static ArrayList<Integer> getDemandsOfUser(int userId) {
        addParam("action", "get_demands");
        addParam("user_id", String.valueOf(userId));

        ArrayList<Integer> demands = new ArrayList<Integer>();
        try {
            JSONArray json_demands = new JSONArray(post(SERVER_URL+USER_FONCTIONS));

            for(int i = 0; i < json_demands.length(); i++) {
                demands.add(json_demands.getInt(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return demands;
    }


    /**
     * Reject a friend demand
     * @param userId
     * @param friendId
     */
    public static void refuseDemand(int userId, int friendId) {
        addParam("action", "refuse_demand");
        addParam("user_id", String.valueOf(userId));
        addParam("friend_id", String.valueOf(friendId));

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

        ArrayList<Product> products = new ArrayList<Product>();

        try {
            JSONArray answer = new JSONArray(post(SERVER_URL+USER_FONCTIONS));

            for(int i =0; i< answer.length(); i++) {
                JSONObject json_product = answer.getJSONObject(i);
                products.add(new Product(json_product));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return products;
    }


    public static void updateProductLocation(int marketId, int productId, double newX, double newY, int newZ) {
        addParam("action","update_product");
        addParam("market_id", String.valueOf(marketId));
        addParam("product_id", String.valueOf(productId));
        JSONObject coords = new JSONObject();
        try {
            coords.put("x", newX);
            coords.put("y", newY);
            coords.put("z", newZ);
            addParam("coords", coords.toString());

            String result = post(SERVER_URL+USER_FONCTIONS);

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

        ArrayList<ListProduct> listProducts = new ArrayList<ListProduct>();

        try {
            JSONArray answer = new JSONArray(post(SERVER_URL + USER_FONCTIONS));

            for(int i=0; i<answer.length(); i++) {
                JSONObject json_list = answer.getJSONObject(i);
                listProducts.add(new ListProduct(json_list));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listProducts;
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

        Log.d("[DEBUG]", json_list.toString());
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

        ArrayList<ListProduct> listProducts = new ArrayList<ListProduct>();
        try {
            JSONArray answer = new JSONArray(post(SERVER_URL+USER_FONCTIONS));

            for(int i=0; i < answer.length(); i++) {
                JSONObject json_list = answer.getJSONObject(i);
                listProducts.add(new ListProduct(json_list));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listProducts;
    }


    /*
    FONCTIONS RELATIVES AUX RECETTES
     */

    /**
     * Add a new recipe for user user_id
     *
     * @param userId
     * @param recipe
     */
    public static void addNewRecette(int userId, Recette recipe) {
        try {
            JSONObject json_recipe = recipe.toJSON();
            addParam("action","add_new_recipe");
            addParam("user_id",String.valueOf(userId));
            addParam("recipe_name", recipe.getRecetteName());
            addParam("recipe",json_recipe.toString());

            String result = post(SERVER_URL+USER_FONCTIONS);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Get all the recipes of the user with id user_id
     * @param userId
     * @return an ArrayList of all recipes
     */
    public static ArrayList<Recette> getUserRecettes(int userId) {
        addParam("action","get_user_recipes");
        addParam("user_id",String.valueOf(userId));

        ArrayList<Recette> recipes = new ArrayList<Recette>();

        try {
            JSONArray answer = new JSONArray(post(SERVER_URL+USER_FONCTIONS));

            for (int i = 0; i < answer.length(); i++) {
                JSONObject recipe = answer.getJSONObject(i);
                recipes.add(new Recette(recipe));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipes;

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

        ArrayList<Market> markets = new ArrayList<Market>();

        try {
            JSONArray answer = new JSONArray(post(SERVER_URL+USER_FONCTIONS));

            for(int i=0; i < answer.length(); i++) {
                JSONObject json_market = answer.getJSONObject(i);

                markets.add(new Market(json_market));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return markets;
    }


    /**
     * Get all the offers in the market with id market_id
     * @param marketId
     * @return an ArrayList of all products and the offer
     */
    public static ArrayList<ProductOnSpecialOffer> getMarketOffers(int marketId) {
        addParam("action", "get_market_offers");
        addParam("market_id", String.valueOf(marketId));

        ArrayList<ProductOnSpecialOffer> offers = new ArrayList<ProductOnSpecialOffer>();

        try {
            JSONArray answer = new JSONArray(post(SERVER_URL+USER_FONCTIONS));

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
     * @param userId
     * @return an ArrayList with the friends id
     */
    public static ArrayList<Integer> getUserFriends(int userId) {
        addParam("action", "get_user_friends");
        addParam("user_id", String.valueOf(userId));

        ArrayList<Integer> friends = new ArrayList<Integer>();
        try {
            JSONArray answer = new JSONArray(post(SERVER_URL + USER_FONCTIONS));

            for(int i =0; i<answer.length(); i++) {
                friends.add(answer.getInt(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return friends;
    }

    private static void addParam(String key, String value) {
        keys.add(key);
        values.add(value);
    }

    /**
     * Get the coords of all beacons in a market (here there just one)
     * @return an arrayMap with beacons uuid and their positions
     */
    private static ArrayMap<String, Point> getBeaconsCoords(Market market) {
        addParam("action", "get_beacons");
        addParam("market", String.valueOf(market.getMarketId()));

        ArrayMap<String, Point> positions = new ArrayMap<>();
        try {
            JSONArray answer = new JSONArray(post(SERVER_URL + USER_FONCTIONS));

            for(int i=0; i<answer.length();i++) {
                JSONObject value = answer.getJSONObject(i);
                positions.put(value.getString("uuid"), new Point(value.getJSONObject("coords")));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return positions;
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

        Log.d("[RESULTS]", values.get(0));
        Log.d("[RESULTS]", "-->"+result);


        keys.clear();
        values.clear();

        return result;
    }
}
