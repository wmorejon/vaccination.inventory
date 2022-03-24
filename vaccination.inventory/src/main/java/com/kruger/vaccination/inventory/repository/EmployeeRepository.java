package com.kruger.vaccination.inventory.repository;

import com.kruger.vaccination.inventory.domain.Employee;
import com.kruger.vaccination.inventory.domain.VaccinationStatus;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.kruger.vaccination.inventory.domain.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Optional<Employee> findByDni(String dni);

  List<Employee> findByVaccinationStatus(VaccinationStatus vaccinationStatus);

  List<Employee> findByVaccineType(VaccineType vaccineType);

  //List<Employee> findByVaccinationDateRange(Date startRange, Date endRange);
}
