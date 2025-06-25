import React from 'react';
import './Notifications.css';

const Notifications = ({ notifications, isDarkTheme }) => {
  // Container styles for theme integration
  const containerStyles = {
    backgroundColor: isDarkTheme ? '#2c3e50' : '#ffffff',
    color: isDarkTheme ? '#ecf0f1' : '#2c3e50',
    border: isDarkTheme ? '2px solid #5d6d7e' : '2px solid #e9ecef',
    transition: 'all 0.3s ease'
  };

  // Format date and time
  const formatDateTime = (dateString) => {
    const date = new Date(dateString);
    const formattedDate = date.toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric'
    });
    const formattedTime = date.toLocaleTimeString('en-US', {
      hour: '2-digit',
      minute: '2-digit',
      hour12: true
    });
    return { date: formattedDate, time: formattedTime };
  };

  return (
    <div className="notifications-container" style={containerStyles}>
      <div className="notifications-header">
        <h3>🔔 Notifications</h3>
        <span className="notification-count">
          {notifications?.length || 0} notifications
        </span>
      </div>

      <div className="notifications-list">
        {notifications && notifications.length > 0 ? (
          notifications.map((notification, index) => {
            const { date, time } = formatDateTime(notification.timestamp);
            
            return (
              <div 
                key={index} 
                className={`notification-item ${notification.isRead ? 'read' : 'unread'}`}
              >
                <div className="notification-content">
                  <div className="notification-header-item">
                    <h4 className="notification-title">{notification.title}</h4>
                    <span className={`notification-status ${notification.isRead ? 'read' : 'unread'}`}>
                      {notification.isRead ? '✓ Read' : '● Unread'}
                    </span>
                  </div>
                  
                  <p className="notification-message">{notification.message}</p>
                  
                  <div className="notification-meta">
                    <span className="notification-date">📅 {date}</span>
                    <span className="notification-time">🕐 {time}</span>
                  </div>
                </div>
                
                {!notification.isRead && <div className="unread-indicator"></div>}
              </div>
            );
          })
        ) : (
          <div className="no-notifications">
            <p>📭 No notifications available</p>
          </div>
        )}
      </div>

      <div className="notifications-footer">
        <span className="notifications-summary">
          {notifications?.filter(n => !n.isRead).length || 0} unread • {notifications?.filter(n => n.isRead).length || 0} read
        </span>
      </div>
    </div>
  );
};

// Default props
Notifications.defaultProps = {
  notifications: []
};

export default Notifications;
