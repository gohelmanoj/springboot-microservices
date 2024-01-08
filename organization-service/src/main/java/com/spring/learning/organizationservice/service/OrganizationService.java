package com.spring.learning.organizationservice.service;

import com.spring.learning.organizationservice.dto.OrganizationDto;

import java.util.List;

public interface OrganizationService {

    List<OrganizationDto> getAllOrganization();

    OrganizationDto getOrganizationByCode(String code);

    OrganizationDto saveOrganization(OrganizationDto organizationDto);

    String deleteOrganizationById(Long id);
}
