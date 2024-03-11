package jpa.domain;

import jakarta.persistence.Entity;

@Entity
public class Membre extends Personne{
    public Membre(String username){
        super(username);
    }

    public Membre() {

    }
}
