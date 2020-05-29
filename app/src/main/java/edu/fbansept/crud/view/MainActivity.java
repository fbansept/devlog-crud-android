package edu.fbansept.crud.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;

import java.util.List;

import edu.fbansept.crud.R;
import edu.fbansept.crud.controller.UtilisateurController;
import edu.fbansept.crud.model.Utilisateur;
import edu.fbansept.crud.utils.RequestManager;

public class MainActivity extends AppCompatActivity {

    //private final Button boutonChangerVue;
    private ImageView imageView;
    private List<Utilisateur> listeUtilisateur = null;

    public void onTelechargementFini(Utilisateur utilisateur){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView prenom =  findViewById(R.id.prenom);
        TextView nom =  findViewById(R.id.nom);
        Button bouton =  findViewById(R.id.bouton_liste_utilisateur);

        UtilisateurController.getInstance().getUtilisateur(
                this,
                1,
                (Utilisateur utilisateur) -> {
                        prenom.setText(utilisateur.getPrenom());
                        nom.setText(utilisateur.getNom());
                }
        );

        bouton.setOnClickListener((View v) -> {
            startActivity(new Intent(this,ListeUtilisateurActivity.class));
        });



        /*final Button boutonTest = findViewById(R.id.bouton_test);
        imageView = findViewById(R.id.avatar_view);*/

        /*boutonTest.setOnClickListener((View v) -> {

            ImageRequest request = new ImageRequest(//"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTZxzhtU8vk5l0tATkpXbkeJ6uzYFzRRbhzA9Riyh6nq3iG1jP4&usqp=CAU",
                    "http://192.168.0.50:8080/utilisateur/1/photo",
                    (Bitmap bitmap) -> {
                         imageView.setImageBitmap(bitmap);
                    }, 100, 100, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565,
                    (VolleyError error) -> Log.d("download error", error != null ? error.getMessage() : "")
            );

            RequestManager.getInstance(this).addToRequestQueue(this,request);
        });*/


        /*boutonTest.setOnClickListener((View v) -> {
                    //JsonArrayRequest si c'est une liste
                    //JsonObjectRequest si c'est un objet
                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                            (Request.Method.GET, "https://jsonplaceholder.typicode.com/users", null,
                                    response -> {
                                        try {
                                            listeUtilisateur = UtilisateurController
                                                    .getInstance()
                                                    .jsonArrayToListeUtilisateur(response);

                                            Toast.makeText(
                                                    this,
                                                    listeUtilisateur.get(0).getPrenom(),
                                                    Toast.LENGTH_SHORT)
                                                    .show();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    },
                                    error -> Log.d("Erreur", error.toString()));
                    RequestManager.getInstance(this).addToRequestQueue(this, jsonArrayRequest);
        });*/
    }

    /* boutonChangerVue = findViewById(R.id.bouton_change_vue);
        boutonTest = findViewById(R.id.bouton_test);
        imageViewCircle = findViewById(R.id.image_view_circle);

        boutonChangerVue.setOnClickListener((View v) -> {
                startActivity(new Intent(this,ListeUtilisateurActivity.class));
        });*/

        /*boutonTest.setOnClickListener((View v) -> {
            //JsonArrayRequest si c'est une liste
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, "http://192.168.0.50:8080/utilisateur/1", null,
                            response -> {
                                try {
                                    UtilisateurController.getInstance().jsonObjectToUtilisateur(this,response);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            },
                            error -> Log.d("Erreur",error.toString()));
            RequestManager.getInstance(this).addToRequestQueue(this,jsonObjectRequest);
        });*/

        /*boutonTest.setOnClickListener((View v) -> {
            UtilisateurController.getInstance().telechargeImageUtilisateur(
                    this,
                    1,
                    (Bitmap image)->imageViewCircle.setImageBitmap(image)
            );
        });*/

        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BlankFragment fragment = new BlankFragment();
        fragmentTransaction.add(R.id.conteneur, fragment);
        fragmentTransaction.commit();*/


}
