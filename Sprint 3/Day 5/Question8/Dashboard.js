import React from 'react';
import UserProfile from './UserProfile';
import Notifications from './Notifications';
import './Dashboard.css';

const Dashboard = ({ userProfile, notifications, isDarkTheme }) => {
  // Dashboard container styles
  const dashboardStyles = {
    backgroundColor: isDarkTheme ? '#1a1a1a' : '#f8f9fa',
    color: isDarkTheme ? '#ecf0f1' : '#2c3e50',
    transition: 'all 0.3s ease',
    minHeight: '100vh',
    padding: '20px 0'
  };

  return (
    <div className="dashboard-container" style={dashboardStyles}>
      <div className="dashboard-header">
        <h1 className="dashboard-title">📊 User Dashboard</h1>
        <p className="dashboard-subtitle">
          Welcome to your personal dashboard where you can view your profile and notifications
        </p>
      </div>

      <div className="dashboard-content">
        <div className="dashboard-section">
          <h2 className="section-title">👤 Profile Information</h2>
          <UserProfile 
            name={userProfile.name}
            email={userProfile.email}
            avatarUrl={userProfile.avatarUrl}
            bio={userProfile.bio}
            isDarkTheme={isDarkTheme}
          />
        </div>

        <div className="dashboard-section">
          <h2 className="section-title">🔔 Recent Activity</h2>
          <Notifications 
            notifications={notifications} 
            isDarkTheme={isDarkTheme}
          />
        </div>
      </div>

      <div className="dashboard-footer">
        <p className="footer-text">
          Dashboard last updated: {new Date().toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
          })}
        </p>
      </div>
    </div>
  );
};

// Default props for the Dashboard component
Dashboard.defaultProps = {
  userProfile: {
    name: 'Guest User',
    email: 'guest@example.com',
    avatarUrl: '',
    bio: 'Welcome to the dashboard!'
  },
  notifications: []
};

export default Dashboard;
