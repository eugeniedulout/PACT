package com.example.serverapi.Server;

import com.example.serverapi.ExternClasses.ListProduct;
import com.example.serverapi.ExternClasses.Market;
import com.example.serverapi.ExternClasses.Product;
import com.example.serverapi.ExternClasses.ProductOnSpecialOffer;
import com.example.serverapi.ExternClasses.Recette;

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

    // Ajoute une recette sur le serveur
    public void addNewRecette(int user_id, Recette recipe);

    // Récupère les recettes d'un utilisateur
    public ArrayList<Recette> getUserRecettes(int user_id);

    //Retourne la liste des amis
    public ArrayList<User> getFriendsOfUser(int id);

    //Retourne La liste d'un User
    public ArrayList<ListProduct> getListsOfUser(int id);

    //Retourne la liste des produits d'une liste
    public ArrayList<Product> getProductOfList(ListProduct liste);
    
    //Retourne la liste des demandes
    public ArrayList<User> getDemandsOfUser(int id);

    //Modifie le mdp
    public void updatePassword(int id, String newPassword);
    
    //Modifie l'e-mail
    public void setEmail(int id,String email);

    //Envoi de demande à un User
    public void sendDemand(int id,User newFriend);

    //Ajout d'ami
    public void addFriend(int id, User friend);
}
