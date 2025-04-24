package com.ironhack.IntroToSpringBoot.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private int patientId;

    @Column(name= "name")
    private String name;

    @Column(name= "date_of_birth")
    @Temporal(TemporalType.DATE) // Especifica que solo se almacenará la fecha (sin tiempo).
    private Date dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "admitted_by", referencedColumnName = "employee_id")
    private Employee admittedBy;

    public Patient() {
    }

    public Patient(String name, Date dateOfBirth, Employee admittedBy) {
        setName(name);
        setDateOfBirth(dateOfBirth);
        setAdmittedBy(admittedBy);
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Employee getAdmittedBy() {
        return admittedBy;
    }

    public void setAdmittedBy(Employee admittedBy) {
        this.admittedBy = admittedBy;
    }
}
