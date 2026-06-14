# Setup Guide - Resource Labor Forecast Backend

This guide will help you set up and run the Spring Boot backend application.

## Prerequisites Installation

### Option 1: Using Homebrew (Recommended for macOS)

#### 1. Install Homebrew (if not already installed)
```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

#### 2. Install Java 17
```bash
brew install openjdk@17
```

After installation, add Java to your PATH:
```bash
echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

Verify Java installation:
```bash
java -version
```

#### 3. Install Maven
```bash
brew install maven
```

Verify Maven installation:
```bash
mvn -version
```

### Option 2: Manual Installation

#### Install Java 17
1. Download Java 17 from [Oracle](https://www.oracle.com/java/technologies/downloads/#java17) or [Adoptium](https://adoptium.net/)
2. Install the downloaded package
3. Set JAVA_HOME environment variable:
   ```bash
   export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home
   export PATH=$JAVA_HOME/bin:$PATH
   ```

#### Install Maven
1. Download Maven from [Apache Maven](https://maven.apache.org/download.cgi)
2. Extract to a directory (e.g., `/opt/maven`)
3. Add to PATH:
   ```bash
   export PATH=/opt/maven/bin:$PATH
   ```

## Building and Running the Application

### Step 1: Navigate to Backend Directory
```bash
cd backend
```

### Step 2: Build the Project
```bash
mvn clean install
```

This will:
- Download all dependencies
- Compile the code
- Run tests
- Create a JAR file in the `target` directory

### Step 3: Run the Application

#### Option A: Using Maven
```bash
mvn spring-boot:run
```

#### Option B: Using the JAR file
```bash
java -jar target/resource-labor-forecast-1.0.0.jar
```

### Step 4: Verify Application is Running

The application should start and display:
```
===========================================
Resource Labor Forecast Application Started
===========================================
Server running on: http://localhost:8080
H2 Console: http://localhost:8080/h2-console
API Base URL: http://localhost:8080/api
===========================================
```

## Testing the Application

### 1. Access H2 Database Console
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:forecastdb`
- Username: `sa`
- Password: (leave empty)

### 2. Test API Endpoints

#### Get All Resources
```bash
curl http://localhost:8080/api/resources
```

#### Get All Forecasts
```bash
curl http://localhost:8080/api/forecasts
```

#### Create a New Resource
```bash
curl -X POST http://localhost:8080/api/resources \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test.user@example.com",
    "department": "Testing"
  }'
```

#### Create a New Forecast
```bash
curl -X POST http://localhost:8080/api/forecasts \
  -H "Content-Type: application/json" \
  -d '{
    "resourceId": 1,
    "weekEndingDate": "2026-06-28",
    "forecastedHours": 40.00,
    "notes": "Test forecast"
  }'
```

## Troubleshooting

### Issue: "mvn: command not found"
**Solution**: Maven is not installed or not in PATH. Follow the installation steps above.

### Issue: "Unable to locate a Java Runtime"
**Solution**: Java is not installed or JAVA_HOME is not set correctly. Follow the Java installation steps above.

### Issue: Port 8080 already in use
**Solution**: Either stop the application using port 8080, or change the port in `application.properties`:
```properties
server.port=8081
```

### Issue: Database connection errors
**Solution**: The application uses H2 in-memory database which should work out of the box. Check the logs for specific errors.

## Development Tips

### Hot Reload (Spring Boot DevTools)
To enable hot reload during development, add this dependency to `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

### Running Tests
```bash
mvn test
```

### Clean Build
```bash
mvn clean install
```

### Skip Tests During Build
```bash
mvn clean install -DskipTests
```

### View Application Logs
Logs are displayed in the console. To change log level, edit `application.properties`:
```properties
logging.level.com.forecast=DEBUG
```

## IDE Setup

### IntelliJ IDEA
1. Open IntelliJ IDEA
2. File → Open → Select the `backend` folder
3. Wait for Maven to import dependencies
4. Right-click on `ForecastApplication.java` → Run

### VS Code
1. Install "Extension Pack for Java" from VS Code marketplace
2. Install "Spring Boot Extension Pack"
3. Open the `backend` folder
4. Press F5 to run or use the Spring Boot Dashboard

### Eclipse
1. File → Import → Maven → Existing Maven Projects
2. Select the `backend` folder
3. Right-click on project → Run As → Spring Boot App

## Next Steps

Once the backend is running successfully:
1. Test all API endpoints using curl or Postman
2. Verify data persistence in H2 console
3. Build the frontend application to connect with this backend
4. Consider adding authentication and authorization
5. Plan for production database (PostgreSQL, MySQL, etc.)

## Support

For issues or questions:
- Check the main README.md for API documentation
- Review application logs for error messages
- Verify all prerequisites are correctly installed

---
Made with Bob