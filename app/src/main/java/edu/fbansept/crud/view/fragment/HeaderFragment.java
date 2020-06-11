package edu.fbansept.crud.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import edu.fbansept.crud.R;
import edu.fbansept.crud.controller.ConnexionController;
import edu.fbansept.crud.model.Utilisateur;
import edu.fbansept.crud.view.ProfilActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeaderFragment extends Fragment {

    public HeaderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_header, container, false);


        TextView nom =  view.findViewById(R.id.textView_pseudo);
        TextView prenom =  view.findViewById(R.id.textView_prenom);
        ImageButton boutonProfil = view.findViewById(R.id.button_profil);

        Utilisateur utilisateurConnecte = ConnexionController.getInstance().getUtilisateurToken(getActivity());

        nom.setText(utilisateurConnecte.getNom());
        prenom.setText(utilisateurConnecte.getPrenom());

        boutonProfil.setOnClickListener((View v) -> {
            startActivity(new Intent(getActivity(), ProfilActivity.class));
        });

        // Inflate the layout for this fragment
        return view;
    }
}
