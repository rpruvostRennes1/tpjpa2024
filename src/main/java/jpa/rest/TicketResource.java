package jpa.rest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jpa.dao.generic.EntityManagerHelper;
import jpa.domain.Ticket;


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
                .header("Access-Control-Allow-Origin", "http://localhost:4200")
                .build();
    }
    // Get ticket by id
    @GET
    @Path("/{ticketId}")
    public Ticket getTicket(@PathParam("ticketId") String ticketId)  {
        // Start the entity manager
        EntityManager manager = EntityManagerHelper.getEntityManager();

        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        // Get the ticket by id using a query
        Ticket ticket = manager.createQuery("Select a From Ticket a where a.id = " + ticketId, Ticket.class).getSingleResult();
        tx.commit();
        return ticket;
    }
    // Add another path to adding a ticket
    @POST
    @Path("/add")
    public Response addTicket(Ticket ticket)  {
        // Start the entity manager
        EntityManager manager = EntityManagerHelper.getEntityManager();

        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        // If the username is not in the database, create a new user
        manager.persist(ticket);
        tx.commit();
        manager.close();


        return Response.ok(ticket)
                .header("Access-Control-Allow-Origin", "http://localhost:4200")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
                .build();
    }
}
