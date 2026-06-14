import axios from 'axios';

// API base URL - will be configured based on environment
const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Resource API calls
export const resourceAPI = {
  getAll: () => api.get('/resources'),
  getById: (id) => api.get(`/resources/${id}`),
  create: (data) => api.post('/resources', data),
  update: (id, data) => api.put(`/resources/${id}`, data),
  delete: (id) => api.delete(`/resources/${id}`),
};

// Forecast API calls
export const forecastAPI = {
  getAll: () => api.get('/forecasts'),
  getById: (id) => api.get(`/forecasts/${id}`),
  getByResource: (resourceId) => api.get(`/forecasts/resource/${resourceId}`),
  getByWeek: (weekEndingDate) => api.get(`/forecasts/week/${weekEndingDate}`),
  create: (data) => api.post('/forecasts', data),
  update: (id, data) => api.put(`/forecasts/${id}`, data),
  delete: (id) => api.delete(`/forecasts/${id}`),
};

export default api;

// Made with Bob
