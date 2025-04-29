package com.ironhack.IntroToSpringBoot.services;

import com.ironhack.IntroToSpringBoot.models.Patient;
import com.ironhack.IntroToSpringBoot.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient updatePatient(int id, Patient patient) {
        Patient existingPatient = patientRepository.findByPatientId(id);
        if (existingPatient != null) {
            existingPatient.setName(patient.getName());
            existingPatient.setDateOfBirth(patient.getDateOfBirth());
            existingPatient.setAdmittedBy(patient.getAdmittedBy());
            return patientRepository.save(existingPatient);
        } else {
            throw new RuntimeException("Patient not found");
        }
    }

}
