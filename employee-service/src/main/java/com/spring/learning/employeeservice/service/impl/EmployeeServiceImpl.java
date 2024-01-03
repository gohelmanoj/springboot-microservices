package com.spring.learning.employeeservice.service.impl;

import com.spring.learning.employeeservice.dto.EmployeeDto;
import com.spring.learning.employeeservice.entity.Employee;
import com.spring.learning.employeeservice.exception.EmailAlreadyExistException;
import com.spring.learning.employeeservice.exception.ResourceNotFoundException;
import com.spring.learning.employeeservice.repository.EmployeeRepository;
import com.spring.learning.employeeservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;

    ModelMapper modelMapper;

    @Override
    public List<EmployeeDto> getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().map(employee ->
                modelMapper.map(employee, EmployeeDto.class)
                ).toList();
    }

    @Override
    public EmployeeDto getEmployeeByEmail(String email) {
        Optional<Employee> employee = employeeRepository.findEmployeeByEmail(email);

        if (employee.isEmpty()) {
            throw new ResourceNotFoundException("Employee", "email", email);
        }

        return modelMapper.map(employee.get(), EmployeeDto.class);
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
}
