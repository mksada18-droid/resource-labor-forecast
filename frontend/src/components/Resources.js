import React, { useState, useEffect } from 'react';
import { resourceAPI } from '../services/api';

function Resources() {
  const [resources, setResources] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [editingResource, setEditingResource] = useState(null);
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    department: '',
  });

  useEffect(() => {
    fetchResources();
  }, []);

  const fetchResources = async () => {
    try {
      setLoading(true);
      const response = await resourceAPI.getAll();
      setResources(response.data);
      setError(null);
    } catch (err) {
      setError('Failed to load resources. Please ensure the backend is running.');
      console.error('Fetch resources error:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editingResource) {
        await resourceAPI.update(editingResource.id, formData);
      } else {
        await resourceAPI.create(formData);
      }
      setShowModal(false);
      setEditingResource(null);
      setFormData({ name: '', email: '', department: '' });
      fetchResources();
    } catch (err) {
      alert('Failed to save resource: ' + (err.response?.data?.error || err.message));
    }
  };

  const handleEdit = (resource) => {
    setEditingResource(resource);
    setFormData({
      name: resource.name,
      email: resource.email,
      department: resource.department,
    });
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this resource? All associated forecasts will also be deleted.')) {
      try {
        await resourceAPI.delete(id);
        fetchResources();
      } catch (err) {
        alert('Failed to delete resource: ' + (err.response?.data?.error || err.message));
      }
    }
  };

  const handleAdd = () => {
    setEditingResource(null);
    setFormData({ name: '', email: '', department: '' });
    setShowModal(true);
  };

  const handleCancel = () => {
    setShowModal(false);
    setEditingResource(null);
    setFormData({ name: '', email: '', department: '' });
  };

  if (loading) {
    return <div className="loading">Loading resources</div>;
  }

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1.5rem' }}>
        <h2>Resources</h2>
        <button className="btn btn-primary" onClick={handleAdd}>
          + Add Resource
        </button>
      </div>

      {error && <div className="error">{error}</div>}

      <div className="card">
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Department</th>
                <th>Created</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {resources.length === 0 ? (
                <tr>
                  <td colSpan="5" style={{ textAlign: 'center', padding: '2rem' }}>
                    No resources found. Click "Add Resource" to create one.
                  </td>
                </tr>
              ) : (
                resources.map((resource) => (
                  <tr key={resource.id}>
                    <td>{resource.name}</td>
                    <td>{resource.email}</td>
                    <td>{resource.department}</td>
                    <td>{new Date(resource.createdAt).toLocaleDateString()}</td>
                    <td>
                      <button
                        className="btn btn-sm btn-secondary"
                        onClick={() => handleEdit(resource)}
                        style={{ marginRight: '0.5rem' }}
                      >
                        Edit
                      </button>
                      <button
                        className="btn btn-sm btn-danger"
                        onClick={() => handleDelete(resource.id)}
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
            <h2>{editingResource ? 'Edit Resource' : 'Add Resource'}</h2>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label>Name *</label>
                <input
                  type="text"
                  value={formData.name}
                  onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                  required
                />
              </div>

              <div className="form-group">
                <label>Email *</label>
                <input
                  type="email"
                  value={formData.email}
                  onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                  required
                />
              </div>

              <div className="form-group">
                <label>Department *</label>
                <input
                  type="text"
                  value={formData.department}
                  onChange={(e) => setFormData({ ...formData, department: e.target.value })}
                  required
                />
              </div>

              <div className="form-actions">
                <button type="submit" className="btn btn-primary">
                  {editingResource ? 'Update' : 'Create'}
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

export default Resources;

// Made with Bob
