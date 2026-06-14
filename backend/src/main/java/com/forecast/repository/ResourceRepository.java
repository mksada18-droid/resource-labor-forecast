package com.forecast.repository;

import com.forecast.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    
    /**
     * Find a resource by email address
     * @param email the email to search for
     * @return Optional containing the resource if found
     */
    Optional<Resource> findByEmail(String email);
    
    /**
     * Find all resources in a specific department
     * @param department the department name
     * @return list of resources in the department
     */
    List<Resource> findByDepartment(String department);
    
    /**
     * Check if a resource exists with the given email
     * @param email the email to check
     * @return true if exists, false otherwise
     */
    boolean existsByEmail(String email);
}

// Made with Bob
