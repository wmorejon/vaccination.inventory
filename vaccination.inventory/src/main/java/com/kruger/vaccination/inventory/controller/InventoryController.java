package com.kruger.vaccination.inventory.controller;

import com.kruger.vaccination.inventory.domain.VaccinationStatus;
import com.kruger.vaccination.inventory.domain.VaccineType;
import com.kruger.vaccination.inventory.domain.credentials.EmployeeCredentials;
import com.kruger.vaccination.inventory.domain.dto.EmployeeDto;
import com.kruger.vaccination.inventory.service.InventoryService;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory/vaccination")
public class InventoryController {

  @Autowired
  InventoryService inventoryService;

  @PostMapping("/employee")
  @ResponseStatus(HttpStatus.CREATED)
  public EmployeeCredentials postEmployee(@RequestBody @Valid EmployeeDto employeeDto) {

    return inventoryService.createEmployee(employeeDto);
  }

  @GetMapping("/employee/{dni}")
  @ResponseStatus(HttpStatus.OK)
  public EmployeeDto getEmployee(@PathVariable String dni) {

    return inventoryService.findEmployee(dni);
  }

  @DeleteMapping("/employee/{dni}")
  @ResponseStatus(HttpStatus.OK)
  public String deleteEmployee(@PathVariable String dni) {

    return inventoryService.deleteEmployee(dni);
  }

  @PatchMapping("/employee/{dni}")
  @ResponseStatus(HttpStatus.OK)
  public EmployeeDto patchEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable String dni) {

    return inventoryService.editEmployee(employeeDto, dni);
  }

  @GetMapping("/employee/vaccination-status")
  @ResponseStatus(HttpStatus.OK)
  public List<EmployeeDto> getByVaccinationStatus(
      @RequestParam VaccinationStatus vaccinationStatus) {

    return inventoryService.filterByVaccinationStatus(vaccinationStatus);
  }

  @GetMapping("/employee/vaccine-type")
  @ResponseStatus(HttpStatus.OK)
  public List<EmployeeDto> getByVaccineType(
          @RequestParam VaccineType vaccineType) {

    return inventoryService.filterByVaccineType(vaccineType);
  }

  /*
  @GetMapping("/employee/vaccination-date-range")
  @ResponseStatus(HttpStatus.OK)
  public List<EmployeeDto> getByVaccinationDateRange(
          @RequestParam Date startRange, @RequestParam Date endRange) {

    return inventoryService.filterByVaccinationDateRange(Date startRange, Date endRange);
  }
   */
}
