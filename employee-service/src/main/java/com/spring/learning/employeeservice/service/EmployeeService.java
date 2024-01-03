package com.spring.learning.employeeservice.service;

import com.spring.learning.employeeservice.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> getAllEmployee();

    EmployeeDto getEmployeeByEmail(String email);

    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    String deleteEmployeeById(Long id);

    String deleteEmployeeByEmail(String email);
}
