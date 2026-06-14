# Deploy to Render - Step-by-Step Guide

This guide will help you deploy both backend and frontend to Render in about 15 minutes.

## Why Render?

- ✅ Free tier available
- ✅ Easy setup - no complex configuration
- ✅ Automatic HTTPS
- ✅ Can host both backend and frontend
- ✅ GitHub integration
- ✅ Automatic deployments

## Prerequisites

- GitHub account (free)
- Render account (free) - we'll create this together

## 🚀 Deployment Steps

### Step 1: Prepare Your Code (5 minutes)

#### 1.1 Initialize Git Repository

```bash
cd /Users/sudarshan/Desktop
git init
git add .
git commit -m "Initial commit: Resource Labor Forecast application"
```

#### 1.2 Create GitHub Repository

1. Go to https://github.com/new
2. Repository name: `resource-labor-forecast`
3. Description: `Resource Labor Forecast Application`
4. Keep it **Public** (required for Render free tier)
5. Click "Create repository"

#### 1.3 Push to GitHub

```bash
git remote add origin https://github.com/YOUR_USERNAME/resource-labor-forecast.git
git branch -M main
git push -u origin main
```

Replace `YOUR_USERNAME` with your GitHub username.

### Step 2: Deploy Backend to Render (5 minutes)

#### 2.1 Create Render Account

1. Go to https://render.com
2. Click "Get Started"
3. Sign up with GitHub (easiest option)
4. Authorize Render to access your repositories

#### 2.2 Create Backend Web Service

1. Click "New +" → "Web Service"
2. Connect your `resource-labor-forecast` repository
3. Configure the service:

**Basic Settings:**
- **Name**: `resource-forecast-backend`
- **Region**: Choose closest to you
- **Branch**: `main`
- **Root Directory**: `backend`
- **Runtime**: `Java`

**Build Settings:**
- **Build Command**: `mvn clean install -DskipTests`
- **Start Command**: `java -jar target/resource-labor-forecast-1.0.0.jar`

**Instance Type:**
- Select **Free** tier

#### 2.3 Add Environment Variables

Click "Advanced" → "Add Environment Variable":

```
SPRING_PROFILES_ACTIVE = prod
PORT = 8080
```

#### 2.4 Deploy

1. Click "Create Web Service"
2. Wait 5-10 minutes for deployment
3. You'll get a URL like: `https://resource-forecast-backend.onrender.com`

**Save this URL!** You'll need it for the frontend.

#### 2.5 Test Backend

Once deployed, test your backend:

```bash
curl https://resource-forecast-backend.onrender.com/api/resources
```

You should see JSON with sample resources.

### Step 3: Deploy Frontend to Render (5 minutes)

#### 3.1 Create Frontend Static Site

1. In Render dashboard, click "New +" → "Static Site"
2. Connect your `resource-labor-forecast` repository
3. Configure the site:

**Basic Settings:**
- **Name**: `resource-forecast-frontend`
- **Branch**: `main`
- **Root Directory**: `frontend`

**Build Settings:**
- **Build Command**: `npm install && npm run build`
- **Publish Directory**: `build`

#### 3.2 Add Environment Variable

Click "Advanced" → "Add Environment Variable":

```
REACT_APP_API_URL = https://resource-forecast-backend.onrender.com/api
```

**Important:** Replace with your actual backend URL from Step 2.4

#### 3.3 Deploy

1. Click "Create Static Site"
2. Wait 3-5 minutes for deployment
3. You'll get a URL like: `https://resource-forecast-frontend.onrender.com`

### Step 4: Update Backend CORS (2 minutes)

Now that you have your frontend URL, update the backend CORS configuration.

#### 4.1 Update CorsConfig.java

Edit `backend/src/main/java/com/forecast/config/CorsConfig.java`:

Find this line:
```java
.allowedOrigins("http://localhost:3000")
```

Change to:
```java
.allowedOrigins(
    "http://localhost:3000",
    "https://resource-forecast-frontend.onrender.com"  // Add your frontend URL
)
```

#### 4.2 Commit and Push

```bash
git add .
git commit -m "Update CORS for production frontend"
git push
```

Render will automatically redeploy your backend!

### Step 5: Test Your Application (2 minutes)

1. Open your frontend URL: `https://resource-forecast-frontend.onrender.com`
2. You should see the dashboard
3. Try these actions:
   - View resources
   - Add a new resource
   - View forecasts
   - Add a new forecast

## 🎉 You're Live!

Your application is now deployed and accessible worldwide!

**Your URLs:**
- **Frontend**: https://resource-forecast-frontend.onrender.com
- **Backend API**: https://resource-forecast-backend.onrender.com/api

## 📝 Important Notes

### Free Tier Limitations

**Render Free Tier:**
- Backend spins down after 15 minutes of inactivity
- First request after spin-down takes 30-60 seconds
- 750 hours/month (enough for one service)
- Automatic HTTPS included

