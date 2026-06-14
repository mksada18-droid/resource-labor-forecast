# Resource Labor Forecast - Frontend

A modern React application for managing resources and forecasting weekly labor hours.

## Features

- 📊 **Dashboard** - Overview of resources and forecasts with key statistics
- 👥 **Resource Management** - Add, edit, and delete team members
- 📅 **Forecast Management** - Create and manage weekly hour forecasts
- 🎨 **Modern UI** - Clean, responsive design with gradient accents
- 🔄 **Real-time Updates** - Instant feedback on all operations

## Technology Stack

- React 18.2
- Axios for API calls
- Modern CSS with gradients and animations
- Responsive design for mobile and desktop

## Getting Started

### Prerequisites

- Node.js 16+ and npm

### Installation

```bash
# Install dependencies
npm install

# Start development server
npm start
```

The application will open at http://localhost:3000

### Environment Variables

Create a `.env` file in the frontend directory:

```env
REACT_APP_API_URL=http://localhost:8080/api
```

For production, update this to your deployed backend URL.

## Building for Production

```bash
npm run build
```

This creates an optimized production build in the `build` folder.

## Deployment

### Deploy to Vercel (Recommended)

1. Install Vercel CLI:
```bash
npm install -g vercel
```

2. Deploy:
```bash
vercel
```

3. Set environment variable in Vercel dashboard:
   - `REACT_APP_API_URL` = your backend URL

### Deploy to Netlify

1. Install Netlify CLI:
```bash
npm install -g netlify-cli
```

2. Build and deploy:
```bash
npm run build
netlify deploy --prod --dir=build
```

3. Set environment variable in Netlify dashboard:
   - `REACT_APP_API_URL` = your backend URL

## Project Structure

```
frontend/
├── public/
│   └── index.html
├── src/
│   ├── components/
│   │   ├── Dashboard.js      # Dashboard with statistics
│   │   ├── Resources.js      # Resource management
│   │   └── Forecasts.js      # Forecast management
│   ├── services/
│   │   └── api.js            # API service layer
│   ├── styles/
│   │   └── App.css           # Global styles
│   ├── App.js                # Main app component
│   └── index.js              # Entry point
├── package.json
└── README.md
```

## API Integration

The frontend connects to the backend API at the URL specified in `REACT_APP_API_URL`.

### API Endpoints Used

- `GET /api/resources` - Get all resources
- `POST /api/resources` - Create resource
- `PUT /api/resources/:id` - Update resource
- `DELETE /api/resources/:id` - Delete resource
- `GET /api/forecasts` - Get all forecasts
- `POST /api/forecasts` - Create forecast
- `PUT /api/forecasts/:id` - Update forecast
- `DELETE /api/forecasts/:id` - Delete forecast

## Features in Detail

### Dashboard
- Total resources count
- Total forecasts count
- Current week hours
- Average hours per resource

### Resources
- View all team members
- Add new resources with name, email, and department
- Edit existing resources
- Delete resources (cascades to forecasts)

### Forecasts
- View all weekly forecasts
- Create forecasts for specific resources and weeks
- Edit forecast hours and notes
- Delete forecasts
- Automatic week ending date calculation

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## License

Part of the Resource Labor Forecast Hours system.