package com.forecast.service;

import com.forecast.model.Resource;
import com.forecast.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ResourceService {
    
    private final ResourceRepository resourceRepository;
    
    @Autowired
    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }
    
    /**
     * Get all resources
     * @return list of all resources
     */
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }
    
    /**
     * Get a resource by ID
     * @param id the resource ID
     * @return Optional containing the resource if found
     */
    public Optional<Resource> getResourceById(Long id) {
        return resourceRepository.findById(id);
    }
    
    /**
     * Get a resource by email
     * @param email the email address
     * @return Optional containing the resource if found
     */
    public Optional<Resource> getResourceByEmail(String email) {
        return resourceRepository.findByEmail(email);
    }
    
    /**
     * Get all resources in a department
     * @param department the department name
     * @return list of resources in the department
     */
    public List<Resource> getResourcesByDepartment(String department) {
        return resourceRepository.findByDepartment(department);
    }
    
    /**
     * Create a new resource
     * @param resource the resource to create
     * @return the created resource
     * @throws IllegalArgumentException if email already exists
     */
    public Resource createResource(Resource resource) {
        if (resourceRepository.existsByEmail(resource.getEmail())) {
            throw new IllegalArgumentException("Resource with email " + resource.getEmail() + " already exists");
        }
        return resourceRepository.save(resource);
    }
    
    /**
     * Update an existing resource
     * @param id the resource ID
     * @param resourceDetails the updated resource details
     * @return the updated resource
     * @throws IllegalArgumentException if resource not found or email conflict
     */
    public Resource updateResource(Long id, Resource resourceDetails) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resource not found with id: " + id));
        
        // Check if email is being changed and if new email already exists
        if (!resource.getEmail().equals(resourceDetails.getEmail()) && 
            resourceRepository.existsByEmail(resourceDetails.getEmail())) {
            throw new IllegalArgumentException("Resource with email " + resourceDetails.getEmail() + " already exists");
        }
        
        resource.setName(resourceDetails.getName());
        resource.setEmail(resourceDetails.getEmail());
        resource.setDepartment(resourceDetails.getDepartment());
        
        return resourceRepository.save(resource);
    }
    
    /**
     * Delete a resource
     * @param id the resource ID
     * @throws IllegalArgumentException if resource not found
     */
    public void deleteResource(Long id) {
        if (!resourceRepository.existsById(id)) {
            throw new IllegalArgumentException("Resource not found with id: " + id);
        }
        resourceRepository.deleteById(id);
    }
    
    /**
     * Check if a resource exists
     * @param id the resource ID
     * @return true if exists, false otherwise
     */
    public boolean resourceExists(Long id) {
        return resourceRepository.existsById(id);
    }
}

// Made with Bob
