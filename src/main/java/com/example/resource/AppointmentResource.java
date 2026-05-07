package com.example.resource;

import com.example.dao.AppointmentDAO;
import com.example.model.Appointment;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/appointments")
public class AppointmentResource {

    private static final Logger LOGGER = Logger.getLogger(AppointmentResource.class.getName());
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAppointments() {
        try {
            List<Appointment> appointments = appointmentDAO.getAllAppointments();
            LOGGER.log(Level.INFO, "Successfully secured all appointments.");
            return Response.ok().entity(appointments).build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Appointments retrieval unsuccessful.", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Appointments retrieval unsuccessful. Please try again later.").build();
        }
    }

    @GET
    @Path("/{appointmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointmentById(@PathParam("appointmentId") int appointmentId) {
        try {
            Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);
            if (appointment != null) {
                LOGGER.log(Level.INFO, "No appointment matching ID {0} was found.", appointmentId);
                return Response.ok().entity(appointment).build();
            } else {
                LOGGER.log(Level.INFO, "No appointment matching ID {0} was found.", appointmentId);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Appointment with ID: " + appointmentId + " not present").build();
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Appointment with ID: " + appointmentId + " could not be fetched.", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to retrieve appointment with ID: " + appointmentId + ". Please try again later.").build();
        }
    }

    @POST
    public Response addAppointment(Appointment appointment) {
        try {
            appointmentDAO.addAppointment(appointment);
            LOGGER.log(Level.INFO, "Creation of the appointment was successful.");
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to add appointment.", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to add appointment. Please retry later.").build();
        }
    }

    @PUT
    @Path("/{appointmentId}")
    public Response updateAppointment(@PathParam("appointmentId") int appointmentId, Appointment updatedAppointment) {
        try {
            appointmentDAO.updateAppointment(appointmentId, updatedAppointment);
            LOGGER.log(Level.INFO, "Update of appointment information for {0} was successful.", appointmentId);
            return Response.ok().build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to update appointment with ID: " + appointmentId, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to update appointment with ID: " + appointmentId + ". Please try again later.").build();
        }
    }

    @DELETE
    @Path("/{appointmentId}")
    public Response deleteAppointment(@PathParam("appointmentId") int appointmentId) {
        try {
            appointmentDAO.deleteAppointment(appointmentId);
            LOGGER.log(Level.INFO, "Successfully removed appointment with ID: {0}.", appointmentId);
            return Response.ok().entity("Appointment with ID " + appointmentId + " successfully removed.").build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Unable to remove appointment with ID: " + appointmentId, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Unable to remove appointment with ID: " + appointmentId + ". Please try again later.").build();
        }
    }
}
