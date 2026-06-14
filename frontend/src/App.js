import React, { useState } from 'react';
import Dashboard from './components/Dashboard';
import Resources from './components/Resources';
import Forecasts from './components/Forecasts';
import './styles/App.css';

function App() {
  const [currentView, setCurrentView] = useState('dashboard');

  const renderView = () => {
    switch (currentView) {
      case 'dashboard':
        return <Dashboard />;
      case 'resources':
        return <Resources />;
      case 'forecasts':
        return <Forecasts />;
      default:
        return <Dashboard />;
    }
  };

  return (
    <div className="app">
      <header className="header">
        <h1>Resource Labor Forecast</h1>
        <p>Manage resources and forecast weekly labor hours</p>
      </header>

      <nav className="nav">
        <button
          className={currentView === 'dashboard' ? 'active' : ''}
          onClick={() => setCurrentView('dashboard')}
        >
          Dashboard
        </button>
        <button
          className={currentView === 'resources' ? 'active' : ''}
          onClick={() => setCurrentView('resources')}
        >
          Resources
        </button>
        <button
          className={currentView === 'forecasts' ? 'active' : ''}
          onClick={() => setCurrentView('forecasts')}
        >
          Forecasts
        </button>
      </nav>

      <main className="main-content">
        {renderView()}
      </main>

      <footer style={{ 
        textAlign: 'center', 
        padding: '1.5rem', 
        color: '#666',
        borderTop: '1px solid #e1e8ed'
      }}>
        <p>Resource Labor Forecast System © 2026</p>
      </footer>
    </div>
  );
}

export default App;

// Made with Bob
