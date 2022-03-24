package com.kruger.vaccination.inventory.service;

import com.kruger.vaccination.inventory.domain.Employee;
import com.kruger.vaccination.inventory.domain.VaccinationStatus;
import com.kruger.vaccination.inventory.domain.VaccineType;
import com.kruger.vaccination.inventory.domain.credentials.EmployeeCredentials;
import com.kruger.vaccination.inventory.domain.dto.EmployeeDto;
import com.kruger.vaccination.inventory.repository.EmployeeRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InventoryService {

  @Autowired
  EmployeeRepository employeeRepository;
  String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

  public EmployeeCredentials createEmployee(EmployeeDto employeeDto) {
    EmployeeCredentials employeeCred = new EmployeeCredentials();
    try{
      Employee employee = new Employee();
      if(validadorDeCedula(employeeDto.getDni())){
        employee.setDni(employeeDto.getDni());
      }
      employee.setNames(employeeDto.getNames());
      employee.setLastNames(employeeDto.getLastNames());
      employee.setEmail(employeeDto.getEmail());

      if(patternMatches(employee.getEmail(),regex)){
        Employee savedEmployee = employeeRepository.save(employee);

        employeeCred = EmployeeCredentials.builder()
                .userName(savedEmployee.getDni())
                .password(savedEmployee.getEmail() + savedEmployee.getDni())
                .build();
      } else {
        System.out.println("El correo no es valido");
      }
    } catch (Exception err) {
      System.out.println("Una excepcion ocurrio en el proceso de validadcion");
    }
    return employeeCred;
  }

  public EmployeeDto findEmployee(String dni) {
    return employeeRepository.findByDni(dni)
        .map(employee -> EmployeeDto.builder()
            .dni(employee.getDni())
            .names(employee.getNames())
            .lastNames(employee.getLastNames())
            .email(employee.getEmail())
            .build())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public String deleteEmployee(String dni) {
    Employee employee = employeeRepository.findByDni(dni)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    employeeRepository.delete(employee);

    return "OK";
  }

  public EmployeeDto editEmployee(EmployeeDto employeeDto, String dni) {
    Employee employee = employeeRepository.findByDni(dni)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (Objects.nonNull(employeeDto.getNames())) {
      employee.setNames(employeeDto.getNames());
    }

    if (Objects.nonNull(employeeDto.getLastNames())) {
      employee.setLastNames(employeeDto.getLastNames());
    }

    if (Objects.nonNull(employeeDto.getEmail())) {
      employee.setEmail(employeeDto.getEmail());
    }

    if (Objects.nonNull(employeeDto.getDateBirth())) {
      employee.setDateBirth(employeeDto.getDateBirth());
    }

    if (Objects.nonNull(employeeDto.getAddress())) {
      employee.setAddress(employeeDto.getAddress());
    }

    if (Objects.nonNull(employeeDto.getPhone())) {
      employee.setPhone(employeeDto.getPhone());
    }

    if (Objects.nonNull(employeeDto.getVaccinationStatus())) {
      employee.setVaccinationStatus(employeeDto.getVaccinationStatus());
    }

    if (Objects.nonNull(employeeDto.getVaccineType())) {
      employee.setVaccineType(employeeDto.getVaccineType());
    }

    if (Objects.nonNull(employeeDto.getVaccinationDate())) {
      employee.setVaccinationDate(employeeDto.getVaccinationDate());
    }

    if (Objects.nonNull(employeeDto.getNumberDose())) {
      employee.setNumberDose(employeeDto.getNumberDose());
    }

    employeeRepository.save(employee);

    return EmployeeDto.builder()
        .dni(employee.getDni())
        .names(employee.getNames())
        .lastNames(employee.getLastNames())
        .email(employee.getEmail())
        .build();
  }

  public List<EmployeeDto> filterByVaccinationStatus(VaccinationStatus vaccinationStatus) {
    return employeeRepository.findByVaccinationStatus(vaccinationStatus)
        .stream().map(employee -> EmployeeDto.builder()
            .dni(employee.getDni())
            .names(employee.getNames())
            .lastNames(employee.getLastNames())
            .email(employee.getEmail())
            .vaccinationStatus(employee.getVaccinationStatus())
            .build())
        .collect(Collectors.toList());
  }

  public List<EmployeeDto> filterByVaccineType(VaccineType vaccineType) {
    return employeeRepository.findByVaccineType(vaccineType)
            .stream().map(employee -> EmployeeDto.builder()
                    .dni(employee.getDni())
                    .names(employee.getNames())
                    .lastNames(employee.getLastNames())
                    .email(employee.getEmail())
                    .vaccinationStatus(employee.getVaccinationStatus())
                    .vaccineType(employee.getVaccineType())
                    .vaccinationDate(employee.getVaccinationDate())
                    .numberDose(employee.getNumberDose())
                    .build())
            .collect(Collectors.toList());
  }

  public static boolean patternMatches(String emailAddress, String regexPattern) {
    return Pattern.compile(regexPattern)
            .matcher(emailAddress)
            .matches();
  }
  /*
  public List<EmployeeDto> filterByVaccinationDateRange(Date startRange, Date endRange) {
    return employeeRepository.findByVaccinationDateRange(startRange, endRange)
            .stream().map(employee -> EmployeeDto.builder()
                    .dni(employee.getDni())
                    .names(employee.getNames())
                    .lastNames(employee.getLastNames())
                    .email(employee.getEmail())
                    .vaccinationStatus(employee.getVaccinationStatus())
                    .vaccineType(employee.getVaccineType())
                    .vaccinationDate(employee.getVaccinationDate())
                    .numberDose(employee.getNumberDose())
                    .build())
            .collect(Collectors.toList());
  }
  */

  public boolean validadorDeCedula(String cedula) {
    boolean cedulaCorrecta = false;

    try {

      if (cedula.length() == 10) // ConstantesApp.LongitudCedula
      {
        int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
        if (tercerDigito < 6) {
// Coeficientes de validación cédula
// El decimo digito se lo considera dígito verificador
          int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
          int verificador = Integer.parseInt(cedula.substring(9,10));
          int suma = 0;
          int digito = 0;
          for (int i = 0; i < (cedula.length() - 1); i++) {
            digito = Integer.parseInt(cedula.substring(i, i + 1))* coefValCedula[i];
            suma += ((digito % 10) + (digito / 10));
          }

          if ((suma % 10 == 0) && (suma % 10 == verificador)) {
            cedulaCorrecta = true;
          }
          else if ((10 - (suma % 10)) == verificador) {
            cedulaCorrecta = true;
          } else {
            cedulaCorrecta = false;
          }
        } else {
          cedulaCorrecta = false;
        }
      } else {
        cedulaCorrecta = false;
      }
    } catch (NumberFormatException nfe) {
      cedulaCorrecta = false;
    } catch (Exception err) {
      System.out.println("Una excepcion ocurrio en el proceso de validadcion");
      cedulaCorrecta = false;
    }

    if (!cedulaCorrecta) {
      System.out.println("La Cédula ingresada es Incorrecta");
    }
    return cedulaCorrecta;
  }
}
