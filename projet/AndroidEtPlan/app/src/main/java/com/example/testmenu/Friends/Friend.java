package com.example.testmenu.Friends;

public class Friend {
    private String Nom;
    private String Prénom;
    private Integer Id;
    private String Email;

    public Friend(String Nom, String Prénom, Integer Id){
        this.Nom=Nom;
        this.Prénom=Prénom;
        this.Id=Id;
    }

    public Integer getId() {
        return Id;
    }

    public String getNom(){
        return Nom;
    }
    public String getPrénom(){
        return Prénom;
    }

    public String getNameFriend(){
        return getNom()+" "+getPrénom();
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public void setPrénom(String prénom) {
        Prénom = prénom;
    }

}
