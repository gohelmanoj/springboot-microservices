package com.spring.learning.organizationservice.controller;

import com.spring.learning.organizationservice.dto.OrganizationDto;
import com.spring.learning.organizationservice.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/organizations")
@AllArgsConstructor
public class OrganizationController {

    private OrganizationService organizationService;

    @GetMapping
    public ResponseEntity<List<OrganizationDto>> getAllOrganization() {

        return new ResponseEntity<>(organizationService.getAllOrganization(), HttpStatus.OK);
    }

    @GetMapping("{code}")
    public ResponseEntity<OrganizationDto> getOrganizationByCode(@PathVariable("code") String code) {

        return new ResponseEntity<>(organizationService.getOrganizationByCode(code), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrganizationDto> saveOrganization(@RequestBody OrganizationDto organizationDto) {
        return new ResponseEntity<>(organizationService.saveOrganization(organizationDto), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOrganizationById(@PathVariable("id") Long id) {

        return new ResponseEntity<>(organizationService.deleteOrganizationById(id), HttpStatus.OK);
    }
}
