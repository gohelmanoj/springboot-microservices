package com.spring.learning.organizationservice.repostitory;

import com.spring.learning.organizationservice.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Optional<Organization> findOrganizationByCode(String code);
}
