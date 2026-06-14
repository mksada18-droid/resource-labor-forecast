# Deployment Guide - Resource Labor Forecast Application

This guide will help you deploy both the backend and frontend to cloud services.

## Overview

We'll deploy:
- **Backend** (Spring Boot) → Railway or Render
- **Frontend** (React) → Vercel or Netlify

## Prerequisites

- Git installed
- GitHub account (for code hosting)
- Accounts on deployment platforms (free tiers available)

## Step 1: Prepare Your Code

### 1.1 Initialize Git Repository

```bash
cd /Users/sudarshan/Desktop
git init
git add .
git commit -m "Initial commit: Resource Labor Forecast application"
```

### 1.2 Create GitHub Repository

1. Go to https://github.com/new
2. Create a new repository named `resource-labor-forecast`
3. Push your code:

```bash
git remote add origin https://github.com/YOUR_USERNAME/resource-labor-forecast.git
git branch -M main
git push -u origin main
```

## Step 2: Deploy Backend

### Option A: Deploy to Railway (Recommended)

Railway offers free tier with PostgreSQL database.

#### 2.1 Sign Up
- Go to https://railway.app
- Sign up with GitHub

#### 2.2 Create New Project
1. Click "New Project"
2. Select "Deploy from GitHub repo"
3. Choose your `resource-labor-forecast` repository
4. Railway will auto-detect Spring Boot

#### 2.3 Configure Backend
1. In Railway dashboard, go to your service
2. Click "Variables" tab
3. Add these environment variables:
   ```
   SPRING_PROFILES_ACTIVE=prod
   PORT=8080
   ```

#### 2.4 Add PostgreSQL Database (Optional for Production)
1. Click "New" → "Database" → "PostgreSQL"
2. Railway will automatically set DATABASE_URL
3. Update backend to use PostgreSQL (see below)

#### 2.5 Get Backend URL
- Railway will provide a URL like: `https://your-app.railway.app`
- Save this URL for frontend configuration

### Option B: Deploy to Render

Render also offers free tier.

#### 2.1 Sign Up
- Go to https://render.com
- Sign up with GitHub

#### 2.2 Create Web Service
1. Click "New +" → "Web Service"
2. Connect your GitHub repository
3. Configure:
   - **Name**: resource-labor-forecast-backend
   - **Root Directory**: backend
   - **Build Command**: `mvn clean install`
   - **Start Command**: `java -jar target/resource-labor-forecast-1.0.0.jar`

#### 2.3 Environment Variables
Add in Render dashboard:
```
SPRING_PROFILES_ACTIVE=prod
```

#### 2.4 Get Backend URL
- Render will provide a URL like: `https://resource-labor-forecast-backend.onrender.com`

## Step 3: Update Backend CORS

Update `backend/src/main/java/com/forecast/config/CorsConfig.java`:

```java
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins(
                "http://localhost:3000",
                "https://your-frontend-url.vercel.app",  // Add your frontend URL
                "https://your-frontend-url.netlify.app"   // Or Netlify URL
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
}
```

Commit and push changes:
```bash
git add .
git commit -m "Update CORS for production"
git push
```

## Step 4: Deploy Frontend

### Option A: Deploy to Vercel (Recommended)

#### 4.1 Install Vercel CLI
```bash
npm install -g vercel
```

#### 4.2 Deploy
```bash
cd frontend
vercel
```

Follow the prompts:
- Set up and deploy? **Y**
- Which scope? Select your account
- Link to existing project? **N**
- Project name? **resource-labor-forecast-frontend**
- Directory? **./frontend**
- Override settings? **N**

#### 4.3 Set Environment Variable
1. Go to Vercel dashboard: https://vercel.com/dashboard
2. Select your project
3. Go to "Settings" → "Environment Variables"
4. Add:
   - **Key**: `REACT_APP_API_URL`
   - **Value**: `https://your-backend-url.railway.app/api`
   - **Environment**: Production

#### 4.4 Redeploy
```bash
vercel --prod
```

#### 4.5 Get Frontend URL
- Vercel provides: `https://resource-labor-forecast-frontend.vercel.app`

### Option B: Deploy to Netlify

#### 4.1 Install Netlify CLI
```bash
npm install -g netlify-cli
```

#### 4.2 Build and Deploy
```bash
cd frontend
npm run build
netlify deploy --prod --dir=build
```

