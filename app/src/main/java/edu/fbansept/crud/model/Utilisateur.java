package edu.fbansept.crud.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

public class Utilisateur implements Serializable {
    private Integer id;
    private String nom;
    private String prenom;
    private Bitmap image;

    private List<Commande> listeCommande;

    public Utilisateur(Integer id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public List<Commande> getListeCommande() {
        return listeCommande;
    }

    public void setListeCommande(List<Commande> listeCommande) {
        this.listeCommande = listeCommande;
    }
}
