package com.forumantique.teledate.Server;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.forumantique.teledate.AppUpdate;
import com.forumantique.teledate.DateActivity;
import com.forumantique.teledate.GetNewMatches;
import com.forumantique.teledate.LoginActivity;
import com.forumantique.teledate.MainActivity;
import com.forumantique.teledate.NewDaterActivity;
import com.forumantique.teledate.Profile;
import com.forumantique.teledate.SettingsActivities.PassActivity;
import com.forumantique.teledate.SettingsActivities.PseudoActivity;
import com.forumantique.teledate.GetProfiles;

import org.json.JSONArray;

public class AccesDistant implements AsyncResponse {

    //Constante

    //Adresse webhost
/*
    private static final String SERVERADDR = "https://teledate.000webhostapp.com/teledate/serveur_users.php";
    public static final String UPLOAD_URL = "https://teledate.000webhostapp.com/teledate/upload_photo.php";
    public static final String UPLOAD_PATH = "https://teledate.000webhostapp.com/teledate/uploads/";*/
    //Adresse Rezel

    private static final String SERVERADDR = "https://forumantique.rezel.net/wp-content/teledate-scripts/serveur_users.php";
    public static final String UPLOAD_URL = "https://forumantique.rezel.net/wp-content/teledate-scripts/upload_photo.php";
    public static final String UPLOAD_PATH = "https://forumantique.rezel.net/wp-content/teledate-uploads/";
    public static final String APK_URL = "https://forumantique.rezel.net/wp-content/teledate.apk";


    private Context from;
    Profile profile = null;

    public AccesDistant() {
        super();
    }


