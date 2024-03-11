package jpa.domain;

import jakarta.persistence.Entity;

@Entity
public class Utilisateur extends Personne{
    public Utilisateur(String username) {
        super(username);
    }

    public Utilisateur() {

    }
}
