package com.forecast.controller;

import com.forecast.model.Resource;
import com.forecast.service.ResourceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin(origins = "http://localhost:3000")
public class ResourceController {
    
    private final ResourceService resourceService;
    
    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }
    
    /**
     * Get all resources
     * @return list of all resources
     */
    @GetMapping
    public ResponseEntity<List<Resource>> getAllResources() {
        List<Resource> resources = resourceService.getAllResources();
        return ResponseEntity.ok(resources);
    }
    
    /**
     * Get a resource by ID
     * @param id the resource ID
     * @return the resource if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getResourceById(@PathVariable Long id) {
        try {
            return resourceService.getResourceById(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(createErrorResponse("Resource not found with id: " + id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error retrieving resource: " + e.getMessage()));
        }
    }
    
    /**
     * Create a new resource
     * @param resource the resource to create
     * @return the created resource
     */
    @PostMapping
    public ResponseEntity<?> createResource(@Valid @RequestBody Resource resource) {
        try {
            Resource createdResource = resourceService.createResource(resource);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdResource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error creating resource: " + e.getMessage()));
        }
    }
    
    /**
     * Update an existing resource
     * @param id the resource ID
     * @param resource the updated resource details
     * @return the updated resource
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateResource(@PathVariable Long id, @Valid @RequestBody Resource resource) {
        try {
            Resource updatedResource = resourceService.updateResource(id, resource);
            return ResponseEntity.ok(updatedResource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error updating resource: " + e.getMessage()));
        }
    }
    
    /**
     * Delete a resource
     * @param id the resource ID
     * @return success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResource(@PathVariable Long id) {
        try {
            resourceService.deleteResource(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Resource deleted successfully");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error deleting resource: " + e.getMessage()));
        }
    }
    
    /**
     * Helper method to create error response
     * @param message the error message
     * @return error response map
     */
    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return error;
    }
}

// Made with Bob
