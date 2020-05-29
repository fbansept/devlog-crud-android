package edu.fbansept.crud.model;

import java.util.List;

public class Commande {
   private List<Article> listeArticle;

    public List<Article> getListeArticle() {
        return listeArticle;
    }

    public void setListeArticle(List<Article> listeArticle) {
        this.listeArticle = listeArticle;
    }
}
