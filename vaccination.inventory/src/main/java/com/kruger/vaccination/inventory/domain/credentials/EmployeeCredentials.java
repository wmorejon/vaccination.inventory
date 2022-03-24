package com.kruger.vaccination.inventory.domain.credentials;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeCredentials {

  private String userName;
  private String password;

}
