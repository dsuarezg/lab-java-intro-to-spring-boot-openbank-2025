package com.ironhack.IntroToSpringBoot.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "department")
    private String department;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    public Employee() {
    }

    public Employee(int employeeId, String department, String name, String status) {
        setEmployeeId(employeeId);
        setDepartment(department);
        setName(name);
        setStatus(status);
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Employee {\n" +
                "    employeeId=" + employeeId + ",\n" +
                "    department='" + department + "',\n" +
                "    name='" + name + "',\n" +
                "    status='" + status + "'\n" +
                "}";
    }
}
