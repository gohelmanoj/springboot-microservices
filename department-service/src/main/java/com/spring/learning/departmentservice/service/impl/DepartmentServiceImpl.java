package com.spring.learning.departmentservice.service.impl;

import com.spring.learning.departmentservice.dto.DepartmentDto;
import com.spring.learning.departmentservice.entity.Department;
import com.spring.learning.departmentservice.exception.CodeAlreadyExistException;
import com.spring.learning.departmentservice.exception.ResourceNotFoundException;
import com.spring.learning.departmentservice.repository.DepartmentRepository;
import com.spring.learning.departmentservice.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    private ModelMapper modelMapper;

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {

        Optional<Department> departmentOptional = departmentRepository.findDepartmentByCode(departmentDto.getCode());

        if (departmentOptional.isPresent()) {
            throw new CodeAlreadyExistException(departmentDto.getCode());
        }

        Department department = modelMapper.map(departmentDto, Department.class);
        Department savedDepartment = departmentRepository.save(department);
        return modelMapper.map(savedDepartment, DepartmentDto.class);
    }

    @Override
    public List<DepartmentDto> getAllDepartment() {
        return departmentRepository.findAll()
                .stream().map(department -> modelMapper.map(department, DepartmentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto getDepartmentByCode(String code) {

        Optional<Department> department = departmentRepository.findDepartmentByCode(code);
        if (department.isEmpty()) {
            throw new ResourceNotFoundException("Department", "code", code);
        }
        return modelMapper.map(department.get(), DepartmentDto.class);
    }

    @Override
    public String deleteDepartmentByCode(String code) {

        Optional<Department> department = departmentRepository.findDepartmentByCode(code);

        if(department.isPresent()) {
            departmentRepository.delete(department.get());
            return "Department deleted successfully with code " + code + " ..!!";
        }
        throw new ResourceNotFoundException("Department", "code", code);
    }

    @Override
    public String deleteDepartmentById(Long id) {
        Optional<Department> department = departmentRepository.findById(id);

        if(department.isPresent()) {
            departmentRepository.delete(department.get());
            return "Department deleted successfully with id " + id + " ..!!";
        }
        throw new ResourceNotFoundException("Department", "id", String.valueOf(id));
    }

}
