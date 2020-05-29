package edu.fbansept.crud.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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

        TextView prenom =  findViewById(R.id.prenom);
        TextView nom =  findViewById(R.id.nom);

        RecyclerView listeUtilisateur = findViewById(R.id.liste_utilisateur);
        listeUtilisateur.setLayoutManager(new LinearLayoutManager(this));
        listeUtilisateur.addItemDecoration(
                new DividerItemDecoration(listeUtilisateur.getContext(), DividerItemDecoration.VERTICAL)
        );

        UtilisateurController.getInstance().getUtilisateur(
                this,
                1,
                (Utilisateur utilisateur) -> {
                    prenom.setText(utilisateur.getPrenom());
                    nom.setText(utilisateur.getNom());
                }
        );

        UtilisateurController.getInstance().getListeUtilisateur(
                this,
                (List<Utilisateur> utilisateurs) -> {
                    listeUtilisateur.setAdapter(new ListeUtilisateurAdapter(
                            utilisateurs,
                            this::afficherProfil
                    ));
                }
        );
    }

    void afficherProfil(Utilisateur utilisateur) {
        Intent intent = new Intent(this, ProfilUtilisateurActivity.class);
        intent.putExtra("utilisateur", utilisateur);
        startActivity(intent);
    }
}
