/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.PrescriptionDAO;
import com.example.model.Prescription;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/prescriptions")

public class PrescriptionResource {

    private static final Logger LOGGER = Logger.getLogger(PrescriptionResource.class.getName());
    
    private PrescriptionDAO prescriptionDAO = new PrescriptionDAO();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Prescription> getAllPrescriptions() {
        return prescriptionDAO.getAllPrescriptions();
    }
    
    
    @GET
    @Path("/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrescription(@PathParam("patientId") int id) {
        try {
            Prescription prescription = prescriptionDAO.getPrescription(id);
            if (prescription != null) {
                LOGGER.log(Level.INFO, "Successfully retrieved prescription for patient ID: {0}.", id);
                return Response.ok().entity(prescription).build();
            } else {
                LOGGER.log(Level.INFO, "Unable to locate any prescriptions for patient ID: " + id + ".");
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Unable to locate any prescriptions for patient ID: " + id + ".").build();
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to fetch prescription for patient ID: " + id, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to fetch prescription for patient ID: " + id + ". Please try again later.").build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPrescription(Prescription prescription) {
        try {
            prescriptionDAO.addPrescription(prescription);
            LOGGER.log(Level.INFO, "Added new prescription for patient ID: {0}", prescription.getPatient().getID());
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to add prescription", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to add prescription. Please try again later.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletePrescription(@PathParam("id") int id) {
        try {
            prescriptionDAO.deletePrescription(id);
            LOGGER.log(Level.INFO, "Deleted prescription with ID: {0}", id);
            return Response.ok().build();
        } catch (NotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Failed to delete prescription with ID: " + id, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to delete prescription with ID: " + id + ". Please try again later.").build();
        }
    }
}

