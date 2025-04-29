package com.ironhack.IntroToSpringBoot.controllers;

import com.ironhack.IntroToSpringBoot.dtos.EmployeePatchDTO;
import com.ironhack.IntroToSpringBoot.models.Employee;
import com.ironhack.IntroToSpringBoot.repositories.EmployeeRepository;
import com.ironhack.IntroToSpringBoot.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

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
        return employeeRepository.findByEmployeeId(id).orElseThrow(() -> new RuntimeException("Employee not found"));
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new employee")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Employee created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid employee data")})
    public Employee createEmployee(@RequestBody @Valid Employee employee) {
        return employeeRepository.save(employee);
    }

    @PatchMapping("/status/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update employee status")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Employee status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Employee not found")})
    public Employee updateEmployeeStatus(@PathVariable int id, @RequestBody EmployeePatchDTO employeeDTO) {
        if (employeeDTO == null || employeeDTO.getStatus() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status is required for this operation.");
        }
        return employeeService.patchEmployeeStatus(id, employeeDTO);
    }

    @PatchMapping("/department/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update employee department")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Employee department updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Employee not found")})
    public Employee updateEmployeeDepartment(@PathVariable int id, @RequestBody EmployeePatchDTO employeeDTO) {
        if (employeeDTO == null || employeeDTO.getDepartment() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department is required for this operation.");
        }
        return employeeService.patchEmployeeDepartment(id, employeeDTO);
    }
}
