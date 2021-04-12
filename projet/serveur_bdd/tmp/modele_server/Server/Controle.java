package com.forumantique.teledate.Server;

import android.content.Context;
import android.widget.ListView;

import com.forumantique.teledate.MainActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public final class Controle {

    private static Controle instance = null;
    private static Context context;

    public static boolean waitForResponse = false;
    public static boolean user_exists = false;

    private static AccesDistant accesDistant;

    private Controle() {
        super();
    }

    public static final Controle getInstance(Context context) {
        if(context!=null) {
            Controle.context = context;
        }
        if(Controle.instance == null) {
            Controle.instance = new Controle();
            accesDistant = new AccesDistant();
        }
        return Controle.instance;
    }


    public static void registerAccount(String pseudo, String mail, String pass) {
        List list = new ArrayList();
        list.add(pseudo);
        list.add(mail);
        list.add(pass);
        JSONArray jsonData = new JSONArray(list);
        accesDistant.envoi("enreg", jsonData);
    }

    public static void checkExistence(String username, String mail, Context context) {
        List list = new ArrayList();
        list.add(username);
        list.add(mail);
        JSONArray jsonData = new JSONArray(list);
        accesDistant.registerAccount(jsonData, context);
    }

    public static void checkUser(String username, String password, Context from) {

        List list = new ArrayList();
        list.add(username);
        list.add(password);
        JSONArray jsonData = new JSONArray(list);
        accesDistant.log(jsonData, from);
    }

    //Charger le profil de l'utilisateur
    public static void loadProfile(int id, Context from) {
        List list = new ArrayList();
        list.add(id);
        accesDistant.envoi("load_profile", new JSONArray(list), from);
    }

    //Obtenir le profil de l'utilisateur d'id : id
    public static void getProfile(int id) {
        List list = new ArrayList();
        list.add(id);
        accesDistant.envoi("get_profile", new JSONArray(list));
    }

    public static void getIds(int userId) {
        List list = new ArrayList();
        list.add(userId);
        accesDistant.envoi("get_ids", new JSONArray(list));
    }

    public void reinitializePassword(String mail, Context from) {
        List list = new ArrayList();
        list.add(mail);
        accesDistant.envoi("reinitialize_pass", new JSONArray(list), from);
    }

    public void newLike(int id, Context from) {
        List list = new ArrayList();
        list.add(MainActivity.userProfile.getId());
        list.add(id);
        accesDistant.envoi("match_request", new JSONArray(list), from);
    }

    public void addNewMatch(int userId, int matchId) {
        List list = new ArrayList();
        list.add(userId);
        list.add(matchId);
        accesDistant.envoi("new_match", new JSONArray(list));
    }

    public void checkNewMatches(int id, Context from) {

        List list = new ArrayList();
        list.add(id);
        accesDistant.envoi("check_new_match", new JSONArray(list), from);
    }

    public void getNewMatch(int id) {

        List list = new ArrayList();
        list.add(id);
        accesDistant.envoi("get_new_match", new JSONArray(list));
    }

    public void setNewMatch(int id, String[] newMatches) {
        String value = "";
        for(String s : newMatches)
            value+=s+"#";
        List list = new ArrayList();
        list.add(id);
        list.add(value);
        accesDistant.envoi("update_new_matches", new JSONArray(list));
    }

    public void updateSetting(int id, String setting, String value) {
        List list = new ArrayList();
        list.add(id);
        list.add(setting);
        list.add(value);
        accesDistant.envoi("update_setting", new JSONArray(list));
    }

    public void checkPseudoExistence(int id, String pseudo, Context from) {
        List list = new ArrayList();
        list.add(id);
        list.add(pseudo);
        accesDistant.envoi("check_pseudo", new JSONArray(list), from);
    }

    public void checkPass(int id, String oldPass, String newPass, Context from) {
        List list = new ArrayList();
        list.add(id);
        list.add(oldPass);
        list.add(newPass);
        accesDistant.envoi("check_pass", new JSONArray(list), from);
    }

    public void checkUpdates(int version) {
        List list = new ArrayList();
        list.add(version);
        accesDistant.envoi("get_version", new JSONArray(list));
    }
}
