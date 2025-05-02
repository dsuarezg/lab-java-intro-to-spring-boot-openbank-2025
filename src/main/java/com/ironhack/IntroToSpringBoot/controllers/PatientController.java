package com.ironhack.IntroToSpringBoot.controllers;

import com.ironhack.IntroToSpringBoot.exceptions.PatientNotFoundException;
import com.ironhack.IntroToSpringBoot.models.Employee;
import com.ironhack.IntroToSpringBoot.models.Patient;
import com.ironhack.IntroToSpringBoot.repositories.EmployeeRepository;
import com.ironhack.IntroToSpringBoot.repositories.PatientRepository;
import com.ironhack.IntroToSpringBoot.services.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private PatientService patientService;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all patients")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Patients retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Patients not found")})
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get patient by ID")
    @Parameter(name = "id", description = "ID of the patient to retrieve")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Patient retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")})
    public Patient findByPatientId(@PathVariable int id) {
        return patientRepository.findByPatientId(id).orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));
    }

    @GetMapping("/date/{from}/{to}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get patients by date of birth range")
    @Parameter(name = "from", description = "Start date in 'yyyy-MM-dd' format")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Patients retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid date format")})
    public List<Patient> findByDateOfBirthBetween(@PathVariable String from, @PathVariable String to) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fromDate = dateFormat.parse(from);
            Date toDate = dateFormat.parse(to);
            return patientRepository.findByDateOfBirthBetween(fromDate, toDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("The date format must be yyyy-MM-dd.");
        }
    }

    @GetMapping("/{admittedByDepartment}/{admittedBy}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get patients by admitted by department and employee ID")
    @Parameter(name = "admittedByDepartment", description = "Department of the employee who admitted the patient")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Patients retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Patients not found")})
    public List<Patient> findByAdmittedByDepartmentAndAdmittedBy(@PathVariable String admittedByDepartment, @PathVariable int admittedBy) {
        Employee employee = employeeRepository.findByEmployeeId(admittedBy).orElseThrow(() -> new RuntimeException("Employee not found"));
        return patientRepository.findByAdmittedBy_DepartmentAndAdmittedBy(admittedByDepartment, employee);
    }

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get patients admitted by status = 'OFF'")
    @Parameter(name = "status", description = "Status of the employee who admitted the patient ('OFF')")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Patients retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Patients not found")})
    public List<Patient> findByAdmittedByStatusAndAdmittedBy() {
        String statusOFF = "OFF";
        return patientRepository.findByAdmittedBy_Status(statusOFF);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new patient")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Patient created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid patient data")})
    public Patient createPatient(@RequestBody @Valid Patient patient) {
        return patientRepository.save(patient);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a patient")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")})
    public Optional<Patient> updatePatient(@PathVariable int id, @RequestBody @Valid Patient patient) {
      return patientService.updatePatient(id, patient);
    }

}
