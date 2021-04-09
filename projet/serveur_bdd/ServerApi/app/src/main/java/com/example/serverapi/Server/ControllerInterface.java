package com.example.serverapi.Server;

public interface ControllerInterface {

    // Retourne le nom d'utilisateur
    public String getUsername(int id);

    // Retourne si l'authentification a réussi ou non
    public boolean login(String username, String password);

    // Retourne vrai si le produit a bien été ajouté, false sinon
    public boolean addToList(String product, int amount, int list_id);

}
