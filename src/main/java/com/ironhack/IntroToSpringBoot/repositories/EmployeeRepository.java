package com.ironhack.IntroToSpringBoot.repositories;

import com.ironhack.IntroToSpringBoot.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findAll();

    Optional<Employee> findByEmployeeId(int employeeId);

    List<Employee> findByStatus(String status);

    List<Employee> findByDepartment(String department);


}
