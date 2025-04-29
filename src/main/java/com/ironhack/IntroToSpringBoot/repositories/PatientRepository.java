package com.ironhack.IntroToSpringBoot.repositories;

import com.ironhack.IntroToSpringBoot.models.Employee;
import com.ironhack.IntroToSpringBoot.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    List<Patient> findAll();

    Patient findByPatientId(int patientId);

    List<Patient> findByDateOfBirthBetween(Date from, Date to);

    List<Patient> findByAdmittedBy_DepartmentAndAdmittedBy(String admittedByDepartment, Employee admittedBy);

    List<Patient> findByAdmittedBy_Status(String status);
}