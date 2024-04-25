package jpa.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Date;
import java.util.Set;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name = "Ticket")
public class Ticket {
    private long id;
    private String titre;
    private Set<String> tags;
    private Statut statut;
    private List<Personne> suivis;
    private Boolean demande;
    private Date date;
    @Column(nullable = false)
    @JoinColumn(name = "discussion_id", referencedColumnName = "id")
    private Discussion discussion;
    @Column(nullable = false)
    private Utilisateur auteur;

    enum Statut {
        OPEN, CLOSED
    }

    public Ticket() {
        super();
    }

    public Ticket(Utilisateur auteur) {
        this.auteur = auteur;
    }
    public Ticket(Utilisateur auteur, Date date, String titre, Boolean demande, Boolean discussion) {
        this.auteur = auteur;
        this.date = date;
        this.titre = titre;
        this.demande = demande;
        if (Boolean.TRUE.equals(discussion)) {
            this.discussion = new Discussion();
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    @XmlElement(name = "id")
    public Long getId() {
        return id;
    }
    @XmlElement(name = "titre")
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @ElementCollection
    @XmlElementWrapper(name = "tags")
    @XmlElement(name = "tag")
    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    @XmlElement(name = "statut")
    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    @OneToMany
    @XmlElementWrapper(name = "suivis")
    @XmlElement(name = "suivi")
    public List<Personne> getSuivis() {
        return suivis;
    }

    public void setSuivis(List<Personne> suivis) {
        this.suivis = suivis;
    }

    @XmlElement(name = "demande")
    public Boolean getDemande() {
        return demande;
    }

    public void setDemande(Boolean demande) {
        this.demande = demande;
    }

    @XmlElement(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne
    @XmlElement(name = "auteur")
    public Utilisateur getAuteur() {
        return auteur;
    }

    public void setAuteur(Utilisateur auteur) {
        this.auteur = auteur;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    @XmlElement(name = "discussion")
    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }
}
