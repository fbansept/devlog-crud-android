package edu.fbansept.crud.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import edu.fbansept.crud.R;
import edu.fbansept.crud.view.ListeUtilisateurActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        ImageButton boutonListeUtilisateur =  view.findViewById(R.id.imageButton_listeUtilisateur);

        boutonListeUtilisateur.setOnClickListener((View v) -> {
            startActivity(new Intent(getActivity(), ListeUtilisateurActivity.class));
        });

        return view;
    }
}
