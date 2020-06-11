package edu.fbansept.crud.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import java.util.List;

import edu.fbansept.crud.R;
import edu.fbansept.crud.model.Utilisateur;

public class MainActivity extends AppCompatActivity {

    //private final Button boutonChangerVue;
    private ImageView imageView;
    private List<Utilisateur> listeUtilisateur = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


}
