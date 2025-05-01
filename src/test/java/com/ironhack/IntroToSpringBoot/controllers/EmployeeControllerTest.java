package com.ironhack.IntroToSpringBoot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.IntroToSpringBoot.models.Employee;
import com.ironhack.IntroToSpringBoot.repositories.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class EmployeeControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private EmployeeRepository employeeRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void deleteEmployee() {
        // comprobar si existe
        Optional<Employee> employee = employeeRepository.findByName("John Doe");
        System.out.println("EXISTE EL EMPLOYEE? " + employee.isPresent());
        // si existe borrarlo
        if (employee.isPresent()) {
            System.out.println("Vamos a borrarlo");
            employeeRepository.deleteById(employee.get().getEmployeeId());
        }
    }

    @Test
    @DisplayName("GET /employee/{id}")
    public void getEmployeeById() throws Exception {
        Employee employee = new Employee(155438, "cardiology", "Jane Doe", "ON");
        employeeRepository.save(employee);

        MvcResult result = mockMvc.perform(get("/employee/" + employee.getEmployeeId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        assertTrue(responseJson.contains("Jane Doe"));
        assertTrue(responseJson.contains("cardiology"));
        assertTrue(responseJson.contains("ON"));
    }

    @Test
    @DisplayName("GET /employees")
    public void getAllEmployees() throws Exception {
        Employee employee1 = new Employee(155438, "cardiology", "Jane Doe", "ON");
        Employee employee2 = new Employee(155439, "immunology", "James Doe", "OFF");
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        MvcResult result = mockMvc.perform(get("/employee")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        assertTrue(responseJson.contains("Jane Doe"));
        assertTrue(responseJson.contains("James Doe"));
        assertTrue(responseJson.contains("cardiology"));
        assertTrue(responseJson.contains("immunology"));
    }

    @Test
    @DisplayName("POST /employee")
    public void createEmployee() throws Exception {

        Employee employee = new Employee(155438, "cardiology", "Jane Doe", "ON");

        String employeeJson = objectMapper.writeValueAsString(employee);

        MvcResult result = mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isCreated())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();

        assertTrue(responseJson.contains("Jane Doe"));
    }

    @Test
    @DisplayName("PATCH /employee/department")
    public void updateEmployeeDepartment() throws Exception {
        Employee employee = new Employee(166552, "cardiology", "Maria Lin", "ON");
        String employeeJson = objectMapper.writeValueAsString(employee);

        MvcResult result = mockMvc.perform(patch("/employee/department/166552")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("cardiology"));
    }

    @Test
    @DisplayName("PATCH /employee/status")
    public void updateEmployeeStatus() throws Exception {
        Employee employee = new Employee(166552, "cardiology", "Maria Lin", "ON_CALL");
        String employeeJson = objectMapper.writeValueAsString(employee);

        MvcResult result = mockMvc.perform(patch("/employee/status/166552")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("CALL"));
    }

}
