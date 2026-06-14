# 🎯 Get Started - Step by Step Guide

Welcome! This guide will walk you through getting your Resource Labor Forecast backend up and running.

## 📋 What You Have

Your Spring Boot backend is **100% complete** and ready to run! Here's what's included:

✅ **Complete Backend Application**
- REST API with all CRUD operations
- Resource management (employees/team members)
- Forecast management (weekly hour forecasts)
- H2 in-memory database with sample data
- Input validation and error handling
- CORS configured for frontend integration

✅ **Documentation**
- README.md - API documentation
- SETUP.md - Installation guide
- API-TESTING-GUIDE.md - Testing examples
- PROJECT-SUMMARY.md - Project overview
- This file - Step-by-step guide

✅ **Helper Scripts**
- run.sh - Quick start for macOS/Linux
- run.bat - Quick start for Windows

## 🚦 Step-by-Step Instructions

### Step 1: Install Prerequisites

You need Java 17 and Maven to run the application.

#### For macOS (Using Homebrew - Recommended)

**1.1 Install Homebrew (if not installed)**
```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

**1.2 Install Java 17**
```bash
brew install openjdk@17
```

**1.3 Add Java to PATH**
```bash
echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

**1.4 Verify Java installation**
```bash
java -version
```
You should see: `openjdk version "17.x.x"`

**1.5 Install Maven**
```bash
brew install maven
```

**1.6 Verify Maven installation**
```bash
mvn -version
```
You should see Maven version information.

#### For Windows

**1.1 Install Java 17**
- Download from: https://adoptium.net/
- Run the installer
- Follow the installation wizard

**1.2 Install Maven**
- Download from: https://maven.apache.org/download.cgi
- Extract to C:\Program Files\Maven
- Add to PATH in System Environment Variables

**1.3 Verify installations**
Open Command Prompt:
```cmd
java -version
mvn -version
```

### Step 2: Navigate to Backend Directory

Open Terminal (macOS) or Command Prompt (Windows):

```bash
cd /Users/sudarshan/Desktop/backend
```

### Step 3: Run the Application

#### Option A: Using Quick Start Script (Easiest)

**macOS/Linux:**
```bash
./run.sh
```

**Windows:**
```cmd
run.bat
```

The script will:
- Check if Java and Maven are installed
- Build the application
- Start the server

#### Option B: Manual Commands

```bash
# Build the application
mvn clean install

# Run the application
mvn spring-boot:run
```

### Step 4: Verify Application is Running

You should see output like this:

```
===========================================
Resource Labor Forecast Application Started
===========================================
Server running on: http://localhost:8080
H2 Console: http://localhost:8080/h2-console
API Base URL: http://localhost:8080/api
===========================================
```

### Step 5: Test the API

Open a new terminal window and test the API:

**Test 1: Get all resources**
```bash
curl http://localhost:8080/api/resources
```

You should see JSON with 4 sample resources.

**Test 2: Get all forecasts**
```bash
curl http://localhost:8080/api/forecasts
```

You should see JSON with sample forecasts.

**Test 3: Access H2 Console**
Open browser: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:forecastdb`
- Username: `sa`
- Password: (leave empty)
- Click "Connect"

You can now see the database tables and data!

### Step 6: Explore the API

See **API-TESTING-GUIDE.md** for comprehensive examples of:
- Creating resources
- Creating forecasts
- Updating data
- Deleting data
- Error handling
- Complete testing scenarios

## 🎉 Success! What's Next?

Your backend is now running! Here are your next steps:

### Immediate Actions
1. ✅ Keep the backend running in one terminal
2. ✅ Test API endpoints using curl or Postman
3. ✅ Explore the H2 console to see the data
4. ✅ Review the API documentation in README.md

### Build a Frontend
Now that your backend is ready, you can build a frontend:

**Option 1: React**
```bash
npx create-react-app frontend
cd frontend
npm start
```
Frontend will run on: http://localhost:3000

**Option 2: Angular**
```bash
npm install -g @angular/cli
ng new frontend
cd frontend
ng serve
```
Frontend will run on: http://localhost:4200

**Option 3: Vue**
```bash
npm create vue@latest frontend
cd frontend
npm install
npm run dev
```
Frontend will run on: http://localhost:5173

**API Base URL for Frontend:**
```javascript
const API_BASE_URL = 'http://localhost:8080/api';
```

### Future Enhancements
- Add authentication (Spring Security + JWT)
- Switch to production database (PostgreSQL/MySQL)
- Add unit and integration tests
- Implement pagination
- Add API documentation (Swagger)
- Containerize with Docker
- Deploy to cloud (AWS/Azure/GCP)

## 🆘 Troubleshooting

### Problem: "mvn: command not found"
**Solution:** Maven is not installed. Go back to Step 1 and install Maven.

### Problem: "Unable to locate a Java Runtime"
**Solution:** Java is not installed. Go back to Step 1 and install Java 17.

### Problem: Port 8080 already in use
**Solution:** Another application is using port 8080.
- Stop the other application, OR
- Change port in `src/main/resources/application.properties`:
  ```properties
  server.port=8081
  ```

### Problem: Build fails
**Solution:** 
1. Check Java version: `java -version` (should be 17+)
2. Clean Maven cache: `mvn clean`
3. Try again: `mvn clean install`

### Problem: Can't connect to H2 console
**Solution:**
- Ensure application is running
- Use exact JDBC URL: `jdbc:h2:mem:forecastdb`
- Username: `sa`
- Password: (empty)

## 📚 Additional Resources

### Documentation Files
- **README.md** - Complete API documentation with all endpoints
- **SETUP.md** - Detailed installation guide with troubleshooting
- **API-TESTING-GUIDE.md** - Comprehensive testing examples
- **PROJECT-SUMMARY.md** - Project overview and architecture

### Useful Commands

**Stop the application:**
Press `Ctrl+C` in the terminal where it's running

**Restart the application:**
```bash
mvn spring-boot:run
```

**Clean build:**
```bash
mvn clean install
```

**Run tests:**
```bash
mvn test
```

**View logs:**
Logs are displayed in the terminal. To change log level, edit `application.properties`:
```properties
logging.level.com.forecast=DEBUG
```

## 💡 Tips

1. **Keep the backend running** while developing the frontend
2. **Use Postman** for easier API testing (import the endpoints)
3. **Check H2 console** to verify data changes
4. **Review logs** in the terminal for debugging
5. **Read API-TESTING-GUIDE.md** for complete testing examples

## 🎓 Learning Resources

- Spring Boot: https://spring.io/guides
- REST API Design: https://restfulapi.net/
- H2 Database: https://www.h2database.com/
- Maven: https://maven.apache.org/guides/

## ✅ Checklist

Before moving to frontend development, ensure:

- [ ] Java 17 is installed and working
- [ ] Maven is installed and working
- [ ] Backend application starts without errors
- [ ] Can access http://localhost:8080/api/resources
- [ ] Can access H2 console at http://localhost:8080/h2-console
- [ ] Tested creating a resource via API
- [ ] Tested creating a forecast via API
- [ ] Reviewed API documentation

## 🤝 Need Help?

If you encounter issues:
1. Check the troubleshooting section above
2. Review SETUP.md for detailed installation steps
3. Check application logs in the terminal
4. Verify all prerequisites are correctly installed

---

**You're all set!** Your backend is ready for testing and frontend development.

Good luck with your Resource Labor Forecast application! 🚀

---
Made with Bob