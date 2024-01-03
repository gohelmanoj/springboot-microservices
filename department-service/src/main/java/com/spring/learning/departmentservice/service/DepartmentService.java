package com.spring.learning.departmentservice.service;

import com.spring.learning.departmentservice.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    DepartmentDto saveDepartment(DepartmentDto departmentDto);

    List<DepartmentDto> getAllDepartment();

    DepartmentDto getDepartmentByCode(String code);

    String deleteDepartmentByCode(String code);

    String deleteDepartmentById(Long id);

}
