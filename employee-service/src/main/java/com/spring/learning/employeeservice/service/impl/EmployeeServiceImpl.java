package com.spring.learning.employeeservice.service.impl;

import com.spring.learning.employeeservice.dto.APIResponse;
import com.spring.learning.employeeservice.dto.DepartmentDto;
import com.spring.learning.employeeservice.dto.EmployeeDto;
import com.spring.learning.employeeservice.entity.Employee;
import com.spring.learning.employeeservice.exception.EmailAlreadyExistException;
import com.spring.learning.employeeservice.exception.ResourceNotFoundException;
import com.spring.learning.employeeservice.repository.EmployeeRepository;
import com.spring.learning.employeeservice.service.APIClient;
import com.spring.learning.employeeservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    private ModelMapper modelMapper;

    private WebClient webClient;

    private APIClient apiClient;

    @Override
    public List<APIResponse> getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().map(employee -> {

            DepartmentDto departmentDto = null;
            if (employee.getDepartmentCode() == null || employee.getDepartmentCode().isEmpty()) {
                departmentDto = new DepartmentDto();
            } else {
                //departmentDto = getDepartmentByDepartmentCode(employee.getDepartmentCode());
                departmentDto = apiClient.getDepartmentByDepartmentCode(employee.getDepartmentCode());
            }
            EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
            return new APIResponse(employeeDto, departmentDto);
        }).toList();
    }

    @Override
    public APIResponse getEmployeeByEmail(String email) {
        Optional<Employee> employee = employeeRepository.findEmployeeByEmail(email);

        if (employee.isEmpty()) {
            throw new ResourceNotFoundException("Employee", "email", email);
        }

        DepartmentDto departmentDto = null;
        if (employee.get().getDepartmentCode() == null || employee.get().getDepartmentCode().isEmpty()) {
            departmentDto = new DepartmentDto();
        } else {
            //departmentDto = getDepartmentByDepartmentCode(employee.get().getDepartmentCode());
            departmentDto = apiClient.getDepartmentByDepartmentCode(employee.get().getDepartmentCode());
        }

        EmployeeDto employeeDto = modelMapper.map(employee.get(), EmployeeDto.class);

        return new APIResponse(employeeDto, departmentDto);
    }

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmail(employeeDto.getEmail());

        if (employeeOptional.isPresent()) {
            throw new EmailAlreadyExistException(employeeDto.getEmail());
        }

        Employee employee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    public String deleteEmployeeById(Long id) {

        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isPresent()) {
            employeeRepository.delete(employee.get());
            return "Employee deleted successfully by Id " + id;
        }
        throw new ResourceNotFoundException("Department", "id", String.valueOf(id));
    }

    @Override
    public String deleteEmployeeByEmail(String email) {

        Optional<Employee> employee = employeeRepository.findEmployeeByEmail(email);

        if (employee.isPresent()) {
            employeeRepository.delete(employee.get());
            return "Employee deleted successfully by email " + email;
        }
        throw new ResourceNotFoundException("Department", "email", email);
    }

    @Override
    public DepartmentDto getDepartmentByDepartmentCode(String departmentCode) {
        return webClient.get()
                .uri("http://localhost:8080/api/departments/" + departmentCode)
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();
    }
}
