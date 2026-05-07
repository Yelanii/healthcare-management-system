/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.MedicalRecordDAO;
import com.example.model.Appointment;
import com.example.model.MedicalRecord;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/medical-records")

public class MedicalRecordResource {

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordResource.class);
    private MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordDAO.getAllMedicalRecords();
    }
    
    
    @GET
    @Path("/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MedicalRecord> getMedicalRecord(@PathParam("patientId") int id) {
            return medicalRecordDAO.getMedicalRecords(id);
         
    }
    
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMedicalRecord(MedicalRecord medicalRecord) {
        try {
            medicalRecordDAO.addMedicalRecord(medicalRecord);
            logger.info("Medical record added successfully: " + medicalRecord.getId());
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception ex) {
           
            throw new WebApplicationException("An error occurred while adding the medical record. Please try again later.", ex);
        }
    }
    
    
    @DELETE
    @Path("/{ID}")
    public void deleteStudent(@PathParam("ID") int id) {
        try {
            medicalRecordDAO.deleteMedicalRecord(id);
        } catch (Exception ex) {
            logger.error("Failed to delete the record with ID {}: {} due to an error.", id, ex.getMessage());
            throw new WebApplicationException("Failed to delete record with ID " + id + ". Please try again later.", ex);
        }
    }

 
}

