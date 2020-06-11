package edu.fbansept.crud.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.fbansept.crud.model.Role;
import edu.fbansept.crud.model.Utilisateur;
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

    public interface TelechargementListeUtilisateurListener {
        void onListeUtilisateurEstTelecharge(List<Utilisateur> listeUtilisateur);
    }

    public void getListeUtilisateur(Context context, TelechargementListeUtilisateurListener evenement) {

        //JsonArrayRequest si c'est une liste
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                //https://jsonplaceholder.typicode.com/users
                (Request.Method.GET, RequestManager.url + "user/utilisateurs", null,
                        response -> {

                            try {
                                List<Utilisateur> listeUtilisateur = new ArrayList<>();

                                for(int i = 0; i < response.length(); i++) {

                                    JSONObject jsonUser = response.getJSONObject(i);

                                    Utilisateur utilisateur = new Utilisateur(
                                            jsonUser.getInt("id"),
                                            jsonUser.getString("pseudo"),
                                            jsonUser.getString("nom"),
                                            jsonUser.getString("prenom"));

                                    listeUtilisateur.add(utilisateur);

                                    JSONArray jsonListeRole = jsonUser.getJSONArray("listeRole");
                                    for(int j = 0; j < jsonListeRole.length(); j++) {
                                        JSONObject jsonRole = jsonListeRole.getJSONObject(j);
                                        Role role = new Role(
                                                jsonUser.getInt("id"),
                                                jsonUser.getString("nom")
                                        );
                                    }
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
                                            response.getString("login"),
                                            response.getString("pseudo"),
                                            response.getString("nom"));

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
