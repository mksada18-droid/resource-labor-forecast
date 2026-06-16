# 🚀 Deploy Your Application - Simple 5-Step Guide

Follow these exact steps to deploy your application to the cloud.

---

## 📋 What You Need

1. **GitHub Account** (free) - https://github.com
2. **Render Account** (free) - https://render.com
3. **15 minutes**

---

## 🎯 STEP 1: Push Code to GitHub (5 minutes)

### 1.1 Create GitHub Repository

1. Go to: https://github.com/new
2. Fill in:
   - **Repository name**: `resource-labor-forecast`
   - **Description**: `Resource Labor Forecast Application`
   - **Visibility**: **Public** ✅ (required for free Render)
   - **DO NOT** check "Initialize with README"
3. Click **"Create repository"**

### 1.2 Push Your Code

GitHub will show you commands. Copy and run these in Terminal:

```bash
cd /Users/sudarshan/Desktop

# Add all files
git add backend/ frontend/ *.md .gitignore

# Commit
git commit -m "Resource Labor Forecast Application - Ready for deployment"

# Add GitHub remote (REPLACE YOUR_USERNAME with your actual GitHub username)
git remote add origin https://github.com/YOUR_USERNAME/resource-labor-forecast.git

# Push to GitHub
git push -u origin main
```

**⚠️ IMPORTANT:** Replace `YOUR_USERNAME` with your actual GitHub username!

### 1.3 Verify

Go to your GitHub repository URL and verify you see your code.

✅ **Checkpoint:** Code is on GitHub!

---

## 🎯 STEP 2: Deploy Backend to Render (5 minutes)

### 2.1 Create Render Account

1. Go to: https://render.com
2. Click **"Get Started"**
3. Choose **"Sign up with GitHub"** (easiest!)
4. Authorize Render to access your repositories

### 2.2 Create Backend Web Service

1. In Render dashboard, click **"New +"** → **"Web Service"**
2. Click **"Connect a repository"**
3. Find and select: `resource-labor-forecast`
4. Click **"Connect"**

### 2.3 Configure Backend

Fill in these exact settings:

| Setting | Value |
|---------|-------|
| **Name** | `resource-forecast-backend` |
| **Region** | Choose closest to you |
| **Branch** | `main` |
| **Root Directory** | `backend` |
| **Runtime** | `Java` |
| **Build Command** | `mvn clean install -DskipTests` |
| **Start Command** | `java -jar target/resource-labor-forecast-1.0.0.jar` |
| **Instance Type** | **Free** |

### 2.4 Add Environment Variables

Click **"Advanced"** → **"Add Environment Variable"**

Add these TWO variables:

**Variable 1:**
- Key: `SPRING_PROFILES_ACTIVE`
- Value: `prod`

**Variable 2:**
- Key: `PORT`
- Value: `8080`

### 2.5 Deploy Backend

1. Click **"Create Web Service"**
2. Wait 5-10 minutes (watch the logs)
3. Status will change to **"Live"** with a green dot

### 2.6 Get Backend URL

You'll see a URL like:
```
https://resource-forecast-backend.onrender.com
```

**📝 COPY THIS URL!** You need it for Step 3.

### 2.7 Test Backend

Open in browser:
```
https://resource-forecast-backend.onrender.com/api/resources
```

You should see JSON with sample resources!

✅ **Checkpoint:** Backend is live!

---

## 🎯 STEP 3: Deploy Frontend to Render (4 minutes)

### 3.1 Create Frontend Static Site

1. In Render dashboard, click **"New +"** → **"Static Site"**
2. Select your `resource-labor-forecast` repository
3. Click **"Connect"**

### 3.2 Configure Frontend

Fill in these exact settings:

| Setting | Value |
|---------|-------|
| **Name** | `resource-forecast-frontend` |
| **Branch** | `main` |
| **Root Directory** | `frontend` |
| **Build Command** | `npm install && npm run build` |
| **Publish Directory** | `build` |

### 3.3 Add Environment Variable

Click **"Advanced"** → **"Add Environment Variable"**

**Key:** `REACT_APP_API_URL`

**Value:** `https://resource-forecast-backend.onrender.com/api`

**⚠️ IMPORTANT:** Use YOUR actual backend URL from Step 2.6!

### 3.4 Deploy Frontend

