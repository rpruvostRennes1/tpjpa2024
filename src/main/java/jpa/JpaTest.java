package jpa;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpa.dao.generic.EntityManagerHelper;
import jpa.domain.Membre;
import jpa.domain.Ticket;
import jpa.domain.Utilisateur;

import java.util.Date;
import java.util.List;

public class JpaTest {


    private EntityManager manager;

    public JpaTest(EntityManager manager) {
        this.manager = manager;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        EntityManager manager = EntityManagerHelper.getEntityManager();

        JpaTest test = new JpaTest(manager);

        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            test.nettoyerTables();
            test.creeUtilisateurs();
            test.creeMembres();
            test.creeTickets();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();

        test.listUtilisateur();


        manager.close();
        EntityManagerHelper.closeEntityManagerFactory();
        System.out.println(".. done");
    }

    private void creeUtilisateurs() {
        int numOfUsers = manager.createQuery("Select a From Utilisateur a",
                Utilisateur.class).getResultList().size();
        if (numOfUsers == 0) {
            manager.persist(new Utilisateur("Billy"));
            manager.persist(new Utilisateur(":)"));
            manager.persist(new Utilisateur(":("));
        }

    }

    private void listUtilisateur() {
        List<Utilisateur> resultList = manager.createQuery("select a From Utilisateur a",
                Utilisateur.class).getResultList();
        System.out.println("num of users: " + resultList.size());
        for (Utilisateur user : resultList) {
            System.out.println("next user: " + user);
        }
    }

    private void creeMembres() {
        int numOfUsers = manager.createQuery("Select a From Membre a",
                Membre.class).getResultList().size();
        if (numOfUsers == 0) {
            manager.persist(new Membre("Billy"));
            manager.persist(new Membre(":)"));
            manager.persist(new Membre(":("));
        }
    }

    private void creeTickets() {
        int numOfTicket = manager.createQuery("Select a From Ticket a",
                Membre.class).getResultList().size();
        if (numOfTicket == 0) {
            List<Utilisateur> resultList = manager.createQuery("select a From Utilisateur a",
                    Utilisateur.class).getResultList();
            for (Utilisateur utilisateur : resultList) {
                manager.persist(new Ticket(utilisateur, new Date(), "Titre du ticket", true, true));
            }
        }
    }

    private void nettoyerTables(){
        manager.createQuery("DELETE FROM Ticket").executeUpdate();
        manager.createQuery("DELETE FROM Discussion").executeUpdate();
        manager.createQuery("DELETE FROM Membre").executeUpdate();
        manager.createQuery("DELETE FROM Utilisateur").executeUpdate();

    }

}
