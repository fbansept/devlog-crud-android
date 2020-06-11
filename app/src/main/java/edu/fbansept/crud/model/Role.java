package edu.fbansept.crud.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {
    private Integer id;
    private String nom;

    private List<Role> listeRole;

    public Role(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
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
}