**To keep backend always active:**
- Upgrade to paid plan ($7/month)
- Or use a service like UptimeRobot to ping it every 10 minutes

### Database Persistence

**Current Setup:**
- H2 in-memory database
- Data resets when backend restarts
- Fine for testing/demo

**For Production:**
- Add PostgreSQL database in Render
- Update backend configuration
- See "Upgrade to PostgreSQL" section below

## 🔧 Troubleshooting

### Backend Issues

**Problem:** Backend deployment fails
- Check build logs in Render dashboard
- Verify Java 17 is being used
- Check `pom.xml` for errors

**Problem:** Backend returns 500 errors
- Check application logs in Render dashboard
- Verify environment variables are set
- Check database connection

### Frontend Issues

**Problem:** Can't connect to backend
- Verify `REACT_APP_API_URL` is set correctly
- Check browser console for CORS errors
- Ensure backend URL is correct (with `/api`)

**Problem:** Blank page
- Check build logs in Render dashboard
- Verify build command is correct
- Check browser console for errors

### CORS Issues

**Problem:** CORS errors in browser
- Verify backend CORS includes frontend URL
- Check that URL matches exactly (https, no trailing slash)
- Redeploy backend after CORS changes

## 🔄 Making Updates

### Update Backend

1. Make changes to backend code
2. Commit and push:
   ```bash
   git add .
   git commit -m "Update backend"
   git push
   ```
3. Render auto-deploys in 5-10 minutes

### Update Frontend

1. Make changes to frontend code
2. Commit and push:
   ```bash
   git add .
   git commit -m "Update frontend"
   git push
   ```
3. Render auto-deploys in 3-5 minutes

## 📊 Monitoring

### View Logs

**Backend Logs:**
1. Go to Render dashboard
2. Select your backend service
3. Click "Logs" tab

**Frontend Logs:**
1. Go to Render dashboard
2. Select your frontend site
3. Click "Logs" tab

### Check Status

- Render dashboard shows service status
- Green = Running
- Yellow = Deploying
- Red = Failed

## 🔐 Security Best Practices

1. **Environment Variables**: Never commit sensitive data
2. **CORS**: Only allow your frontend URL
3. **HTTPS**: Render provides this automatically
4. **Database**: Use strong passwords for production DB

## 💾 Upgrade to PostgreSQL (Optional)

For data persistence:

### 1. Add PostgreSQL in Render

1. In Render dashboard, click "New +" → "PostgreSQL"
2. Name: `resource-forecast-db`
3. Select Free tier
4. Click "Create Database"
5. Copy the "Internal Database URL"

### 2. Update Backend

Add to backend environment variables:
```
DATABASE_URL = [paste internal database URL]
DB_DRIVER = org.postgresql.Driver
DB_DIALECT = org.hibernate.dialect.PostgreSQLDialect
```

### 3. Add PostgreSQL Dependency

Edit `backend/pom.xml`, add:
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

### 4. Deploy

```bash
git add .
git commit -m "Add PostgreSQL support"
git push
```

## 🎯 Custom Domain (Optional)

### Add Custom Domain

1. Purchase domain from Namecheap, GoDaddy, etc.
2. In Render dashboard:
   - Select your service
   - Go to "Settings" → "Custom Domain"
   - Add your domain
   - Update DNS records as instructed

## 📈 Scaling

### Upgrade Plans

**When to upgrade:**
- Need always-on backend
- Need more resources
- Need faster builds
- Need team features

**Pricing:**
- Starter: $7/month per service
- Standard: $25/month per service
- Pro: $85/month per service

## 🎊 Success Checklist

- [ ] GitHub repository created
- [ ] Code pushed to GitHub
- [ ] Render account created
- [ ] Backend deployed successfully
- [ ] Backend URL obtained
- [ ] Frontend deployed successfully
- [ ] Frontend URL obtained
- [ ] CORS updated with frontend URL
- [ ] Application tested and working
- [ ] URLs shared with team

## 📞 Support

**Render Support:**
- Documentation: https://render.com/docs
- Community: https://community.render.com
- Status: https://status.render.com

**Application Issues:**
- Check logs in Render dashboard
- Review troubleshooting section
- Verify environment variables

## 🚀 Next Steps

After successful deployment:

1. ✅ Share your URLs with team
2. ✅ Set up monitoring (UptimeRobot)
3. ✅ Consider upgrading to PostgreSQL
4. ✅ Add custom domain (optional)
5. ✅ Set up automated backups
6. ✅ Add authentication (future)

---

## Quick Reference

**Your Deployment URLs:**
```
Frontend: https://resource-forecast-frontend.onrender.com
Backend:  https://resource-forecast-backend.onrender.com
API:      https://resource-forecast-backend.onrender.com/api
```

**Common Commands:**
```bash
# Push updates
git add .
git commit -m "Your message"
git push

# View logs
# Use Render dashboard

# Test backend
curl https://your-backend-url.onrender.com/api/resources
```

---

**Congratulations!** Your application is now live on the internet! 🎉

**Made with Bob** 🤖