    //Retour du serveur distant
    @Override
    public void processFinish(String output) {

        Controle controle = Controle.getInstance(null);

        Log.d("serveur","*****************"+output);
        //Découpage du message reçu avec %
        String[] message = output.split("%");
        //Dans message[0] soit enreg, dernier ou erreur
        //Dans message[1] le reste du message

        //S'il y a deux cases
        if(message.length>1) {

            switch (message[0]) {
                case "enreg":
                    Log.d("enreg", "**************"+message[1]);
                    break;

                case "dernier":
                    Log.d("dernier", "**************"+message[1]);
                    break;

                case "Erreur !":
                    Log.d("Erreur !", "**************"+message[1]);
                    break;

                case "dater_exist":
                    Log.d("existence","*********************"+message[1]+"-"+message[2]);

                    boolean username_exist = message[1].matches("true");
                    boolean mail_exists = message[2].matches("true");

                    ((NewDaterActivity)from).checkPseudoAndMailExists(!username_exist, !mail_exists);

                    break;

                case "login":
                    Log.d("login","*********************"+message[1]+" id:"+message[2]);

                    ((LoginActivity)from).checkLogs(message[1].matches("true"), Integer.parseInt(message[2]));
                    break;

                case "load_profile":
                    Log.d("loadProfile","*********************"+message[1]+"-"+message[2]+"-"+message[3]);
                    MainActivity.userProfile.setPseudo(message[1]);
                    MainActivity.userProfile.setSexe(Integer.parseInt(message[2]));
                    MainActivity.userProfile.setBio(message[3]);
                    Controle.getInstance(null).checkNewMatches(MainActivity.userProfile.getId(), from);
                    break;

                case "get_profile":
                    Log.d("loadProfile","*********************"+message[1]+"-"+message[2]+"-"+message[3]+"-"+message[4]);
                    int id = Integer.parseInt(message[1]);
                    MainActivity.allProfiles.get(id).setPseudo(message[2]);
                    MainActivity.allProfiles.get(id).setSexe(Integer.parseInt(message[3]));
                    MainActivity.allProfiles.get(id).setBio(message[4]);
                    GetProfiles.check(id);
                    break;

                case "reinitialize_pass":
                    Log.d("reinitialize password", "*********************************"+message[1]);
                    if(message[1].matches("true")) {
                        ((LoginActivity)from).closeDialog();
                        Toast.makeText(from, "Un mail a été envoyé. Vérifie tes SPAMS", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(from, "L'adresse mail n'est pas reconnue", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case "get_ids":
                    Log.d("getting ids", "**************"+message[1]);
                    String[] ids = message[1].split("#");
                    MainActivity.ids = new Integer[ids.length];
                    for(int i =0; i < ids.length; i++) {
                        MainActivity.allProfiles.put(Integer.parseInt(ids[i]), new Profile(Integer.parseInt(ids[i])));
                        MainActivity.ids[i] = Integer.parseInt(ids[i]);
                    }
                    GetProfiles.loadProfiles();
                    break;

                case "match_request":
                    Log.d("match_request", "*************"+message[1]);
                    boolean match = message[1].matches("true");
                    ((DateActivity)from).checkMatch(match);
                    break;

                case "new_match":
                    Log.d("new_match", "ok");
                    break;

                case "check_new_match":
                    Log.d("check new match", "*********"+message[1]);
                    String[] matchIds = message[1].split("#");
                    ((LoginActivity)from).startHomeActivity(matchIds);
                    break;

                case "get_new_match":
                    Log.d("get new match", "******"+message[1]+"//"+message[2]+"//"+message[3]+"//"+message[4]);
                    if(!message[1].matches("false")) {
                        int matchId = Integer.parseInt(message[1]);
                        String pseudo = message[2];
                        int sexe = Integer.parseInt(message[3]);
                        String bio = message[4];

                        Profile p = new Profile(matchId, pseudo, sexe, bio, null);
                        GetNewMatches.setPhoto(p);
                    }
                    break;

                case "update_new_matches":
                    Log.d("update_new_matches", message[1]);
                    break;

                case "update_setting":
                    Log.d("update setting", message[1]);
                    break;

                case "check_pseudo":
                    Log.d("check_pseudo", message[1]);
                    boolean dispo = message[1].matches("true");
                    ((PseudoActivity)from).changePseudo(dispo);
                    break;

                case "check_pass":
                    Log.d("check_pass", message[1]);
                    boolean valid = message[1].matches("true");
                    ((PassActivity)from).finalizePass(valid);
                    break;

                case "get_version":
                    Log.d("get_version", message[1]);
                    int lastVersion = Integer.parseInt(message[1]);
                    if(lastVersion > AppUpdate.APP_VERSION) {
                        AppUpdate app = new AppUpdate();
                        app.promptUpdate();
                    }
                    break;
            }
        } else {
            switch (message[0]) {
                case "check_new_match":
                    Log.d("check new match", "*********No new matches");
                    String[] empty = new String[0];
                    ((LoginActivity)from).startHomeActivity(empty);
                    break;
            }
        }
    }

    public void envoi(String operation, JSONArray lesdonneesJSON) {
        AccesHTTP accesDonnees = new AccesHTTP();
        //lien de délégation
        accesDonnees.delegate = this;
        //ajout parametres
        accesDonnees.addParam("operation", operation);
        accesDonnees.addParam("lesdonnees", lesdonneesJSON.toString());
        //appel au serveur -> appel doInBackgroung de AccesHTTP
        accesDonnees.execute(SERVERADDR);
    }

    public void envoi(String operation, JSONArray lesdonneesJSON, Context from) {
        this.from = from;

        AccesHTTP accesDonnees = new AccesHTTP();
        //lien de délégation
        accesDonnees.delegate = this;
        //ajout parametres
        accesDonnees.addParam("operation", operation);
        accesDonnees.addParam("lesdonnees", lesdonneesJSON.toString());
        //appel au serveur -> appel doInBackgroung de AccesHTTP
        accesDonnees.execute(SERVERADDR);
    }

    public void loadProfile(JSONArray lesdonneesJSON, Profile profile) {
        this.profile = profile;
        AccesHTTP accesDonnees = new AccesHTTP();
        accesDonnees.delegate = this;
        accesDonnees.addParam("operation", "load_profile");
        accesDonnees.addParam("lesdonnees", lesdonneesJSON.toString());
        accesDonnees.execute(SERVERADDR);
    }


    public void log(JSONArray data, Context from) {
        this.from = from;

        AccesHTTP accesDonnees = new AccesHTTP();
        //lien de délégation
        accesDonnees.delegate = this;
        //ajout parametres
        accesDonnees.addParam("operation", "login");
        accesDonnees.addParam("lesdonnees", data.toString());
        //appel au serveur -> appel doInBackgroung de AccesHTTP
        accesDonnees.execute(SERVERADDR);
    }

    public void registerAccount(JSONArray data, Context context) {
        this.from = context;

        AccesHTTP accesDonnees = new AccesHTTP();
        //lien de délégation
        accesDonnees.delegate = this;
        //ajout parametres
        accesDonnees.addParam("operation", "dater_exist");
        accesDonnees.addParam("lesdonnees", data.toString());
        //appel au serveur -> appel doInBackgroung de AccesHTTP
        accesDonnees.execute(SERVERADDR);
    }

}
