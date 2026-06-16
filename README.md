# Resource Labor Forecast Application

A full-stack web application for managing resources (team members) and forecasting their weekly labor hours.

## 🌟 Features

- **Dashboard**: Overview with key statistics and metrics
- **Resource Management**: Add, edit, and delete team members
- **Forecast Management**: Create and manage weekly hour forecasts
- **Modern UI**: Clean, responsive design with gradient accents
- **Real-time Updates**: Instant feedback on all operations
- **RESTful API**: Well-documented backend API

## 🏗️ Architecture

### Backend
- **Framework**: Spring Boot 3.1.5
- **Language**: Java 17
- **Database**: H2 (development) / PostgreSQL (production)
- **API**: RESTful with JSON responses

### Frontend
- **Framework**: React 18.2
- **Styling**: Modern CSS with gradients
- **HTTP Client**: Axios
- **Routing**: Simple view-based navigation

## 📁 Project Structure

```
resource-labor-forecast/
├── backend/                    # Spring Boot backend
│   ├── src/
│   │   ├── main/java/com/forecast/
│   │   │   ├── controller/    # REST controllers
│   │   │   ├── model/         # Entity models
│   │   │   ├── repository/    # Data access
│   │   │   ├── service/       # Business logic
│   │   │   └── config/        # Configuration
│   │   └── resources/
│   │       ├── application.properties
│   │       └── schema.sql
│   ├── pom.xml
│   └── README.md
├── frontend/                   # React frontend
│   ├── public/
│   ├── src/
│   │   ├── components/        # React components
│   │   ├── services/          # API services
│   │   ├── styles/            # CSS files
│   │   ├── App.js
│   │   └── index.js
│   ├── package.json
│   └── README.md
├── DEPLOYMENT-GUIDE.md        # Deployment instructions
└── README.md                  # This file
```

## 🚀 Quick Start

### Prerequisites

**Backend:**
- Java 17 or higher
- Maven 3.6 or higher

**Frontend:**
- Node.js 16+ and npm

### Running Locally

#### 1. Start the Backend

```bash
cd backend
./run.sh          # macOS/Linux
# or
run.bat           # Windows
```

Backend will run on: http://localhost:8080

#### 2. Start the Frontend

```bash
cd frontend
npm install
npm start
```

Frontend will run on: http://localhost:3000

### Verify Installation

1. Backend API: http://localhost:8080/api/resources
2. H2 Console: http://localhost:8080/h2-console
3. Frontend: http://localhost:3000

## 📚 Documentation

- **[Backend README](backend/README.md)** - Backend API documentation
- **[Frontend README](frontend/README.md)** - Frontend documentation
- **[Setup Guide](backend/SETUP.md)** - Detailed installation instructions
- **[API Testing Guide](backend/API-TESTING-GUIDE.md)** - API testing examples
- **[Deployment Guide](DEPLOYMENT-GUIDE.md)** - Cloud deployment instructions

## 🌐 Deployment

The application can be deployed to various cloud platforms:

### Recommended Setup
- **Backend**: Railway or Render (free tier available)
- **Frontend**: Vercel or Netlify (free tier available)

See [DEPLOYMENT-GUIDE.md](DEPLOYMENT-GUIDE.md) for detailed instructions.

## 🔧 Configuration

### Backend Configuration

Edit `backend/src/main/resources/application.properties`:

```properties
server.port=8080
spring.datasource.url=jdbc:h2:mem:forecastdb
```

### Frontend Configuration

Create `frontend/.env`:

```env
REACT_APP_API_URL=http://localhost:8080/api
```

For production, update to your deployed backend URL.

## 📊 API Endpoints

### Resources
- `GET /api/resources` - Get all resources
- `GET /api/resources/{id}` - Get resource by ID
- `POST /api/resources` - Create resource
- `PUT /api/resources/{id}` - Update resource
- `DELETE /api/resources/{id}` - Delete resource

### Forecasts
- `GET /api/forecasts` - Get all forecasts
- `GET /api/forecasts/{id}` - Get forecast by ID
- `GET /api/forecasts/resource/{resourceId}` - Get forecasts by resource
- `GET /api/forecasts/week/{weekEndingDate}` - Get forecasts by week
- `POST /api/forecasts` - Create forecast
- `PUT /api/forecasts/{id}` - Update forecast
- `DELETE /api/forecasts/{id}` - Delete forecast

## 🧪 Testing

### Backend Testing

```bash
cd backend
mvn test
```

### Frontend Testing

```bash
cd frontend
npm test
```

### Manual API Testing

See [API-TESTING-GUIDE.md](backend/API-TESTING-GUIDE.md) for curl examples.

## 🎨 UI Features

- **Gradient Header**: Purple gradient with white text
- **Responsive Design**: Works on mobile and desktop
- **Modal Forms**: Clean modal dialogs for create/edit
- **Table Views**: Organized data display
- **Statistics Cards**: Dashboard metrics
- **Smooth Animations**: Hover effects and transitions

## 🔐 Security Notes

- CORS is configured for localhost development
- Update CORS settings for production deployment
- Consider adding authentication for production use
- Use environment variables for sensitive data

## 🚧 Future Enhancements

- [ ] User authentication and authorization
- [ ] Role-based access control
- [ ] Advanced reporting and analytics
- [ ] Export to Excel/CSV
- [ ] Email notifications
- [ ] Calendar integration
- [ ] Mobile app
- [ ] Real-time collaboration
- [ ] Audit logging
- [ ] Data visualization charts

## 🐛 Troubleshooting

### Backend Issues

**Port 8080 already in use:**
```bash
# Change port in application.properties
server.port=8081
```

**Java/Maven not found:**
- See [SETUP.md](backend/SETUP.md) for installation instructions

### Frontend Issues

**Cannot connect to backend:**
- Ensure backend is running on port 8080
- Check `REACT_APP_API_URL` in `.env`
- Verify CORS configuration

**npm command not found:**
- Install Node.js from https://nodejs.org

## 📝 License

This project is part of the Resource Labor Forecast Hours system.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📧 Support

For issues or questions:
- Check the documentation files
- Review the troubleshooting section
- Check application logs

## 🎉 Acknowledgments

Built with:
- Spring Boot
- React
- H2 Database
- Maven
- Axios

---

**Made with Bob** 🤖

## Quick Links

- [Backend Documentation](backend/README.md)
- [Frontend Documentation](frontend/README.md)
- [Deployment Guide](DEPLOYMENT-GUIDE.md)
- [API Testing Guide](backend/API-TESTING-GUIDE.md)
- [Setup Instructions](backend/SETUP.md)# resource-labor-forecast
