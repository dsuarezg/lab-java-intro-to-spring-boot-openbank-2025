package com.ironhack.IntroToSpringBoot.services;

import com.ironhack.IntroToSpringBoot.models.Patient;
import com.ironhack.IntroToSpringBoot.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Optional<Patient> updatePatient(int id, Patient patient) {
        Patient existingPatient = patientRepository.findByPatientId(id).isPresent()
                ? patientRepository.findByPatientId(id).get()
                : null;;
        if (existingPatient != null) {
            existingPatient.setName(patient.getName());
            existingPatient.setDateOfBirth(patient.getDateOfBirth());
            existingPatient.setAdmittedBy(patient.getAdmittedBy());
            return Optional.of(patientRepository.save(existingPatient));
        } else {
            throw new RuntimeException("Patient not found");
        }
    }

}
