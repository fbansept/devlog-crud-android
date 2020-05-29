package edu.fbansept.crud.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.fbansept.crud.R;
import edu.fbansept.crud.model.Utilisateur;

public class ListeUtilisateurAdapter extends RecyclerView.Adapter
        <ListeUtilisateurAdapter.UtilisateurViewHolder> {

    private final EcouteurClicUtilisateur ecouteur;

    public interface EcouteurClicUtilisateur {
        void onClicUtilisateur(Utilisateur item);
    }

    List<Utilisateur> listeUtilisateur;

    public ListeUtilisateurAdapter(List<Utilisateur> listeUtilisateur, EcouteurClicUtilisateur ecouteur) {
        this.ecouteur = ecouteur;
        this.listeUtilisateur = listeUtilisateur;
    }

    public static class UtilisateurViewHolder extends RecyclerView.ViewHolder {

        TextView nom;
        TextView prenom;
        Button bouton;

        public UtilisateurViewHolder(View itemView){
            super(itemView);
            //TODO récupérer les composants ici (TextView , button ...)

            nom = itemView.findViewById(R.id.nom);
            prenom = itemView.findViewById(R.id.prenom);
            bouton = itemView.findViewById(R.id.button);
        }
    }

    @NonNull
    @Override
    public UtilisateurViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_utilisateur,parent,false);
        UtilisateurViewHolder utilisateurViewHolder = new UtilisateurViewHolder(view);
        return utilisateurViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UtilisateurViewHolder holder, int position) {
        Utilisateur utilisateur = listeUtilisateur.get(position);
        holder.prenom.setText(utilisateur.getPrenom());
        holder.nom.setText(utilisateur.getNom());

        holder.bouton.setOnClickListener( v ->
                ecouteur.onClicUtilisateur(utilisateur)
        );
    }

    @Override
    public int getItemCount() {
        return listeUtilisateur.size();
    }
}
