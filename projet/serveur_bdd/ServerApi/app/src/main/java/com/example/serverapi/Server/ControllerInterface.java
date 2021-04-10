package com.example.serverapi.Server;

import com.example.serverapi.ExternClasses.ListProduct;
import com.example.serverapi.ExternClasses.Market;
import com.example.serverapi.ExternClasses.Product;
import com.example.serverapi.ExternClasses.ProductOnSpecialOffer;

import java.util.ArrayList;

public interface ControllerInterface {

    // Retourne le nom d'utilisateur
    public String getUsername(int id);

    // Retourne si l'authentification a réussi ou non
    public boolean connect(String username, String password);

    // Retourne la liste des produits d'un magasin particulier
    public  ArrayList<Product> getAllProducts(int marketId);

    // Retourne l'ensemble des listes d'un utilisateur
    public ArrayList<ListProduct> getUserLists(int userId);

    // Ajoute une nouvelle liste à un utilisateur
    public void addNewListOfProducts(int userId, ListProduct listProduct);

    // Retourne l'ensemble des listes partagées entre l'amis friendId et l'utilisateur userId
    public ArrayList<ListProduct> getFriendLists(int friendId, int userId);

    // Retourne l'ensemble deds magasins présents dans la base de donnée
    public ArrayList<Market> getAllMarkets();

    // Retourne l'ensemble des promotions d'un magasin
    public ArrayList<ProductOnSpecialOffer> getMarketOffers(int market_id);

    // Retourne la liste des amis de user_id
    public ArrayList<Integer> getUserFriends(int user_id);
}