#### 4.3 Set Environment Variable
1. Go to Netlify dashboard
2. Select your site
3. Go to "Site settings" → "Environment variables"
4. Add:
   - **Key**: `REACT_APP_API_URL`
   - **Value**: `https://your-backend-url.railway.app/api`

#### 4.4 Redeploy
```bash
netlify deploy --prod --dir=build
```

## Step 5: Update Backend CORS (Again)

Now that you have your frontend URL, update CORS in backend:

1. Edit `backend/src/main/java/com/forecast/config/CorsConfig.java`
2. Add your actual frontend URL
3. Commit and push:
```bash
git add .
git commit -m "Add production frontend URL to CORS"
git push
```

## Step 6: Test Your Deployment

1. Open your frontend URL
2. Verify:
   - Dashboard loads
   - Can view resources
   - Can create/edit/delete resources
   - Can view forecasts
   - Can create/edit/delete forecasts

## Troubleshooting

### Backend Issues

**Problem**: Backend not starting
- Check logs in Railway/Render dashboard
- Verify Java 17 is being used
- Check environment variables

**Problem**: Database errors
- H2 in-memory database resets on restart
- Consider upgrading to PostgreSQL for persistence

### Frontend Issues

**Problem**: Can't connect to backend
- Verify `REACT_APP_API_URL` is set correctly
- Check browser console for CORS errors
- Ensure backend CORS includes frontend URL

**Problem**: Environment variable not working
- Redeploy after setting environment variables
- Clear browser cache
- Check variable name is exactly `REACT_APP_API_URL`

## Upgrading to PostgreSQL (Production)

For production use, upgrade from H2 to PostgreSQL:

### 1. Add PostgreSQL Dependency

Edit `backend/pom.xml`, add:
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

### 2. Create application-prod.properties

Create `backend/src/main/resources/application-prod.properties`:
```properties
# PostgreSQL Configuration
spring.datasource.url=${DATABASE_URL}
spring.datasource.driverClassName=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Disable H2 Console
spring.h2.console.enabled=false

# Logging
logging.level.org.springframework.web=INFO
logging.level.com.forecast=INFO
```

### 3. Deploy
Railway/Render will automatically use PostgreSQL if DATABASE_URL is set.

## Cost Estimates

### Free Tier Limits

**Railway**:
- $5 free credit per month
- Enough for small applications
- PostgreSQL included

**Render**:
- Free tier available
- 750 hours/month
- Spins down after inactivity

**Vercel**:
- 100 GB bandwidth/month
- Unlimited deployments
- Custom domains

**Netlify**:
- 100 GB bandwidth/month
- 300 build minutes/month
- Custom domains

## Custom Domain (Optional)

### For Frontend (Vercel/Netlify)
1. Purchase domain from Namecheap, GoDaddy, etc.
2. Add domain in Vercel/Netlify dashboard
3. Update DNS records as instructed

### For Backend (Railway/Render)
1. Add custom domain in dashboard
2. Update DNS CNAME record
3. Update frontend API URL

## Monitoring and Maintenance

### Backend Monitoring
- Check Railway/Render logs regularly
- Monitor database size
- Set up alerts for errors

### Frontend Monitoring
- Check Vercel/Netlify analytics
- Monitor build times
- Review error logs

## Security Considerations

1. **Environment Variables**: Never commit sensitive data
2. **CORS**: Only allow specific frontend URLs
3. **HTTPS**: Both platforms provide SSL automatically
4. **Database**: Use strong passwords for production DB
5. **API Rate Limiting**: Consider adding rate limiting

## Backup Strategy

### Database Backups
- Railway: Automatic backups on paid plans
- Render: Manual backups via dashboard
- Consider periodic exports

### Code Backups
- GitHub serves as code backup
- Tag releases: `git tag v1.0.0`
- Push tags: `git push --tags`

## Next Steps

After successful deployment:
1. ✅ Test all features thoroughly
2. ✅ Set up monitoring
3. ✅ Configure custom domain (optional)
4. ✅ Add authentication (future enhancement)
5. ✅ Implement analytics (future enhancement)

## Support

For issues:
- Railway: https://railway.app/help
- Render: https://render.com/docs
- Vercel: https://vercel.com/docs
- Netlify: https://docs.netlify.com

---

**Congratulations!** Your Resource Labor Forecast application is now live! 🎉