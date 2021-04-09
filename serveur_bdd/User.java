package zineb.epokamp.profilapp;

import java.util.ArrayList;

public class User {
    private String name;
    private Integer id;
    private String adresse;
    private ArrayList<String> friends;
    private ArrayList<String> demandes;

    public User(Integer id, String name, String adresse){
        this.id=id;
        this.name=name;
        this.adresse=adresse;
    }

    Integer getId(){
        return id;
    }

    String getName(){
        return name;
    }

    String getAdresseMail(){
        return adresse;
    }

    ArrayList<String> getUserFriends(int id){
        return friends;
    }

    ArrayList<String> getUserDemandes(int id){
        return demandes;
    }

}
