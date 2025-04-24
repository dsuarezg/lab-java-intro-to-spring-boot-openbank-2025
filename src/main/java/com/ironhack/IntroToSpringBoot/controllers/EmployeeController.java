package com.ironhack.IntroToSpringBoot.controllers;

import com.ironhack.IntroToSpringBoot.models.Employee;
import com.ironhack.IntroToSpringBoot.repositories.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all employees")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Employees retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employees not found")})
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get employee by ID")
    @Parameter(name = "id", description = "ID of the employee to retrieve")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Employee retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")})
    public Employee findByEmployeeId(@PathVariable int id) {
        return employeeRepository.findByEmployeeId(id);
    }

    @GetMapping("/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get employees by status")
    @Parameter(name = "status", description = "Status of the employees to retrieve")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Employees retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employees not found")})
    public List<Employee> findByStatus(@PathVariable String status) {
        return employeeRepository.findByStatus(status);
    }

    @GetMapping("/department/{department}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get employees by department")
    @Parameter(name = "department", description = "Department of the employees to retrieve")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Employees retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employees not found")})
    public List<Employee> findByDepartment(@PathVariable String department) {
        return employeeRepository.findByDepartment(department);
    }
}
