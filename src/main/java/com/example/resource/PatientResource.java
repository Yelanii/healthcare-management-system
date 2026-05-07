package com.example.resource;

import com.example.dao.PatientDAO;
import com.example.model.Patient;
import com.example.exception.UserNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/patients")
public class PatientResource {

    private static final Logger LOGGER = Logger.getLogger(PatientResource.class.getName());
    private PatientDAO patientDAO = new PatientDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPatients() {
        try {
            List<Patient> patients = patientDAO.getAllPatients();
            LOGGER.log(Level.INFO, "Retrieved all patient data.");
            return Response.ok().entity(patients).build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to retrieve patients' data", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to retrieve patients' data. Please retry later.").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientById(@PathParam("id") int id) {
        try {
            Patient patient = patientDAO.getPatientById(id);
            if (patient != null) {
                LOGGER.log(Level.INFO, "Patient with ID {0} accessed successfully.", id);
                return Response.ok().entity(patient).build();
            } else {
                throw new UserNotFoundException("Patient with ID " + id + " not found.");
            }
        } catch (UserNotFoundException ex) {
            LOGGER.log(Level.WARNING, "Patient with ID {0} not found", id);
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to access patient with ID " + id, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to access patient with ID " + id + ". Please retry later.").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPatient(Patient patient) {
        try {
            patientDAO.addPatient(patient);
            LOGGER.log(Level.INFO, "Added patient successfully");
            return Response.status(Response.Status.CREATED).entity("Patient added successfully.").build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Encountered issue while adding patient", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Encountered issue while adding patient. Please try again later.").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePatient(@PathParam("id") int patientId, Patient updatePatient) {
        try {
            Patient existingPatient = patientDAO.getPatientById(patientId);
            if (existingPatient != null) {
                updatePatient.setID(patientId);
                patientDAO.updatePatient(updatePatient);
                LOGGER.log(Level.INFO, "Updated patient with ID {0}.", patientId);
                return Response.ok().entity("Patient with ID " + patientId + " updated").build();
            } else {
                throw new UserNotFoundException("Patient with ID " + patientId + " not found.");
            }
        } catch (UserNotFoundException ex) {
            LOGGER.log(Level.WARNING, "Patient with ID {0} not found", patientId);
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to update patient with ID " + patientId, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to update patient with ID " + patientId + ". Please try again later.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletePatient(@PathParam("id") int id) {
        try {
            patientDAO.deletePatient(id);
            LOGGER.log(Level.INFO, "Deleted patient with ID {0}.", id);
            return Response.ok().entity("Patient with ID " + id + " successfully deleted.").build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to delete patient with ID " + id, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to delete patient with ID " + id + ". Error: " + ex.getMessage()).build();
        }
    }
}
