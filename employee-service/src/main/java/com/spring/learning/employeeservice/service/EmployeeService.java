package com.spring.learning.employeeservice.service;

import com.spring.learning.employeeservice.dto.APIResponse;
import com.spring.learning.employeeservice.dto.DepartmentDto;
import com.spring.learning.employeeservice.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    List<APIResponse> getAllEmployee();

    APIResponse getEmployeeByEmail(String email);

    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    String deleteEmployeeById(Long id);

    String deleteEmployeeByEmail(String email);

    DepartmentDto getDepartmentByDepartmentCode(String departmentCode);
}
