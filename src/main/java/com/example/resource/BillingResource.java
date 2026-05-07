package com.example.resource;

import com.example.dao.BillingDAO;
import com.example.model.Billing;
import com.example.exception.UserNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/billings")
public class BillingResource {

    private static final Logger LOGGER = Logger.getLogger(BillingResource.class.getName());
    private BillingDAO billingDAO = new BillingDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBillings() {
        try {
            List<Billing> billings = billingDAO.getAllBillings();
            LOGGER.log(Level.INFO, "Successfully retrieved all billing records.");
            return Response.ok().entity(billings).build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Unable to retrieve billing records.", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Unable to retrieve billing records. Please try again later.").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBillingById(@PathParam("id") int id) {
        try {
            Billing billing = billingDAO.getBilling(id);
            if (billing != null) {
                LOGGER.log(Level.INFO, "Successfully retrieved billing with ID {0}.", id);
                return Response.ok().entity(billing).build();
            } else {
                throw new UserNotFoundException("Sorry, but there's no billing record associated with ID " + id + ".");
            }
        } catch (UserNotFoundException ex) {
            LOGGER.log(Level.WARNING, "Sorry, but there's no billing record associated with ID {0}", id);
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to retrieve billing with ID " + id, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Sorry, but there's no billing record associated with ID " + id + ". Please try again later.").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBilling(Billing billing) {
        try {
            billingDAO.addBilling(billing);
            LOGGER.log(Level.INFO, "Billing record added successfully");
            return Response.status(Response.Status.CREATED).entity("Billing addition successful.").build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Unable to add billing record", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Unable to add billing record. Please try again later.").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBilling(@PathParam("id") int billingId, Billing updatedBilling) {
        try {
            Billing existingBilling = billingDAO.getBilling(billingId);
            if (existingBilling != null) {
                updatedBilling.setNo(billingId);
                billingDAO.updateBilling(updatedBilling);
                LOGGER.log(Level.INFO, "Updated billing details for ID {0} without any issues.", billingId);
                return Response.ok().entity("Billing with ID " + billingId + " successfully updated").build();
            } else {
                throw new UserNotFoundException("Unable to find billing with ID " + billingId);
            }
        } catch (UserNotFoundException ex) {
            LOGGER.log(Level.WARNING, "Unable to find billing with ID {0}.", billingId);
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,  "Update of billing with ID unsuccessfull" + billingId, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Update of billing with ID unsuccessfull " + billingId + ". Please try again later.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBilling(@PathParam("id") int id) {
        try {
            billingDAO.deleteBills(id);
            LOGGER.log(Level.INFO, " billing with ID {0} deleted successfully", id);
            return Response.ok().entity("Billing with ID " + id + " successfully deleted.").build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Deletion of billing with ID " + id + "unsuccessfull.", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Deletion of billing with ID " + id + ". Error: " + ex.getMessage()).build();
        }
    }
}
