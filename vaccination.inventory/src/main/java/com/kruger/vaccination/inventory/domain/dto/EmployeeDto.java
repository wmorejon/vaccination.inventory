package com.kruger.vaccination.inventory.domain.dto;

import com.kruger.vaccination.inventory.domain.VaccinationStatus;
import com.kruger.vaccination.inventory.domain.VaccineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.Role;
import javax.persistence.Column;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeDto {

  private String dni;
  private String names;
  private String lastNames;
  private String email;
  private VaccinationStatus vaccinationStatus;
  private Date dateBirth;
  private String address;
  private Integer phone;
  private VaccineType vaccineType;
  private Date vaccinationDate;
  private Integer numberDose;

}
