package com.ironhack.IntroToSpringBoot.repositories;

import com.ironhack.IntroToSpringBoot.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAll();

    Employee findByEmployeeId(int employeeId);

    List<Employee> findByStatus(String status);

    List<Employee> findByDepartment(String department);


}
