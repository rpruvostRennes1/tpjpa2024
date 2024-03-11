package jpa.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpa.dao.generic.EntityManagerHelper;
import jpa.domain.Ticket;
import jpa.domain.Utilisateur;

@WebServlet(name = "bddinfo",
        urlPatterns = { "/BddInfo"})
public class BddInfo extends HttpServlet {
    private List<Ticket> listTicket;
    private EntityManager manager;
    private Utilisateur utilisateur = null;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        getBddInfo();

        // Display the data in the Ticket table in the browser with listTicket in the form of a list
        out.println("<HTML>\n<BODY>\n" +
                "<H1>Recapitulatif des informations</H1>\n" +
                "<style>\n" +
                        "table, th, td {\n" +
                        "  border:1px solid black;\n" +
                        "}\n" +
                        "</style>");
        out.println("<table>\n<tr>" +
                    "<th>" + "id" + "</th>" +
                    "<th>" + "date" + "</th>" +
                    "<th>" + "demande" + "</th>" +
                    "<th>" + "statut" + "</th>" +
                    "<th>" + "titre" + "</th>" +
                    "<th>" + "auteur_id" + "</th>" +
                    "<th>" + "Username" + "</th>" +
                    "<th>" + "discussion_id" + "</th>" +
                    "</tr>");
        for (Ticket ticket : listTicket) {
            out.println( "<tr>"+
                    " <td>" + ticket.getId() + "</td>" +
                    " <td>" + ticket.getDate() + "</td>" +
                    " <td>" + ticket.getDemande() + "</td>" +
                    " <td>" + ticket.getStatut() + "</td>" +
                    " <td>" + ticket.getTitre() + "</td>" +
                    " <td>" + ticket.getAuteur().getId() + "</td>" +
                    " <td>" + ticket.getAuteur().getUsername() + "</td>" +
                    " <td>" + ticket.getDiscussion().getId() + "</td>" +
                    "</tr>");
        }

        // Create a link to the ticketform.html page
        out.println("<a href=\"ticketform.html\">Ajouter un ticket</a>");
        out.println("</table>\n</BODY></HTML>");
    }

    // Do post
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Add the ticket to the database
        manager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            // Find the author of the ticket in the Utilisateur table and set it as the author of the ticket using a sql query
           utilisateur = manager.createQuery("Select a From Utilisateur a where a.username = :nom",
                    jpa.domain.Utilisateur.class).setParameter("nom", request.getParameter("auteur_nom")).getSingleResult();
           manager.persist(new Ticket(utilisateur, new Date(), request.getParameter("Titre"), Boolean.parseBoolean(request.getParameter("demande")), true));
        } catch (NoResultException nre) {
            utilisateur = new Utilisateur(request.getParameter("auteur_nom"));
            manager.persist(utilisateur);
            manager.persist(new Ticket(utilisateur, new Date(), request.getParameter("Titre"), Boolean.parseBoolean(request.getParameter("demande")), true));
        }
        tx.commit();
        doGet(request, response);
    }

    private void getBddInfo() {
        manager = EntityManagerHelper.getEntityManager();

        EntityTransaction tx = manager.getTransaction();

        tx.begin();

        try {
            // Get the data in the Ticket table and store it in a list
            listTicket = manager.createQuery("Select a From Ticket a", Ticket.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
    }
}
