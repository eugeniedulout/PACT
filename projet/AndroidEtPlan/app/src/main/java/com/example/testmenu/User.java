package com.example.testmenu;


import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable{

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

    public User(String firstname) {
        this.firstname = firstname;
    }

    public User(JSONObject json_user) {
        try {
            this.id = json_user.getInt("id");
            this.mail = json_user.getString("mail");
            this.firstname = json_user.getString("firstname");
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

