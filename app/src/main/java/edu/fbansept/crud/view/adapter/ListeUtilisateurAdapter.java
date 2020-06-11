package edu.fbansept.crud.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

    private List<Utilisateur> listeUtilisateur;

    public ListeUtilisateurAdapter(List<Utilisateur> listeUtilisateur, EcouteurClicUtilisateur ecouteur) {
        this.ecouteur = ecouteur;
        this.listeUtilisateur = listeUtilisateur;
    }

    static class UtilisateurViewHolder extends RecyclerView.ViewHolder {

        TextView textViewPseudo;
        TextView textViewNom;
        TextView textViewPrenom;
        LinearLayout layoutItem;

        UtilisateurViewHolder(View itemView){
            super(itemView);
            textViewPseudo = itemView.findViewById(R.id.textView_pseudo);
            textViewNom = itemView.findViewById(R.id.textView_nom);
            textViewPrenom = itemView.findViewById(R.id.textView_prenom);
            layoutItem = itemView.findViewById(R.id.layout_item);
        }
    }

    @NonNull
    @Override
    public UtilisateurViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_utilisateur,parent,false);
        return new UtilisateurViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UtilisateurViewHolder holder, int position) {
        Utilisateur utilisateur = listeUtilisateur.get(position);
        holder.textViewPseudo.setText(utilisateur.getPseudo());
        holder.textViewPrenom.setText(utilisateur.getPrenom());
        holder.textViewNom.setText(utilisateur.getNom());

        holder.layoutItem.setOnClickListener( v ->
                ecouteur.onClicUtilisateur(utilisateur)
        );
    }

    @Override
    public int getItemCount() {
        return listeUtilisateur.size();
    }
}
