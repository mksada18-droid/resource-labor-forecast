# Quick Start Guide - Resource Labor Forecast Application

Get your application running in 5 minutes!

## 🎯 What You'll Get

A complete full-stack application with:
- ✅ Backend API (Spring Boot)
- ✅ Frontend UI (React)
- ✅ Database (H2 in-memory)
- ✅ Sample data pre-loaded

## 📋 Prerequisites Check

Before starting, ensure you have:

### For Backend:
- [ ] Java 17+ installed (`java -version`)
- [ ] Maven 3.6+ installed (`mvn -version`)

### For Frontend:
- [ ] Node.js 16+ installed (`node -version`)
- [ ] npm installed (`npm -version`)

**Don't have these?** See [Installation Guide](#installation-guide) below.

## 🚀 Start in 3 Steps

### Step 1: Start Backend (Terminal 1)

```bash
cd /Users/sudarshan/Desktop/backend
./run.sh
```

**Windows users:** Use `run.bat` instead

**Expected output:**
```
===========================================
Resource Labor Forecast Application Started
===========================================
Server running on: http://localhost:8080
```

✅ Backend is ready when you see this message!

### Step 2: Start Frontend (Terminal 2)

Open a **new terminal window**:

```bash
cd /Users/sudarshan/Desktop/frontend
npm install
npm start
```

**Expected output:**
```
Compiled successfully!
You can now view the app in the browser.
Local: http://localhost:3000
```

✅ Frontend is ready! Your browser should open automatically.

### Step 3: Use the Application

Open http://localhost:3000 in your browser.

You should see:
- 📊 Dashboard with statistics
- 👥 Resources tab (4 sample resources)
- 📅 Forecasts tab (sample forecasts)

## 🎮 Try These Actions

1. **View Resources**
   - Click "Resources" tab
   - See 4 pre-loaded team members

2. **Add a Resource**
   - Click "+ Add Resource"
   - Fill in: Name, Email, Department
   - Click "Create"

3. **View Forecasts**
   - Click "Forecasts" tab
   - See weekly hour forecasts

4. **Add a Forecast**
   - Click "+ Add Forecast"
   - Select a resource
   - Set week ending date
   - Enter hours (0-168)
   - Click "Create"

5. **Check Dashboard**
   - Click "Dashboard"
   - See updated statistics

## 🔍 Verify Everything Works

### Backend Health Check
```bash
curl http://localhost:8080/api/resources
```

Should return JSON with resources.

### Database Console
Open: http://localhost:8080/h2-console

Login with:
- JDBC URL: `jdbc:h2:mem:forecastdb`
- Username: `sa`
- Password: (leave empty)

### Frontend
Open: http://localhost:3000

Should show the dashboard.

## 🛑 Stopping the Application

### Stop Backend
In Terminal 1: Press `Ctrl+C`

### Stop Frontend
In Terminal 2: Press `Ctrl+C`

## 📱 What's Next?

### Local Development
- Backend runs on: http://localhost:8080
- Frontend runs on: http://localhost:3000
- Make changes and see them live!

### Deploy to Cloud
Ready to deploy? See [DEPLOYMENT-GUIDE.md](DEPLOYMENT-GUIDE.md)

### Learn More
- [Backend API Documentation](backend/README.md)
- [Frontend Documentation](frontend/README.md)
- [API Testing Guide](backend/API-TESTING-GUIDE.md)

## 🐛 Troubleshooting

### Backend Won't Start

**Problem:** "mvn: command not found"
```bash
# Install Maven
brew install maven  # macOS
```

**Problem:** "Unable to locate a Java Runtime"
```bash
# Install Java 17
brew install openjdk@17  # macOS
```

**Problem:** "Port 8080 already in use"
```bash
# Find and kill the process
lsof -ti:8080 | xargs kill -9
```

### Frontend Won't Start

**Problem:** "npm: command not found"
- Install Node.js from https://nodejs.org

**Problem:** "Cannot connect to backend"
- Ensure backend is running on port 8080
- Check terminal 1 for backend status

**Problem:** "Module not found"
```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
```

### Browser Issues

**Problem:** Blank page
- Check browser console (F12)
- Ensure backend is running
- Clear browser cache

**Problem:** CORS errors
- Backend CORS is pre-configured for localhost:3000
- Restart backend if you changed CORS settings

## 📚 Installation Guide

### Install Java 17 (macOS)

```bash
# Using Homebrew
brew install openjdk@17

# Add to PATH
echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc

# Verify
java -version
```

### Install Maven (macOS)

```bash
# Using Homebrew
brew install maven

# Verify
mvn -version
```

### Install Node.js (macOS)

```bash
# Using Homebrew
brew install node

# Verify
node -version
npm -version
```

### Windows Installation

1. **Java 17**
   - Download from: https://adoptium.net/
   - Run installer
   - Add to PATH

2. **Maven**
   - Download from: https://maven.apache.org/download.cgi
   - Extract and add to PATH

3. **Node.js**
   - Download from: https://nodejs.org
   - Run installer

## 🎓 Understanding the Application

### Backend (Spring Boot)
- **Port**: 8080
- **API Base**: http://localhost:8080/api
- **Database**: H2 in-memory (resets on restart)
- **Sample Data**: Auto-loaded on startup

### Frontend (React)
- **Port**: 3000
- **Framework**: React 18
- **Styling**: Modern CSS with gradients
- **API Client**: Axios

### Data Flow
```
Browser → React Frontend (3000)
    ↓
    HTTP Requests
    ↓
Spring Boot Backend (8080)
    ↓
H2 Database (in-memory)
```

## 💡 Tips

1. **Keep both terminals open** while developing
2. **Backend changes** require restart
3. **Frontend changes** auto-reload in browser
4. **Database resets** when backend restarts
5. **Check logs** in terminals for errors

## 🎉 Success Checklist

- [ ] Backend started successfully
- [ ] Frontend opened in browser
- [ ] Can view dashboard
- [ ] Can see sample resources
- [ ] Can see sample forecasts
- [ ] Can create new resource
- [ ] Can create new forecast
- [ ] Can edit and delete items

## 📞 Need Help?

1. Check the troubleshooting section above
2. Review [SETUP.md](backend/SETUP.md) for detailed setup
3. Check application logs in terminals
4. Verify all prerequisites are installed

## 🚀 Ready to Deploy?

Once everything works locally, deploy to the cloud:

1. Read [DEPLOYMENT-GUIDE.md](DEPLOYMENT-GUIDE.md)
2. Choose your platforms (Railway + Vercel recommended)
3. Follow step-by-step deployment instructions
4. Get your live URLs!

---

**Congratulations!** You now have a fully functional Resource Labor Forecast application running locally! 🎊

**Next Steps:**
- Explore the features
- Make some changes
- Deploy to production
- Share with your team

---

Made with Bob 🤖