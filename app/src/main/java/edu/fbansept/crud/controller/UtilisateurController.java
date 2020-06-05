package edu.fbansept.crud.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.fbansept.crud.model.Article;
import edu.fbansept.crud.model.Commande;
import edu.fbansept.crud.model.Utilisateur;
import edu.fbansept.crud.utils.JWTUtils;
import edu.fbansept.crud.utils.RequestManager;

public class UtilisateurController {

    Utilisateur utilisateurConnecte = null;

    Map<Integer, Utilisateur> mapUtilisateur = new HashMap<>();

    private static UtilisateurController instance = null;

    private UtilisateurController() {
        super();
    }

    public static UtilisateurController getInstance() {
        if(instance == null) {
            instance = new UtilisateurController();
        }

        return instance;
    }

    public void helloWorld(AppCompatActivity vue) {
        Toast.makeText( vue,"Hello world !",Toast.LENGTH_SHORT).show();
    }

    public interface TelechargementUtilisateurListener {
        void onUtilisateurEstTelecharge(Utilisateur utilisateur);
    }

    public void getUtilisateur(Context context, int idUtilisateur, TelechargementUtilisateurListener evenement) {
        if(mapUtilisateur.containsKey(idUtilisateur)) {
            evenement.onUtilisateurEstTelecharge(mapUtilisateur.get(idUtilisateur));
        } else {

            //JsonArrayRequest si c'est une liste
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    //https://jsonplaceholder.typicode.com/users/1
                    (Request.Method.GET, "http://192.168.0.50:8080/user/utilisateur/" + idUtilisateur, null,
                            response -> {

                                try {
                                    Utilisateur utilisateur = new Utilisateur(
                                            response.getInt("id"),
                                            response.getString("pseudo"),
                                            response.getString("prenom"));

                                    mapUtilisateur.put(utilisateur.getId(), utilisateur);

                                    evenement.onUtilisateurEstTelecharge(utilisateur);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            },
                            error -> Log.d("Erreur", error.toString())){

                @Override
                public Map<String, String> getHeaders() {

                    SharedPreferences preference = context.getSharedPreferences("MesPreferences", 0); // 0 - for private mode
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("Authorization", "Bearer " + preference.getString("token",null));
                    return params;
                }

            };

            RequestManager.getInstance(context).addToRequestQueue(context, jsonObjectRequest);
        }
    }

    public interface TelechargementListeUtilisateurListener {
        void onListeUtilisateurEstTelecharge(List<Utilisateur> listeUtilisateur);
    }

    public void getListeUtilisateur(Context context, TelechargementListeUtilisateurListener evenement) {

        //JsonArrayRequest si c'est une liste
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                //https://jsonplaceholder.typicode.com/users
                (Request.Method.GET, "http://192.168.0.50:8080/user/utilisateurs", null,
                        response -> {

                            try {
                                List<Utilisateur> listeUtilisateur = new ArrayList<>();

                                for(int i = 0; i < response.length(); i++) {

                                    JSONObject jsonUser = response.getJSONObject(i);

                                    Utilisateur utilisateur = new Utilisateur(
                                            jsonUser.getInt("id"),
                                            jsonUser.getString("pseudo"),
                                            "prenom");

                                    listeUtilisateur.add(utilisateur);

                                    /*JSONArray jsonListeCategories = jsonUser.getJSONArray("catregories");
                                    for(int j = 0; j < jsonListeCategories.length(); j++) {
                                        Commande commande = new Commande();
                                        JSONObject jsonCommande = jsonListeCategories.getJSONObject(j);
                                        JSONArray jsonListeArticle = jsonCommande.getJSONArray("articles");
                                        for(int k = 0; k < jsonListeArticle.length(); k++) {
                                            Article article = new Article();
                                            article.setNom(jsonListeArticle.getJSONObject(k).getString("nom"));
                                            commande.getListeArticle().add(article);
                                        }
                                        utilisateur.getListeCommande().add(commande);
                                    }*/
                                }

                                evenement.onListeUtilisateurEstTelecharge(listeUtilisateur);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> Log.d("Erreur", error.toString())
                ){

            @Override
            public Map<String, String> getHeaders() {

                SharedPreferences preference = context.getSharedPreferences("MesPreferences", 0); // 0 - for private mode
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + preference.getString("token",""));
                return params;
            }

        };

        RequestManager.getInstance(context).addToRequestQueue(context, jsonArrayRequest);

    }

    public interface UtilisateurConnecteSuccessListener {
        void onUtilisateurConnecteSucces();
    }

    public interface UtilisateurConnecteErrorListener {
        void onUtilisateurConnecteError(String messageErreur);
    }

    public void getUtilisateurConnecte(
            Context context,
            TelechargementUtilisateurListener evenementSucces,
            UtilisateurConnecteErrorListener evenementError
    ) {

        SharedPreferences preference = context.getSharedPreferences("MesPreferences", 0);

        String token = preference.getString("token",null);

        if(token != null) {

            try {
                Integer idUtilisateur = JWTUtils.getBody(token).getInt("id");

               /* getUtilisateur(
                        context,
                        idUtilisateur,
                        evenementSucces);
                )*/


              //  Context context,
               // int idUtilisateur, boolean teclechargeImageUtilisateur, TelechargementAvatarListener listener

            } catch (JSONException | UnsupportedEncodingException e) {
                evenementError.onUtilisateurConnecteError(e.getMessage());
            }
        }
    }

    public interface SuccesConnexionEcouteur {
        void onSuccesConnexion();
    }

    public interface ErreurConnexionEcouteur {
        void onErreurConnexion(String messageErreur);
    }

    public void connexion(
            Context context,
            String login,
            String password,
            SuccesConnexionEcouteur ecouteurSucces,
            ErreurConnexionEcouteur ecouteurErreur
    ){
        StringRequest stringRequest = new StringRequest
                                     //RequestManager.url + "auth/signin"
                (Request.Method.POST,RequestManager.url + "authentification" ,
                        token -> {
                            SharedPreferences preference = context.getSharedPreferences("MesPreferences", 0); // 0 - for private mode
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














   /* public void connexion(Context context,String login, String password,  TelechargementUtilisateurListener evenement) {

        StringRequest stringRequest = new StringRequest
                (Request.Method.GET, RequestManager.url + "authentification",
                        token -> {
                            SharedPreferences preference = context.getSharedPreferences("MesPreferences", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = preference.edit();
                            editor.putString("token", token); // Storing string
                            editor.apply();

                            evenement.onUtilisateurEstTelecharge(null);
                        },
                        error -> Log.d("Erreur",error != null ? error.getMessage() : ""));

        RequestManager.getInstance(context).addToRequestQueue(context,stringRequest);
    }
*/
    public interface TelechargementAvatarListener {
        void onTelechargementFini(Bitmap image);
    }

    public void getUtilisateur(
            Context context,
            int idUtilisateur, boolean teclechargeImageUtilisateur, TelechargementAvatarListener listener) {

        //JsonArrayRequest si c'est une liste
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, RequestManager.url + "utilisateur/" + idUtilisateur, null,
                        response -> {

                            if(teclechargeImageUtilisateur) {
                                try {
                                    Utilisateur utilisateur = new Utilisateur(
                                            response.getInt("id"),
                                            response.getString("nom"),
                                            response.getString("prenom"));

                                    mapUtilisateur.put(utilisateur.getId(), utilisateur);

                                    telechargeImageUtilisateur(context,  idUtilisateur, listener);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        error -> Log.d("Erreur",error.toString()));

        RequestManager.getInstance(context).addToRequestQueue(context,jsonObjectRequest);
    }

    public void telechargeImageUtilisateur(Context context, int idUtilisateur, TelechargementAvatarListener listener) {

        if (mapUtilisateur.get(idUtilisateur) == null) {
            getUtilisateur(context, idUtilisateur, true, listener);
        } else if (mapUtilisateur.get(idUtilisateur).getImage() == null) {
            ImageRequest request = new ImageRequest(RequestManager.url + "utilisateur/" + idUtilisateur + "/photo",
                    (Bitmap bitmap) -> {
                        mapUtilisateur.get(idUtilisateur).setImage(bitmap);
                        listener.onTelechargementFini(bitmap);
                    }
                    , 100, 100, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565,
                    (VolleyError error) -> Log.d("download error", error != null ? error.getMessage() : ""));

            RequestManager.getInstance(context).addToRequestQueue(context, request);

        } else {
            listener.onTelechargementFini(mapUtilisateur.get(idUtilisateur).getImage());
        }
    }
}
