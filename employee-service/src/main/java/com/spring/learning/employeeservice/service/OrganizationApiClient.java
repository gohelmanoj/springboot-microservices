package com.spring.learning.employeeservice.service;

import com.spring.learning.employeeservice.dto.OrganizationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ORGANIZATION-SERVICE")
public interface OrganizationApiClient {

    @GetMapping("api/organizations/{organization-code}")
    OrganizationDto getOrganizationByOrganizationCode(@PathVariable("organization-code") String organizationCode);
}