1. Click **"Create Static Site"**
2. Wait 3-5 minutes
3. Status will change to **"Live"**

### 3.5 Get Frontend URL

You'll see a URL like:
```
https://resource-forecast-frontend.onrender.com
```

**🎉 This is your live application URL!**

✅ **Checkpoint:** Frontend is live!

---

## 🎯 STEP 4: Update Backend CORS (2 minutes)

Now update backend to allow your frontend URL.

### Option A: Via Render Dashboard (Quick)

1. Go to your backend service in Render
2. Click **"Environment"** tab
3. Add new variable:
   - Key: `ALLOWED_ORIGINS`
   - Value: `https://resource-forecast-frontend.onrender.com`
4. Click **"Save Changes"**
5. Backend will auto-redeploy

### Option B: Via Code (Better)

1. Edit `backend/src/main/java/com/forecast/config/CorsConfig.java`
2. Find line with `.allowedOrigins("http://localhost:3000")`
3. Change to:
```java
.allowedOrigins(
    "http://localhost:3000",
    "https://resource-forecast-frontend.onrender.com"
)
```
4. Save file
5. Commit and push:
```bash
cd /Users/sudarshan/Desktop
git add backend/src/main/java/com/forecast/config/CorsConfig.java
git commit -m "Update CORS for production"
git push
```
6. Render will auto-redeploy backend

✅ **Checkpoint:** CORS updated!

---

## 🎯 STEP 5: Test Your Live Application! (1 minute)

### 5.1 Open Your Application

Go to: `https://resource-forecast-frontend.onrender.com`

(Use your actual URL)

### 5.2 Test Features

✅ Dashboard loads with statistics
✅ Click "Resources" - see 4 sample resources
✅ Click "+ Add Resource" - create a new one
✅ Click "Forecasts" - see sample forecasts
✅ Click "+ Add Forecast" - create a new one
✅ Edit and delete work

---

## 🎉 SUCCESS! You're Live!

### Your URLs:

**Frontend (Share this!):**
```
https://resource-forecast-frontend.onrender.com
```

**Backend API:**
```
https://resource-forecast-backend.onrender.com/api
```

---

## 📱 Share Your Application

Share your frontend URL with:
- ✅ Your team
- ✅ Your manager
- ✅ Your portfolio
- ✅ LinkedIn
- ✅ Resume

---

## ⚠️ Important Notes

### Free Tier Behavior

**Backend Spin-Down:**
- Free tier spins down after 15 minutes of inactivity
- First request after spin-down takes 30-60 seconds
- This is normal for free tier

**Data Persistence:**
- H2 in-memory database resets on restart
- For permanent data, upgrade to PostgreSQL

### Keep Backend Active

To prevent spin-down:
1. Upgrade to paid plan ($7/month)
2. Use UptimeRobot (free) to ping every 10 minutes

---

## 🔄 Making Updates

### Update Your Application

1. Make changes to code
2. Commit and push:
```bash
cd /Users/sudarshan/Desktop
git add .
git commit -m "Your update message"
git push
```
3. Render auto-deploys!
   - Backend: 5-10 minutes
   - Frontend: 3-5 minutes

---

## 🐛 Troubleshooting

### Backend Not Responding
- Check Render dashboard logs
- Verify environment variables
- Wait for initial deployment (10 min)

### Frontend Shows Errors
- Check browser console (F12)
- Verify `REACT_APP_API_URL` is correct
- Ensure backend is running

### CORS Errors
- Verify backend CORS includes frontend URL
- Check URL matches exactly
- Redeploy backend after changes

### Can't Push to GitHub
- Verify you replaced `YOUR_USERNAME`
- Check repository is public
- Try: `git remote -v`

---

## ✅ Deployment Checklist

- [ ] GitHub account created
- [ ] GitHub repository created
- [ ] Code pushed to GitHub
- [ ] Render account created
- [ ] Backend deployed
- [ ] Backend URL obtained
- [ ] Frontend deployed
- [ ] Frontend URL obtained
- [ ] Environment variable set
- [ ] CORS updated
- [ ] Application tested
- [ ] URLs shared

---

## 🎊 Congratulations!

Your application is now:
- ✅ Live on the internet
- ✅ Accessible from anywhere
- ✅ Free to use
- ✅ Automatically deployed on updates

**Enjoy your application!** 🚀

---

**Made with Bob** 🤖