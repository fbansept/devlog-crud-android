package edu.fbansept.crud.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Debug;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.fbansept.crud.model.Role;
import edu.fbansept.crud.model.Utilisateur;
import edu.fbansept.crud.utils.JWTUtils;
import edu.fbansept.crud.utils.RequestManager;

public class ConnexionController {

    Utilisateur utilisateurConnecte = null;
    Map<Integer, Utilisateur> mapUtilisateur = new HashMap<>();

    public final static String FICHIER_PREFERENCE = "MesPreferences";

    private static ConnexionController instance = null;

    private ConnexionController() {
        super();
    }

    public static ConnexionController getInstance() {
        if(instance == null) {
            instance = new ConnexionController();
        }

        return instance;
    }

    public interface TelechargementListeUtilisateurListener {
        void onListeUtilisateurEstTelecharge(List<Utilisateur> listeUtilisateur);
    }

    public interface UtilisateurConnecteSuccessListener {
        void onUtilisateurConnecteSucces();
    }

    public interface UtilisateurConnecteErrorListener {
        void onUtilisateurConnecteError(String messageErreur);
    }

    public interface SuccesEcouteur {
        void onSuccesConnexion();
    }

    public interface ErreurEcouteur {
        void onErreurConnexion(String messageErreur);
    }

    public void connexion(
            Context context,
            String login,
            String password,
            SuccesEcouteur ecouteurSucces,
            ErreurEcouteur ecouteurErreur
    ){
        StringRequest stringRequest = new StringRequest
                                     //RequestManager.url + "auth/signin"
                (Request.Method.POST,RequestManager.url + "authentification" ,
                        token -> {
                            SharedPreferences preference = context.getSharedPreferences(FICHIER_PREFERENCE, 0); // 0 - for private mode
                            SharedPreferences.Editor editor = preference.edit();
                            editor.putString("token", token); // Storing string
                            editor.apply();

                            ecouteurSucces.onSuccesConnexion();
                        },
                        error -> ecouteurErreur.onErreurConnexion("Impossible de se connecter")
                ){

            @Override
            public Map<String, String> getHeaders() {

                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonBody = new JSONObject();
                    //Attntion si code de dale : jsonBody.put("username", login);
                    jsonBody.put("pseudo", login);
                    jsonBody.put("password", password);

                    return jsonBody.toString().getBytes(StandardCharsets.UTF_8);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }
        };

        RequestManager.getInstance(context).addToRequestQueue(context,stringRequest);

    }

    public void deconnexion(
            Context context,
            SuccesEcouteur ecouteurSucces
    ){
        SharedPreferences preference = context.getSharedPreferences(FICHIER_PREFERENCE, 0);
        SharedPreferences.Editor editor = preference.edit();
        editor.remove("token"); // Storing string
        editor.apply();
        ecouteurSucces.onSuccesConnexion();
    }


    public boolean isTokenValide(Context context){
        SharedPreferences preference = context.getSharedPreferences(FICHIER_PREFERENCE, 0);
        String token = preference.getString("token",null);
        if(token != null) {
            try {
                Date expiration = new Date(JWTUtils.getBody(token).getLong("exp"));
                if(expiration.before(new Date())){
                    return true;
                }
            } catch (UnsupportedEncodingException | JSONException e) {
                return false;
            }
        }
        return false;
    }

    public Utilisateur getUtilisateurToken(Context context){
        SharedPreferences preference = context.getSharedPreferences(FICHIER_PREFERENCE, 0);
        String token = preference.getString("token",null);

        Log.d("role",  token);

        if(token != null) {

            try {
                JSONObject jsonUtilisateur = JWTUtils.getBody(token);
                Utilisateur utilisateur = new Utilisateur(
                        jsonUtilisateur.getInt("id"),
                        jsonUtilisateur.getString("sub"),
                        jsonUtilisateur.getString("nom"),
                        jsonUtilisateur.getString("prenom")
                );

                String[] listeRole = jsonUtilisateur.getString("roles").split(",");

                for(int i = 0; i < listeRole.length; i++) {
                    utilisateur.getListeRole().add(new Role(
                            null,
                            listeRole[i]
                    ));
                }

                return utilisateur;

            } catch (UnsupportedEncodingException | JSONException e) {
                return new Utilisateur(0,"-", "-", "-");
            }
        }
        return new Utilisateur(0,"-", "-", "-");
    }

    public boolean isAdminUtilisateurConnecte(Context context) {
        for(Role role : this.getUtilisateurToken(context).getListeRole()){
            if(role.getNom().equals("ROLE_ADMIN")){
                return true;
            }
        }
        return false;
    }

}
