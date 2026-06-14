import React, { useState, useEffect } from 'react';
import { forecastAPI, resourceAPI } from '../services/api';

function Forecasts() {
  const [forecasts, setForecasts] = useState([]);
  const [resources, setResources] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [editingForecast, setEditingForecast] = useState(null);
  const [formData, setFormData] = useState({
    resourceId: '',
    weekEndingDate: '',
    forecastedHours: '',
    notes: '',
  });

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      setLoading(true);
      const [forecastsRes, resourcesRes] = await Promise.all([
        forecastAPI.getAll(),
        resourceAPI.getAll(),
      ]);
      setForecasts(forecastsRes.data);
      setResources(resourcesRes.data);
      setError(null);
    } catch (err) {
      setError('Failed to load data. Please ensure the backend is running.');
      console.error('Fetch data error:', err);
    } finally {
      setLoading(false);
    }
  };

  const getResourceName = (resourceId) => {
    const resource = resources.find((r) => r.id === resourceId);
    return resource ? resource.name : 'Unknown';
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const data = {
        ...formData,
        resourceId: parseInt(formData.resourceId),
        forecastedHours: parseFloat(formData.forecastedHours),
      };

      if (editingForecast) {
        await forecastAPI.update(editingForecast.id, data);
      } else {
        await forecastAPI.create(data);
      }
      setShowModal(false);
      setEditingForecast(null);
      setFormData({ resourceId: '', weekEndingDate: '', forecastedHours: '', notes: '' });
      fetchData();
    } catch (err) {
      alert('Failed to save forecast: ' + (err.response?.data?.error || err.message));
    }
  };

  const handleEdit = (forecast) => {
    setEditingForecast(forecast);
    setFormData({
      resourceId: forecast.resourceId.toString(),
      weekEndingDate: forecast.weekEndingDate,
      forecastedHours: forecast.forecastedHours.toString(),
      notes: forecast.notes || '',
    });
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this forecast?')) {
      try {
        await forecastAPI.delete(id);
        fetchData();
      } catch (err) {
        alert('Failed to delete forecast: ' + (err.response?.data?.error || err.message));
      }
    }
  };

  const handleAdd = () => {
    setEditingForecast(null);
    // Set default week ending date to next Saturday
    const today = new Date();
    const nextSaturday = new Date(today);
    nextSaturday.setDate(today.getDate() + ((6 - today.getDay() + 7) % 7 || 7));
    const dateStr = nextSaturday.toISOString().split('T')[0];
    
    setFormData({ 
      resourceId: resources.length > 0 ? resources[0].id.toString() : '',
      weekEndingDate: dateStr,
      forecastedHours: '40',
      notes: '' 
    });
    setShowModal(true);
  };

  const handleCancel = () => {
    setShowModal(false);
    setEditingForecast(null);
    setFormData({ resourceId: '', weekEndingDate: '', forecastedHours: '', notes: '' });
  };

  if (loading) {
    return <div className="loading">Loading forecasts</div>;
  }

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1.5rem' }}>
        <h2>Forecasts</h2>
        <button 
          className="btn btn-primary" 
          onClick={handleAdd}
          disabled={resources.length === 0}
        >
          + Add Forecast
        </button>
      </div>

      {error && <div className="error">{error}</div>}

      {resources.length === 0 && (
        <div className="error">
          No resources available. Please add resources first before creating forecasts.
        </div>
      )}

      <div className="card">
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Resource</th>
                <th>Week Ending</th>
                <th>Hours</th>
                <th>Notes</th>
                <th>Created</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {forecasts.length === 0 ? (
                <tr>
                  <td colSpan="6" style={{ textAlign: 'center', padding: '2rem' }}>
                    No forecasts found. Click "Add Forecast" to create one.
                  </td>
                </tr>
              ) : (
                forecasts.map((forecast) => (
                  <tr key={forecast.id}>
                    <td>{getResourceName(forecast.resourceId)}</td>
                    <td>{new Date(forecast.weekEndingDate).toLocaleDateString()}</td>
                    <td>{forecast.forecastedHours} hrs</td>
                    <td>{forecast.notes || '-'}</td>
                    <td>{new Date(forecast.createdAt).toLocaleDateString()}</td>
                    <td>
                      <button
                        className="btn btn-sm btn-secondary"
                        onClick={() => handleEdit(forecast)}
                        style={{ marginRight: '0.5rem' }}
                      >
                        Edit
                      </button>
                      <button
                        className="btn btn-sm btn-danger"
                        onClick={() => handleDelete(forecast.id)}
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>

      {showModal && (
        <div className="modal-overlay" onClick={handleCancel}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <h2>{editingForecast ? 'Edit Forecast' : 'Add Forecast'}</h2>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label>Resource *</label>
                <select
                  value={formData.resourceId}
                  onChange={(e) => setFormData({ ...formData, resourceId: e.target.value })}
                  required
                >
                  <option value="">Select a resource</option>
                  {resources.map((resource) => (
                    <option key={resource.id} value={resource.id}>
                      {resource.name} - {resource.department}
                    </option>
                  ))}
                </select>
              </div>

              <div className="form-group">
                <label>Week Ending Date *</label>
                <input
                  type="date"
                  value={formData.weekEndingDate}
                  onChange={(e) => setFormData({ ...formData, weekEndingDate: e.target.value })}
                  required
                />
              </div>

              <div className="form-group">
                <label>Forecasted Hours * (0-168)</label>
                <input
                  type="number"
                  step="0.01"
                  min="0"
                  max="168"
                  value={formData.forecastedHours}
                  onChange={(e) => setFormData({ ...formData, forecastedHours: e.target.value })}
                  required
                />
              </div>

              <div className="form-group">
                <label>Notes</label>
                <textarea
                  value={formData.notes}
                  onChange={(e) => setFormData({ ...formData, notes: e.target.value })}
                  placeholder="Optional notes about this forecast..."
                />
              </div>

              <div className="form-actions">
                <button type="submit" className="btn btn-primary">
                  {editingForecast ? 'Update' : 'Create'}
                </button>
                <button type="button" className="btn btn-secondary" onClick={handleCancel}>
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

export default Forecasts;

// Made with Bob
