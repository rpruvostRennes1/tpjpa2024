package jpa.rest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpa.dao.generic.EntityManagerHelper;
import jpa.domain.Ticket;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("/ticket")
@Produces({"application/json", "application/xml"})
public class TicketResource {
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
}
