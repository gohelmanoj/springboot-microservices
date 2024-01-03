package com.spring.learning.departmentservice.controller;

import com.spring.learning.departmentservice.dto.DepartmentDto;
import com.spring.learning.departmentservice.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/departments")
public class DepartmentController {

    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentDto> saveDepartment(@RequestBody DepartmentDto departmentDto) {

        DepartmentDto savedDepartmentDto = departmentService.saveDepartment(departmentDto);
        return new ResponseEntity<>(savedDepartmentDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        List<DepartmentDto> departments = departmentService.getAllDepartment();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("{code}")
    public ResponseEntity<DepartmentDto> getDepartmentByCode(@PathVariable("code") String code) {
        return new ResponseEntity<>(departmentService.getDepartmentByCode(code), HttpStatus.OK);
    }

    @DeleteMapping("code/{code}")
    public ResponseEntity<String> deleteDepartmentByCode(@PathVariable("code") String code) {
        return new ResponseEntity<>(departmentService.deleteDepartmentByCode(code), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteDepartmentById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(departmentService.deleteDepartmentById(id), HttpStatus.OK);
    }
}
