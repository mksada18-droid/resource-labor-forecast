@echo off
REM Resource Labor Forecast - Quick Start Script for Windows
REM This script helps you build and run the Spring Boot application

echo ==========================================
echo Resource Labor Forecast - Quick Start
echo ==========================================
echo.

REM Check if Java is installed
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo X Java is not installed!
    echo.
    echo Please install Java 17 first from:
    echo   https://adoptium.net/
    echo.
    echo Or follow the instructions in SETUP.md
    pause
    exit /b 1
)

echo [OK] Java is installed
java -version
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo X Maven is not installed!
    echo.
    echo Please install Maven first from:
    echo   https://maven.apache.org/download.cgi
    echo.
    echo Or follow the instructions in SETUP.md
    pause
    exit /b 1
)

echo [OK] Maven is installed
mvn -version
echo.

REM Build the application
echo Building the application...
echo.
call mvn clean install -DskipTests

if %ERRORLEVEL% EQU 0 (
    echo.
    echo [OK] Build successful!
    echo.
    echo Starting the application...
    echo.
    echo ==========================================
    echo Press Ctrl+C to stop the application
    echo ==========================================
    echo.
    
    REM Run the application
    call mvn spring-boot:run
) else (
    echo.
    echo X Build failed! Please check the error messages above.
    pause
    exit /b 1
)

@REM Made with Bob
