package com.ironhack.IntroToSpringBoot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.IntroToSpringBoot.models.Employee;
import com.ironhack.IntroToSpringBoot.models.Patient;
import com.ironhack.IntroToSpringBoot.repositories.EmployeeRepository;
import com.ironhack.IntroToSpringBoot.repositories.PatientRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
public class PatientControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void deletePatient() {
        // comprobar si existe
        Optional <Patient> patient = patientRepository.findByName("John Doe");
        System.out.println("EXISTE EL PACIENTE? " + patient.isPresent());
        // si existe borrarlo
        if (patient.isPresent()) {
            System.out.println("Vamos a borrarlo");
            patientRepository.deleteById(patient.get().getPatientId());
        }
    }


    @Test
    @DisplayName("POST /patient")
    public void createPatient() throws Exception {

        Employee employee = new Employee(155438, "cardiology", "Jane Doe", "ON");
        employeeRepository.save(employee);

        Patient patient = new Patient("John Doe", new Date(), employee);
        String patientJson = objectMapper.writeValueAsString(patient);

        MvcResult result = mockMvc.perform(post("/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(patientJson))
                .andExpect(status().isCreated())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();

        assertTrue(responseJson.contains("John Doe"));
    }




}
