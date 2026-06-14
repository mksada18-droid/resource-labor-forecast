-- Drop tables if they exist
DROP TABLE IF EXISTS forecasts;
DROP TABLE IF EXISTS resources;

-- Create resources table
CREATE TABLE resources (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    department VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create forecasts table
CREATE TABLE forecasts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    resource_id BIGINT NOT NULL,
    week_ending_date DATE NOT NULL,
    forecasted_hours DECIMAL(5,2) NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (resource_id) REFERENCES resources(id) ON DELETE CASCADE,
    CONSTRAINT unique_resource_week UNIQUE (resource_id, week_ending_date)
);

-- Create indexes for better query performance
CREATE INDEX idx_forecasts_resource_id ON forecasts(resource_id);
CREATE INDEX idx_forecasts_week_ending_date ON forecasts(week_ending_date);
CREATE INDEX idx_resources_email ON resources(email);
CREATE INDEX idx_resources_department ON resources(department);

-- Insert sample data for testing
INSERT INTO resources (name, email, department) VALUES
('John Doe', 'john.doe@example.com', 'Engineering'),
('Jane Smith', 'jane.smith@example.com', 'Engineering'),
('Bob Johnson', 'bob.johnson@example.com', 'Design'),
('Alice Williams', 'alice.williams@example.com', 'Product');

INSERT INTO forecasts (resource_id, week_ending_date, forecasted_hours, notes) VALUES
(1, '2026-06-14', 40.00, 'Full week on Project Alpha'),
(1, '2026-06-21', 35.00, 'Project Alpha and code review'),
(2, '2026-06-14', 40.00, 'Backend development'),
(2, '2026-06-21', 40.00, 'API integration work'),
(3, '2026-06-14', 30.00, 'UI/UX design for new feature'),
(4, '2026-06-14', 38.00, 'Product planning and stakeholder meetings');

-- Made with Bob
