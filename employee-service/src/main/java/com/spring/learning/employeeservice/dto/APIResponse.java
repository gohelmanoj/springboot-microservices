package com.spring.learning.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {

    private EmployeeDto employee;
    private DepartmentDto department;
    private OrganizationDto organization;
}
