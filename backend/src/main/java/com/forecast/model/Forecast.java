package com.forecast.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "forecasts", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"resource_id", "week_ending_date"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Forecast {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Resource ID is required")
    @Column(name = "resource_id", nullable = false)
    private Long resourceId;
    
    @NotNull(message = "Week ending date is required")
    @Column(name = "week_ending_date", nullable = false)
    private LocalDate weekEndingDate;
    
    @NotNull(message = "Forecasted hours is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Forecasted hours must be greater than 0")
    @Column(name = "forecasted_hours", nullable = false, precision = 5, scale = 2)
    private BigDecimal forecastedHours;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

// Made with Bob
