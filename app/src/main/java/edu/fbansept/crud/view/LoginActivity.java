package edu.fbansept.crud.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.fbansept.crud.R;
import edu.fbansept.crud.controller.UtilisateurController;

public class LoginActivity extends AppCompatActivity {

    TextView textViewLogin;
    TextView textViewPassword;
    Button boutonConnexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textViewLogin =  findViewById(R.id.login);
        textViewPassword =  findViewById(R.id.password);
        boutonConnexion =  findViewById(R.id.bouton_connexion);

        boutonConnexion.setOnClickListener((View v) -> {
            UtilisateurController.getInstance().connexion(
                    this,
                    textViewLogin.getText().toString(),
                    textViewPassword.getText().toString(),
                    () -> startActivity(new Intent(this,MainActivity.class)),
                    (String messageErreur) -> Toast.makeText(this, messageErreur, Toast.LENGTH_LONG).show()
            );
        });























/*
        UtilisateurController
                .getInstance()
                .getUtilisateurConnecte(
                        this,
                        () -> startActivity(new Intent(this,MainActivity.class)),
                        (String messageErreur) -> initFormulaireConnexion();
                );*/
    }

   /* void initFormulaireConnexion()  {
        textViewLogin =  findViewById(R.id.login);
        textViewPassword =  findViewById(R.id.password);
        boutonConnexion =  findViewById(R.id.bouton_connexion);*/

       /* boutonConnexion.setOnClickListener((View v) -> {
            UtilisateurController
                    .getInstance()
                    .connexion(this,
                            textViewLogin.getText().toString(),
                            textViewPassword.getText().toString(),
                            () -> startActivity(new Intent(this,MainActivity.class))
                    );
        });*/

}
