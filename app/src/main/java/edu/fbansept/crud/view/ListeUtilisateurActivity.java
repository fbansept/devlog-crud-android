package edu.fbansept.crud.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import edu.fbansept.crud.R;
import edu.fbansept.crud.controller.UtilisateurController;
import edu.fbansept.crud.model.Utilisateur;
import edu.fbansept.crud.view.adapter.ListeUtilisateurAdapter;

public class ListeUtilisateurActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_utilisateur);

        TextView prenom =  findViewById(R.id.textView_prenom);
        TextView nom =  findViewById(R.id.textView_pseudo);

        RecyclerView listeUtilisateur = findViewById(R.id.liste_utilisateur);
        listeUtilisateur.setLayoutManager(new LinearLayoutManager(this));
        listeUtilisateur.addItemDecoration(
                new DividerItemDecoration(listeUtilisateur.getContext(), DividerItemDecoration.VERTICAL)
        );

        UtilisateurController.getInstance().getListeUtilisateur(
                this,
                (List<Utilisateur> utilisateurs) -> {

                    //Collections.sort(utilisateurs, (u1,u2)-> u1.getNom().compareTo(u2.getNom()));

                    listeUtilisateur.setAdapter(new ListeUtilisateurAdapter(
                            utilisateurs,
                            (Utilisateur utilisateur) -> afficherProfil(utilisateur)
                    ));
                }
        );
    }


    private void afficherProfil(Utilisateur utilisateur) {
        //startActivity(new Intent(this, ProfilUtilisateurActivity.class));
        Intent intent = new Intent(this, UtilisateurActivity.class);
        intent.putExtra("utilisateur", utilisateur);
        intent.putExtra("tarteALaFraise", 42);
        startActivity(intent);
    }
}
