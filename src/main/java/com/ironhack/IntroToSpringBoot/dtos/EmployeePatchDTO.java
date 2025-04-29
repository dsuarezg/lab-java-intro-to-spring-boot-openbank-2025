package com.ironhack.IntroToSpringBoot.dtos;

public class EmployeePatchDTO {

    private String name;
    private String department;
    private String status;

    public EmployeePatchDTO() {
    }

    public EmployeePatchDTO(String name, String department, String status) {
        this.name = name;
        this.department = department;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EmployeePatchDTO {\n" +
                "    name='" + name + "',\n" +
                "    department='" + department + "',\n" +
                "    status='" + status + "'\n" +
                "}";
    }
}
