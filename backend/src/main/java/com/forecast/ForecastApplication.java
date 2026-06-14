package com.forecast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application class for Resource Labor Forecast Hours System
 * 
 * This application provides REST APIs for managing resources and their forecasted hours.
 * 
 * Features:
 * - Resource management (CRUD operations)
 * - Forecast management (CRUD operations)
 * - H2 in-memory database for development
 * - CORS enabled for React frontend
 * - Input validation
 * - RESTful API design
 * 
 * @author Resource Labor Forecast Team
 * @version 1.0.0
 */
@SpringBootApplication
public class ForecastApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForecastApplication.class, args);
        System.out.println("\n===========================================");
        System.out.println("Resource Labor Forecast Application Started");
        System.out.println("===========================================");
        System.out.println("Server running on: http://localhost:8080");
        System.out.println("H2 Console: http://localhost:8080/h2-console");
        System.out.println("API Base URL: http://localhost:8080/api");
        System.out.println("===========================================\n");
    }
}

// Made with Bob
