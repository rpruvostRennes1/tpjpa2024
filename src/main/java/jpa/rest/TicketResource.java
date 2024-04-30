package jpa.rest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jpa.dao.generic.EntityManagerHelper;
import jpa.domain.Ticket;
import jpa.domain.Utilisateur;

import java.util.List;


@Path("/ticket")
@Produces({"application/json", "application/xml"})
public class TicketResource {
    // Get all tickets
    @GET
    public Response getTickets() {
        // Start the entity manager
        EntityManager manager = EntityManagerHelper.getEntityManager();

        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        // Get all tickets using a query
        Ticket[] tickets = manager.createQuery("Select a From Ticket a", Ticket.class).getResultList().toArray(new Ticket[0]);
        tx.commit();
        return Response.ok(tickets)
                .build();
    }
    // Get ticket by id
    @GET
    @Path("/{ticketId}")
    public Response getTicket(@PathParam("ticketId") String ticketId)  {
        EntityManager manager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        Ticket ticket = null;
        try {
            ticket = manager.createQuery("Select a From Ticket a where a.id = " + ticketId, Ticket.class).getSingleResult();
        } catch (NoResultException e) {
            tx.rollback();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        tx.commit();
        return Response.ok(ticket).build();
    }
    // Add another path to adding a ticket
    @POST
    @Path("/add")
    public Response addTicket(Ticket ticket)  {
        EntityManager manager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        String username = ticket.getAuteur().getUsername();
        TypedQuery<Utilisateur> query = manager.createQuery("Select a From Utilisateur a where a.username = :username", Utilisateur.class);
        query.setParameter("username", username);
        List<Utilisateur> users = query.getResultList();

        if (users.isEmpty()) {
            manager.persist(ticket.getAuteur());
        } else {
            ticket.setAuteur(users.get(0));
        }

        manager.persist(ticket);
        tx.commit();

        return Response.ok(ticket).build();
    }

    // Update ticket
    @PUT
    @Path("/update/{ticketId}")
    public Response updateTicket(@PathParam("ticketId") String ticketId, Ticket updatedTicket) {
        EntityManager manager = EntityManagerHelper.getEntityManager();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        Ticket existingTicket = null;
        try {
            existingTicket = manager.createQuery("Select a From Ticket a where a.id = " + ticketId, Ticket.class).getSingleResult();
        } catch (NoResultException e) {
            tx.rollback();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Update the existing ticket with the new values
        existingTicket.setTitre(updatedTicket.getTitre());
        existingTicket.setTags(updatedTicket.getTags());
        existingTicket.setStatut(updatedTicket.getStatut());

        if (existingTicket.getDiscussion() != null) {
            existingTicket.setDiscussion(manager.merge(existingTicket.getDiscussion()));
        }

        existingTicket = manager.merge(existingTicket); // Merge the detached entity back into the current session
        manager.persist(existingTicket);
        tx.commit();

        return Response.ok(existingTicket).build();
    }

}
