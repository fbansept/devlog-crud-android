package edu.fbansept.crud.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import edu.fbansept.crud.R;
import edu.fbansept.crud.model.Utilisateur;

public class ProfilUtilisateurActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilisateur);
        Intent i = getIntent();
        Utilisateur utilisateur = (Utilisateur) i.getSerializableExtra("utilisateur");
    }
}
