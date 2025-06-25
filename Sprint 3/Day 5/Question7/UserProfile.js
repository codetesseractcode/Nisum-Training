import React from 'react';
import './UserCard.css';

const UserProfile = ({ name, email, avatarUrl, bio, isDarkTheme }) => {
  // Default placeholder image URL
  const defaultAvatar = "https://via.placeholder.com/150x150/3498db/ffffff?text=User";

  // Container styles for theme integration
  const containerStyles = {
    backgroundColor: isDarkTheme ? '#2c3e50' : '#ffffff',
    color: isDarkTheme ? '#ecf0f1' : '#2c3e50',
    border: isDarkTheme ? '2px solid #5d6d7e' : '2px solid #e9ecef',
    transition: 'all 0.3s ease'
  };

  return (
    <div className="user-profile-card" style={containerStyles}>
      <div className="user-avatar-section">
        <img 
          src={avatarUrl || defaultAvatar} 
          alt={`${name}'s avatar`}
          className="user-avatar"
          onError={(e) => {
            e.target.src = defaultAvatar;
          }}
        />
      </div>
      
      <div className="user-info-section">
        <h2 className="user-name">{name}</h2>
        <p className="user-email">{email}</p>
        <div className="user-bio">
          <h4>About</h4>
          <p>{bio}</p>
        </div>
      </div>
      
      <div className="user-profile-footer">
        <span className="profile-badge">👤 User Profile</span>
      </div>
    </div>
  );
};

// Default props
UserProfile.defaultProps = {
  name: 'Anonymous User',
  email: 'user@example.com',
  avatarUrl: '',
  bio: 'No bio available. This user hasn\'t shared any information about themselves yet.'
};

export default UserProfile;
