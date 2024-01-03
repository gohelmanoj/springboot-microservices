package com.spring.learning.employeeservice.controller;

import com.spring.learning.employeeservice.dto.APIResponse;
import com.spring.learning.employeeservice.dto.EmployeeDto;
import com.spring.learning.employeeservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/employees")
public class EmployeeController {

    EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<APIResponse>> getAllEmployee() {
        return new ResponseEntity<>(employeeService.getAllEmployee(), HttpStatus.OK);
    }

    @GetMapping("{email}")
    public ResponseEntity<APIResponse> getEmployeeByEmail(@PathVariable("email") String email) {
        return new ResponseEntity<>(employeeService.getEmployeeByEmail(email), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto) {
        return new ResponseEntity<>(employeeService.saveEmployee(employeeDto), HttpStatus.CREATED);
    }

    @DeleteMapping("email/{email}")
    public ResponseEntity<String> deleteEmployeeByEmail(@PathVariable("email") String email) {
        return new ResponseEntity<>(employeeService.deleteEmployeeByEmail(email), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(employeeService.deleteEmployeeById(id), HttpStatus.OK);
    }
}
