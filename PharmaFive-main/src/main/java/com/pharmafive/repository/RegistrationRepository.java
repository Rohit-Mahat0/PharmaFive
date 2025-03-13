package com.pharmafive.repository;

import com.pharmafive.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

	Optional<Registration> findByEmail(String email);
    @Query("SELECT r FROM Registration r " +
           "WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR LOWER(r.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR LOWER(r.organisationName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Registration> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
