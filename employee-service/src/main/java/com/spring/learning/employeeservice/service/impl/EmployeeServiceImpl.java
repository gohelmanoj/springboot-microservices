package com.spring.learning.employeeservice.service.impl;

import com.spring.learning.employeeservice.dto.APIResponse;
import com.spring.learning.employeeservice.dto.DepartmentDto;
import com.spring.learning.employeeservice.dto.EmployeeDto;
import com.spring.learning.employeeservice.dto.OrganizationDto;
import com.spring.learning.employeeservice.entity.Employee;
import com.spring.learning.employeeservice.exception.EmailAlreadyExistException;
import com.spring.learning.employeeservice.exception.ResourceNotFoundException;
import com.spring.learning.employeeservice.repository.EmployeeRepository;
import com.spring.learning.employeeservice.service.DepartmentAPIClient;
import com.spring.learning.employeeservice.service.EmployeeService;
import com.spring.learning.employeeservice.service.OrganizationApiClient;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private EmployeeRepository employeeRepository;

    private ModelMapper modelMapper;

    private WebClient webClient;

    private DepartmentAPIClient departmentAPIClient;

    private OrganizationApiClient organizationApiClient;

    @Override
    //@CircuitBreaker(name="${spring.application.name}", fallbackMethod="getDefaultDepartment")
    @Retry(name="${spring.application.name}", fallbackMethod="getDefaultDepartment")
    public List<APIResponse> getAllEmployee() {

        LOGGER.info("Inside GetAllEmployee .....");
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().map(employee -> {

            DepartmentDto departmentDto = null;
            if (employee.getDepartmentCode() == null || employee.getDepartmentCode().isEmpty()) {
                departmentDto = new DepartmentDto();
            } else {
                departmentDto = getDepartmentByDepartmentCode(employee.getDepartmentCode());
                //departmentDto = departmentAPIClient.getDepartmentByDepartmentCode(employee.getDepartmentCode());
            }

            OrganizationDto organizationDto = null;
            if (employee.getOrganizationCOde() == null || employee.getOrganizationCOde().isEmpty()) {
                organizationDto = new OrganizationDto();
            } else {
                organizationDto = organizationApiClient.getOrganizationByOrganizationCode(employee.getOrganizationCOde());
            }

            EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
            return new APIResponse(employeeDto, departmentDto, organizationDto);
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
            departmentDto = departmentAPIClient.getDepartmentByDepartmentCode(employee.get().getDepartmentCode());
        }

        OrganizationDto organizationDto = null;
        if (employee.get().getOrganizationCOde() == null || employee.get().getOrganizationCOde().isEmpty()) {
            organizationDto = new OrganizationDto();
        } else {
            organizationDto = organizationApiClient.getOrganizationByOrganizationCode(employee.get().getOrganizationCOde());
        }

        EmployeeDto employeeDto = modelMapper.map(employee.get(), EmployeeDto.class);

        return new APIResponse(employeeDto, departmentDto, organizationDto);
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
        throw new ResourceNotFoundException("Employee", "id", String.valueOf(id));
    }

    @Override
    public String deleteEmployeeByEmail(String email) {

        Optional<Employee> employee = employeeRepository.findEmployeeByEmail(email);

        if (employee.isPresent()) {
            employeeRepository.delete(employee.get());
            return "Employee deleted successfully by email " + email;
        }
        throw new ResourceNotFoundException("Employee", "email", email);
    }

    @Override
    public DepartmentDto getDepartmentByDepartmentCode(String departmentCode) {
        return webClient.get()
                .uri("http://localhost:8080/api/departments/" + departmentCode)
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();
    }


    public List<APIResponse> getDefaultDepartment(Exception exception) {

        LOGGER.info("Inside GetDefaultDepartment .....");
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().map(employee -> {

            DepartmentDto departmentDto = new DepartmentDto();
            departmentDto.setCode("RD001");
            departmentDto.setName("R&D");
            departmentDto.setDescription("Research and Development Department");

            OrganizationDto organizationDto = new OrganizationDto();
            organizationDto.setCode("TORG");
            organizationDto.setName("SampleORG");
            organizationDto.setDescription("Default Organization");
            organizationDto.setCreatedDate(LocalDateTime.now());

            EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
            return new APIResponse(employeeDto, departmentDto, organizationDto);
        }).toList();
    }
}
