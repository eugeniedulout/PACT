package com.forumantique.teledate.Server;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class AccesHTTP extends AsyncTask<String, Integer, Long> {

    private ArrayList<NameValuePair> parametres;
    private String ret = null;
    public AsyncResponse delegate = null;

    //Constructeur
    public AccesHTTP() {
        parametres = new ArrayList<NameValuePair>();
    }

    //Ajout d'un paramètre POST
    public void addParam(String nom, String valeur) {
        parametres.add(new BasicNameValuePair(nom, valeur));
    }

    /*
    Connection en tâche de fond dans un thread séparé
     */

    @Override
    protected Long doInBackground(String... strings) {
        HttpClient cnxHttp = new DefaultHttpClient();       //Objets pour la connection
        HttpPost paramCnx = new HttpPost(strings[0]);

        try {
            //encodage des paramètres
            paramCnx.setEntity(new UrlEncodedFormEntity(parametres));
            //connexion et envoie des paramètres, attente de réponse
            HttpResponse reponse = cnxHttp.execute(paramCnx);
            //Transformation de la réponse
            ret = EntityUtils.toString(reponse.getEntity());

        } catch (UnsupportedEncodingException e) {
            Log.d("Erreur encodage","******************"+e.toString());
        } catch (ClientProtocolException e) {
            Log.d("Erreur Protocole","******************"+e.toString());
        } catch (IOException e) {
            Log.d("Erreur I/O","******************"+e.toString());
        }
        return null;
    }


    @Override
    protected void onPostExecute(Long result) {
        delegate.processFinish(ret.toString());
    }
}
