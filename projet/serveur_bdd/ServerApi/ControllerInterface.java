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

    // Retourne un objet User à partir de son id
    public User getUser(int userId);

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



    // Ajoute une recette sur le serveur
    public void addNewRecette(int user_id, Recette recipe);

    // Récupère les recettes d'un utilisateur
    public ArrayList<Recette> getUserRecettes(int user_id);



    //Retourne La liste d'un User
    public ArrayList<ListProduct> getListsOfUser(int id);

    //Retourne la liste des produits d'une liste
    public ArrayList<Product> getProductOfList(ListProduct liste);
    
  //Modifie le mdp
    public void updatePassword(int id, String newPassword);
    

    //Modifie l'e-mail
    public void setEmail(int id,String email);


     // Return null if the password and email are wrong
     public User connect(String email, String password);

    // Renvoie null si l'adresse email existe déjà dans la BDD, sinon renvoie objet de type User 
     public User signUp(String email, String prenom, String nom, String password);


    //Ajout d'ami
    public void addFriend(int id, User friend);

    //Envoi de demande à un User
    public void sendDemand(int id,User newFriend);

    //Retourne la liste des demandes
    public ArrayList<User> getDemandsOfUser(int id);

    //Refuser une demande
    public void refuseDemand(int id, User refusedFriend);



    /*
     *
     * Il ne suffit pas juste d'itérer sur chacunes des listes renvoyées par getFriendLists() ?
     *
     */
    //Retourne la liste de produits des listes des amis friendID de l'utilisateur (userId)
    public ArrayList<ArrayList<Product>> getFriendsProduct(int user_id);


    /*
     *
     * Il est possible de faire appel à getUser(id) pour l'ensemble des id renvoyés par la fonction getUserFriends(int userId) déjà présente, afin de récupérer une liste de User.
     *
     */
    // Retourne la liste des amis de user_id
    public ArrayList<User> getUserFriends(int user_id)

  

        //////////////////////////////////////////////////////
    //////////////TO DO ! ////////////////////////////////
    /////////////////////////////////////////////////////


////////////////////////////////////////////////////
// Modification de la structure de la classe ListProduct
/////////////////////////////////////////////////////
public class ListProduct {

    private String listName;
    private ArrayList<Product> listOfProducts;
    private ArrayList<Integer> quantites; // Pour le produit à l'index i de listOfProducts , on lui associe une quantité( un entier ) à l'index i de quantites
    private int market_id; // La liste de course est asssocié à un magasin


      public ListProduct(String listName, ArrayList<Product> listOfProducts) {
          this.listName = listName;
          this.listOfProducts = listOfProducts;
      }
  }

  //#################### Il faut donc modifier  la fonction getUserLists() ##########################

}

public void shareList(ListProduct liste, int user, int userfriend); // Partage de liste entre deux amis, les deux amis pourront acceder aux listes partagés

public void shareRecipe(Recette recette, int user, int Userfriend); // Partage de recette entre deux amis, les deux amis pourront acceder aux recette partagés

public void updateList(int userId, String listName, ArrayList<Product> newProducts); // Modification des produits dans une liste. On remplace les produits dans la liste par les nouveaux produits "newProducts"  T

public void removeList(String listName, int userId); // Suppression d'une liste pour un utilisateur donné 

public void removeRecipe(String recipeName, int userId); // Suppression d'une recette pour un utilisateur donné 

public Product getProductFromBarCode( int barcode, int marketId); //  renvoie le produit pour un barcode et un magasin donné.


/////////////////////////////// Il faudrait aussi ajouter deux attributs à la classe Product ( désolé :(  )

// Premier parametre : nutriscore (c'est un entier entre 0 et 4 : 0 pour un produit de bonne qualité et 4 pour un produit de mauvaise qualité)
// Second Paramètre : tag  (pour l'algo de suggestion de Charles il va en avoir besoin, c'est une chaine de caractere qui vaudra "Bio" par exemple) 

// Nouveaux constructeur  Constructeur

    private int nutriscore; 
    private String tag;
    
    public Product(String name, String productImageUrl, double price, double x, double y, int z, int nutriscore, String tag) {
        this(name, productImageUrl, price);
        this.x = x;
        this.y = y;
        this.z = z;
        this.nutriscore = nutriscore;
        this.tag = tag;
    }









}
