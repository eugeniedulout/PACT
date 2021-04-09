package com.example.serverapi.Server;

import com.example.serverapi.ExternClasses.Product;

import java.util.ArrayList;

public interface ControllerInterface {

    // Retourne le nom d'utilisateur
    public String getUsername(int id);

    // Retourne si l'authentification a réussi ou non
    public boolean connect(String username, String password);

    // Retourne la liste des produits d'un magasin particulier
    public  ArrayList<Product> getAllProducts(int marketId);
}
