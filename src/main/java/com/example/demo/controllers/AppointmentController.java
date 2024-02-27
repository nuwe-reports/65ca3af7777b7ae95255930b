package com.example.demo.controllers;

import com.example.demo.repositories.*;
import com.example.demo.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class AppointmentController {

    @Autowired
    AppointmentRepository appointmentRepository;

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointments(){
        List<Appointment> appointments = new ArrayList<>();

        appointmentRepository.findAll().forEach(appointments::add);

        if (appointments.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable("id") long id){
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (appointment.isPresent()){
            return new ResponseEntity<>(appointment.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/appointment")
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
        // Checks that the start time is before the finish time. Else, will return a "Bad Request" response.
        if (!appointment.getStartsAt().isBefore(appointment.getFinishesAt())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (isAppointmentOverlap(appointment)) {
            // Verifies any possible overlapping. If there is, it returns a "Not Acceptable" response.
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } else {
            // If everything is correct, it creates the appointment and returns an "OK" response.
            Appointment createdAppointment = appointmentRepository.save(appointment);
            return new ResponseEntity<>(createdAppointment, HttpStatus.OK);
        }
    }

    // Check the possible overlapping between appointments by using the "Overlaps" method from the Appointment class.
    private boolean isAppointmentOverlap(Appointment newAppointment) {
        List<Appointment> existingAppointments = appointmentRepository.findAll();

        for (Appointment existingAppointment : existingAppointments) {
            if (existingAppointment.overlaps(newAppointment)) {
                return true;
            }
        }
        return false;
    }

    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<HttpStatus> deleteAppointment(@PathVariable("id") long id){

        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (!appointment.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        appointmentRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
        
    }

    @DeleteMapping("/appointments")
    public ResponseEntity<HttpStatus> deleteAllAppointments(){
        appointmentRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
