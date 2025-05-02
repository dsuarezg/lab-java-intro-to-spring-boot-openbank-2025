package com.ironhack.IntroToSpringBoot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.IntroToSpringBoot.models.Employee;
import com.ironhack.IntroToSpringBoot.models.Patient;
import com.ironhack.IntroToSpringBoot.repositories.EmployeeRepository;
import com.ironhack.IntroToSpringBoot.repositories.PatientRepository;
import org.junit.jupiter.api.*;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PatientControllerTest {

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
        Optional<Patient> patient = patientRepository.findByName("John Doe");
        System.out.println("EXISTE EL PACIENTE? " + patient.isPresent());
        // si existe borrarlo
        if (patient.isPresent()) {
            System.out.println("Vamos a borrarlo");
            patientRepository.deleteById(patient.get().getPatientId());
        }
    }

    @Test
    @DisplayName("GET /patient")
    public void getAllPatients() throws Exception {
        Employee employee = new Employee(155438, "cardiology", "Jane Doe", "ON");
        employeeRepository.save(employee);

        Patient patient1 = new Patient("John Doe", new Date(), employee);
        Patient patient2 = new Patient("Jack Smith", new Date(), employee);
        patientRepository.save(patient1);
        patientRepository.save(patient2);

        MvcResult result = mockMvc.perform(get("/patient")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        assertTrue(responseJson.contains("John Doe"));
        assertTrue(responseJson.contains("Smith"));
    }

    @Test
    @DisplayName("GET /patient/{id}")
    public void getPatientById() throws Exception {
        Employee employee = new Employee(155438, "cardiology", "Jane Doe", "ON");
        employeeRepository.save(employee);

        Patient patient = new Patient("John Doe", new Date(), employee);
        patientRepository.save(patient);

        MvcResult result = mockMvc.perform(get("/patient/" + patient.getPatientId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        assertTrue(responseJson.contains("John Doe"));
    }

    @Test
    @DisplayName("GET /patient/date/{date}/{date}")
    public void findByDateOfBirthBetween() throws Exception {

        MvcResult result = mockMvc.perform(get("/patient/date/1950-01-01/1990-01-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        assertTrue(responseJson.contains("Dusterdieck"));
        assertTrue(responseJson.contains("Garcia"));
        assertTrue(responseJson.contains("Jordán"));
    }

    @Test
    @DisplayName("GET /patient/{admittedByDepartment}/{admittedBy}")
    public void findByAdmittedByDepartmentAndAdmittedBy() throws Exception {

        MvcResult result = mockMvc.perform(get("/patient/cardiology/156545")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        assertTrue(responseJson.contains("Paquito"));
        assertTrue(responseJson.contains("Carlos"));
    }

    @Test
    @DisplayName("GET /patient/status")
    void findByAdmittedByStatusAndAdmittedBy() throws Exception {

        MvcResult result = mockMvc.perform(get("/patient/status")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        assertTrue(responseJson.contains("Garcia"));
        assertTrue(responseJson.contains("Steve"));
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

    @Test
    @DisplayName("PUT /patient/{id}")
    void updatePatient() throws Exception {

        Employee employee = employeeRepository.findByEmployeeId(155438).orElseThrow(() -> new RuntimeException("Employee not found"));

        Patient patient = new Patient("John Done", new Date(), employee);
        String patientJson = objectMapper.writeValueAsString(patient);

        MvcResult result = mockMvc.perform(put("/patient/17")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientJson))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("John Done"));

    }
}