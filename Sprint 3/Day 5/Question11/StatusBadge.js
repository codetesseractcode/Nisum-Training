import React from 'react';
import classNames from 'classnames';
import './StatusBadge.css';

const StatusBadge = ({ status, children, size = 'medium', isDarkTheme = false, icon = true }) => {
  // Define status icons
  const statusIcons = {
    success: '✅',
    error: '❌',
    warning: '⚠️',
    info: 'ℹ️',
    pending: '⏳'
  };

  // Use classnames to conditionally combine CSS classes
  const badgeClasses = classNames(
    'status-badge',
    `status-badge--${status}`,
    `status-badge--${size}`,
    {
      'status-badge--dark': isDarkTheme,
      'status-badge--light': !isDarkTheme,
      'status-badge--with-icon': icon
    }
  );

  return (
    <span className={badgeClasses}>
      {icon && statusIcons[status] && (
        <span className="status-badge__icon">
          {statusIcons[status]}
        </span>
      )}
      <span className="status-badge__text">
        {children || status.charAt(0).toUpperCase() + status.slice(1)}
      </span>
    </span>
  );
};

// Default props
StatusBadge.defaultProps = {
  status: 'info',
  size: 'medium',
  isDarkTheme: false,
  icon: true
};

export default StatusBadge;
