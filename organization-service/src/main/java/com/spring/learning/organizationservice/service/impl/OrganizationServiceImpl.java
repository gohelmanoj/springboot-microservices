package com.spring.learning.organizationservice.service.impl;

import com.spring.learning.organizationservice.dto.OrganizationDto;
import com.spring.learning.organizationservice.entity.Organization;
import com.spring.learning.organizationservice.exception.CodeAlreadyExistException;
import com.spring.learning.organizationservice.exception.ResourceNotFoundException;
import com.spring.learning.organizationservice.repostitory.OrganizationRepository;
import com.spring.learning.organizationservice.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private ModelMapper modelMapper;

    private OrganizationRepository organizationRepository;

    @Override
    public List<OrganizationDto> getAllOrganization() {
        List<Organization> organizations = organizationRepository.findAll();

        return organizations.stream().map(organization ->
            modelMapper.map(organization, OrganizationDto.class)
        ).collect(Collectors.toList());
    }

    @Override
    public OrganizationDto getOrganizationByCode(String code) {

        Optional<Organization> organization = organizationRepository.findOrganizationByCode(code);

        if(organization.isPresent()) {
            return modelMapper.map(organization.get(), OrganizationDto.class);
        } else {
            throw new ResourceNotFoundException("Organization", "Code", code);
        }
    }

    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {

        Optional<Organization> organizationOptional = organizationRepository.findOrganizationByCode(organizationDto.getCode());

        if (organizationOptional.isPresent()) {
            throw new CodeAlreadyExistException(organizationDto.getCode());
        } else {
            Organization organization = modelMapper.map(organizationDto, Organization.class);
            Organization savedOrganization = organizationRepository.save(organization);

            return modelMapper.map(savedOrganization, OrganizationDto.class);
        }
    }

    @Override
    public String deleteOrganizationById(Long id) {

        Optional<Organization> organizationOptional = organizationRepository.findById(id);
        if (organizationOptional.isPresent()) {
            organizationRepository.delete(organizationOptional.get());
            return "Organization deleted successfully with id " + id;
        }
        throw  new ResourceNotFoundException("Organization", "Id", String.valueOf(id));
    }
}
