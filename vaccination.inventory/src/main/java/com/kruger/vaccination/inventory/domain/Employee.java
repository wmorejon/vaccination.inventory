package com.kruger.vaccination.inventory.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.jdi.IntegerType;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "employee_id", nullable = false)
  private Long employeeId;

  @Column(nullable = false)
  private String dni;

  @Column(nullable = false)
  private String names;

  @Column(name = "last_names", nullable = false)
  private String lastNames;

  @Column(nullable = false)
  private String email;

  @Column(name = "vaccination_status")
  private VaccinationStatus vaccinationStatus;

  @Column(name = "date_birth")
  private Date dateBirth;

  private String address;

  private Integer phone;

  @Column(name = "vaccine_type")
  private VaccineType vaccineType;

  @Column(name = "vaccination_date")
  private Date vaccinationDate;

  @Column(name = "number_dose")
  private Integer numberDose;


}
