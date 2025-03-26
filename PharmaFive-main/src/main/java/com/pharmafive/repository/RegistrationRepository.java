package com.pharmafive.repository;

import com.pharmafive.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    Optional<Registration> findByEmail(String email);

    Page<Registration> findByStatus(Registration.Status status, Pageable pageable);

    Page<Registration> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrOrganisationNameContainingIgnoreCase(
        String name, String email, String organisationName, Pageable pageable);

    Page<Registration> findByStatusAndNameContainingIgnoreCaseOrStatusAndEmailContainingIgnoreCaseOrStatusAndOrganisationNameContainingIgnoreCase(
        Registration.Status status1, String name,
        Registration.Status status2, String email,
        Registration.Status status3, String organisationName,
        Pageable pageable
    );
}
