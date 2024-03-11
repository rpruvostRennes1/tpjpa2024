package jpa.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Discussion {
    private Long id;
    private List<Commentaire> commentaires;
    private List<Personne> participants;

    public Discussion(){}

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    @OneToMany
    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    @OneToMany
    public List<Personne> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Personne> participants) {
        this.participants = participants;
    }
}
