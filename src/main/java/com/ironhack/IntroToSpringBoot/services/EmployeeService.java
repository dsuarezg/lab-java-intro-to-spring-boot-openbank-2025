package com.ironhack.IntroToSpringBoot.services;

import com.ironhack.IntroToSpringBoot.dtos.EmployeePatchDTO;
import com.ironhack.IntroToSpringBoot.models.Employee;
import com.ironhack.IntroToSpringBoot.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    public Employee patchEmployeeStatus(int id, EmployeePatchDTO employeeDTO) {
        if (employeeDTO.getStatus() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status is required for this operation.");
        }

        Employee existingEmployee = employeeRepository.findByEmployeeId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        existingEmployee.setStatus(employeeDTO.getStatus());
        return employeeRepository.save(existingEmployee);
    }

    public Employee patchEmployeeDepartment(int id, EmployeePatchDTO employeePatchDTO) {
        if (employeePatchDTO.getDepartment() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department is required for this operation.");
        }

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        existingEmployee.setDepartment(employeePatchDTO.getDepartment());
        return employeeRepository.save(existingEmployee);
    }


}
