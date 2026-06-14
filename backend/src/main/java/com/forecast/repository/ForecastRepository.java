package com.forecast.repository;

import com.forecast.model.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {
    
    /**
     * Find all forecasts for a specific resource
     * @param resourceId the resource ID
     * @return list of forecasts for the resource
     */
    List<Forecast> findByResourceId(Long resourceId);
    
    /**
     * Find all forecasts for a specific week ending date
     * @param weekEndingDate the week ending date
     * @return list of forecasts for that week
     */
    List<Forecast> findByWeekEndingDate(LocalDate weekEndingDate);
    
    /**
     * Find a specific forecast for a resource and week
     * @param resourceId the resource ID
     * @param weekEndingDate the week ending date
     * @return Optional containing the forecast if found
     */
    Optional<Forecast> findByResourceIdAndWeekEndingDate(Long resourceId, LocalDate weekEndingDate);
    
    /**
     * Find forecasts for a resource within a date range
     * @param resourceId the resource ID
     * @param startDate the start date
     * @param endDate the end date
     * @return list of forecasts within the date range
     */
    List<Forecast> findByResourceIdAndWeekEndingDateBetween(Long resourceId, LocalDate startDate, LocalDate endDate);
    
    /**
     * Check if a forecast exists for a resource and week
     * @param resourceId the resource ID
     * @param weekEndingDate the week ending date
     * @return true if exists, false otherwise
     */
    boolean existsByResourceIdAndWeekEndingDate(Long resourceId, LocalDate weekEndingDate);
}

// Made with Bob
