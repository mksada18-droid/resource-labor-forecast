package com.forecast.controller;

import com.forecast.model.Forecast;
import com.forecast.service.ForecastService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/forecasts")
@CrossOrigin(origins = "http://localhost:3000")
public class ForecastController {
    
    private final ForecastService forecastService;
    
    @Autowired
    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }
    
    /**
     * Get all forecasts
     * @return list of all forecasts
     */
    @GetMapping
    public ResponseEntity<List<Forecast>> getAllForecasts() {
        List<Forecast> forecasts = forecastService.getAllForecasts();
        return ResponseEntity.ok(forecasts);
    }
    
    /**
     * Get a forecast by ID
     * @param id the forecast ID
     * @return the forecast if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getForecastById(@PathVariable Long id) {
        try {
            return forecastService.getForecastById(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(createErrorResponse("Forecast not found with id: " + id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error retrieving forecast: " + e.getMessage()));
        }
    }
    
    /**
     * Get all forecasts for a specific resource
     * @param resourceId the resource ID
     * @return list of forecasts for the resource
     */
    @GetMapping("/resource/{resourceId}")
    public ResponseEntity<?> getForecastsByResourceId(@PathVariable Long resourceId) {
        try {
            List<Forecast> forecasts = forecastService.getForecastsByResourceId(resourceId);
            return ResponseEntity.ok(forecasts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error retrieving forecasts: " + e.getMessage()));
        }
    }
    
    /**
     * Get all forecasts for a specific week ending date
     * @param weekEndingDate the week ending date (format: yyyy-MM-dd)
     * @return list of forecasts for that week
     */
    @GetMapping("/week/{weekEndingDate}")
    public ResponseEntity<?> getForecastsByWeekEndingDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate weekEndingDate) {
        try {
            List<Forecast> forecasts = forecastService.getForecastsByWeekEndingDate(weekEndingDate);
            return ResponseEntity.ok(forecasts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error retrieving forecasts: " + e.getMessage()));
        }
    }
    
    /**
     * Get forecasts for a resource within a date range
     * @param resourceId the resource ID
     * @param startDate the start date
     * @param endDate the end date
     * @return list of forecasts within the date range
     */
    @GetMapping("/resource/{resourceId}/range")
    public ResponseEntity<?> getForecastsByResourceIdAndDateRange(
            @PathVariable Long resourceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<Forecast> forecasts = forecastService.getForecastsByResourceIdAndDateRange(
                    resourceId, startDate, endDate);
            return ResponseEntity.ok(forecasts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error retrieving forecasts: " + e.getMessage()));
        }
    }
    
    /**
     * Create a new forecast
     * @param forecast the forecast to create
     * @return the created forecast
     */
    @PostMapping
    public ResponseEntity<?> createForecast(@Valid @RequestBody Forecast forecast) {
        try {
            Forecast createdForecast = forecastService.createForecast(forecast);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdForecast);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error creating forecast: " + e.getMessage()));
        }
    }
    
    /**
     * Update an existing forecast
     * @param id the forecast ID
     * @param forecast the updated forecast details
     * @return the updated forecast
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateForecast(@PathVariable Long id, @Valid @RequestBody Forecast forecast) {
        try {
            Forecast updatedForecast = forecastService.updateForecast(id, forecast);
            return ResponseEntity.ok(updatedForecast);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error updating forecast: " + e.getMessage()));
        }
    }
    
    /**
     * Delete a forecast
     * @param id the forecast ID
     * @return success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteForecast(@PathVariable Long id) {
        try {
            forecastService.deleteForecast(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Forecast deleted successfully");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error deleting forecast: " + e.getMessage()));
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
