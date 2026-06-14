import React, { useState, useEffect } from 'react';
import { resourceAPI, forecastAPI } from '../services/api';

function Dashboard() {
  const [stats, setStats] = useState({
    totalResources: 0,
    totalForecasts: 0,
    thisWeekHours: 0,
    avgHoursPerResource: 0,
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const fetchDashboardData = async () => {
    try {
      setLoading(true);
      const [resourcesRes, forecastsRes] = await Promise.all([
        resourceAPI.getAll(),
        forecastAPI.getAll(),
      ]);

      const resources = resourcesRes.data;
      const forecasts = forecastsRes.data;

      // Calculate stats
      const totalHours = forecasts.reduce((sum, f) => sum + parseFloat(f.forecastedHours), 0);
      const avgHours = resources.length > 0 ? (totalHours / resources.length).toFixed(2) : 0;

      // Get current week's forecasts
      const today = new Date();
      const currentWeekEnd = new Date(today);
      currentWeekEnd.setDate(today.getDate() + (7 - today.getDay()));
      const currentWeekEndStr = currentWeekEnd.toISOString().split('T')[0];
      
      const thisWeekForecasts = forecasts.filter(f => f.weekEndingDate === currentWeekEndStr);
      const thisWeekHours = thisWeekForecasts.reduce((sum, f) => sum + parseFloat(f.forecastedHours), 0);

      setStats({
        totalResources: resources.length,
        totalForecasts: forecasts.length,
        thisWeekHours: thisWeekHours.toFixed(2),
        avgHoursPerResource: avgHours,
      });
      setError(null);
    } catch (err) {
      setError('Failed to load dashboard data. Please ensure the backend is running.');
      console.error('Dashboard error:', err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="loading">Loading dashboard</div>;
  }

  return (
    <div>
      <h2>Dashboard</h2>
      
      {error && <div className="error">{error}</div>}

      <div className="stats-grid">
        <div className="stat-card">
          <h3>Total Resources</h3>
          <div className="stat-value">{stats.totalResources}</div>
        </div>

        <div className="stat-card">
          <h3>Total Forecasts</h3>
          <div className="stat-value">{stats.totalForecasts}</div>
        </div>

        <div className="stat-card">
          <h3>This Week Hours</h3>
          <div className="stat-value">{stats.thisWeekHours}</div>
        </div>

        <div className="stat-card">
          <h3>Avg Hours/Resource</h3>
          <div className="stat-value">{stats.avgHoursPerResource}</div>
        </div>
      </div>

      <div className="card">
        <h3>Quick Actions</h3>
        <p>Use the navigation above to manage resources and forecasts.</p>
        <ul style={{ marginTop: '1rem', lineHeight: '1.8' }}>
          <li><strong>Resources:</strong> Add, edit, or remove team members</li>
          <li><strong>Forecasts:</strong> Manage weekly hour forecasts for each resource</li>
        </ul>
      </div>
    </div>
  );
}

export default Dashboard;

// Made with Bob
