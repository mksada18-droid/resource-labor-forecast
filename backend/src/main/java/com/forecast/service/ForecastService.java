package com.forecast.service;

import com.forecast.model.Forecast;
import com.forecast.repository.ForecastRepository;
import com.forecast.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ForecastService {
    
    private final ForecastRepository forecastRepository;
    private final ResourceRepository resourceRepository;
    
    @Autowired
    public ForecastService(ForecastRepository forecastRepository, ResourceRepository resourceRepository) {
        this.forecastRepository = forecastRepository;
        this.resourceRepository = resourceRepository;
    }
    
    /**
     * Get all forecasts
     * @return list of all forecasts
     */
    public List<Forecast> getAllForecasts() {
        return forecastRepository.findAll();
    }
    
    /**
     * Get a forecast by ID
     * @param id the forecast ID
     * @return Optional containing the forecast if found
     */
    public Optional<Forecast> getForecastById(Long id) {
        return forecastRepository.findById(id);
    }
    
    /**
     * Get all forecasts for a specific resource
     * @param resourceId the resource ID
     * @return list of forecasts for the resource
     */
    public List<Forecast> getForecastsByResourceId(Long resourceId) {
        return forecastRepository.findByResourceId(resourceId);
    }
    
    /**
     * Get all forecasts for a specific week ending date
     * @param weekEndingDate the week ending date
     * @return list of forecasts for that week
     */
    public List<Forecast> getForecastsByWeekEndingDate(LocalDate weekEndingDate) {
        return forecastRepository.findByWeekEndingDate(weekEndingDate);
    }
    
    /**
     * Get forecasts for a resource within a date range
     * @param resourceId the resource ID
     * @param startDate the start date
     * @param endDate the end date
     * @return list of forecasts within the date range
     */
    public List<Forecast> getForecastsByResourceIdAndDateRange(Long resourceId, LocalDate startDate, LocalDate endDate) {
        return forecastRepository.findByResourceIdAndWeekEndingDateBetween(resourceId, startDate, endDate);
    }
    
    /**
     * Create a new forecast
     * @param forecast the forecast to create
     * @return the created forecast
     * @throws IllegalArgumentException if resource doesn't exist or forecast already exists for that week
     */
    public Forecast createForecast(Forecast forecast) {
        // Validate that the resource exists
        if (!resourceRepository.existsById(forecast.getResourceId())) {
            throw new IllegalArgumentException("Resource not found with id: " + forecast.getResourceId());
        }
        
        // Check if forecast already exists for this resource and week
        if (forecastRepository.existsByResourceIdAndWeekEndingDate(
                forecast.getResourceId(), forecast.getWeekEndingDate())) {
            throw new IllegalArgumentException(
                "Forecast already exists for resource " + forecast.getResourceId() + 
                " and week ending " + forecast.getWeekEndingDate());
        }
        
        return forecastRepository.save(forecast);
    }
    
    /**
     * Update an existing forecast
     * @param id the forecast ID
     * @param forecastDetails the updated forecast details
     * @return the updated forecast
     * @throws IllegalArgumentException if forecast not found or validation fails
     */
    public Forecast updateForecast(Long id, Forecast forecastDetails) {
        Forecast forecast = forecastRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Forecast not found with id: " + id));
        
        // Validate that the resource exists if it's being changed
        if (!forecast.getResourceId().equals(forecastDetails.getResourceId()) && 
            !resourceRepository.existsById(forecastDetails.getResourceId())) {
            throw new IllegalArgumentException("Resource not found with id: " + forecastDetails.getResourceId());
        }
        
        // Check if the update would create a duplicate (different resource or week)
        if (!forecast.getResourceId().equals(forecastDetails.getResourceId()) || 
            !forecast.getWeekEndingDate().equals(forecastDetails.getWeekEndingDate())) {
            if (forecastRepository.existsByResourceIdAndWeekEndingDate(
                    forecastDetails.getResourceId(), forecastDetails.getWeekEndingDate())) {
                throw new IllegalArgumentException(
                    "Forecast already exists for resource " + forecastDetails.getResourceId() + 
                    " and week ending " + forecastDetails.getWeekEndingDate());
            }
        }
        
        forecast.setResourceId(forecastDetails.getResourceId());
        forecast.setWeekEndingDate(forecastDetails.getWeekEndingDate());
        forecast.setForecastedHours(forecastDetails.getForecastedHours());
        forecast.setNotes(forecastDetails.getNotes());
        
        return forecastRepository.save(forecast);
    }
    
    /**
     * Delete a forecast
     * @param id the forecast ID
     * @throws IllegalArgumentException if forecast not found
     */
    public void deleteForecast(Long id) {
        if (!forecastRepository.existsById(id)) {
            throw new IllegalArgumentException("Forecast not found with id: " + id);
        }
        forecastRepository.deleteById(id);
    }
    
    /**
     * Check if a forecast exists
     * @param id the forecast ID
     * @return true if exists, false otherwise
     */
    public boolean forecastExists(Long id) {
        return forecastRepository.existsById(id);
    }
}

// Made with Bob
