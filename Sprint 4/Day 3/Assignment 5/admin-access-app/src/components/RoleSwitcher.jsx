import React from 'react';
import { useUser } from '../contexts/UserContext';

const RoleSwitcher = () => {
  const { user, switchRole, isAdmin, isUser } = useUser();

  const switcherStyle = {
    padding: '15px',
    margin: '20px auto',
    maxWidth: '400px',
    backgroundColor: '#fff',
    border: '1px solid #ddd',
    borderRadius: '4px',
    textAlign: 'center',
    color: '#333'
  };

  const buttonStyle = {
    padding: '8px 16px',
    margin: '0 5px',
    border: '1px solid #ddd',
    borderRadius: '4px',
    cursor: 'pointer',
    backgroundColor: '#fff',
    color: '#333'
  };

  const activeButtonStyle = {
    ...buttonStyle,
    backgroundColor: '#000',
    color: 'white',
    border: '1px solid #000'
  };

  return (
    <div style={switcherStyle}>
      <p>Current User: {user.name} ({user.role})</p>
      <div>
        <button
          style={user.role === 'user' ? activeButtonStyle : buttonStyle}
          onClick={() => switchRole('user')}
        >
          User
        </button>
        <button
          style={user.role === 'admin' ? activeButtonStyle : buttonStyle}
          onClick={() => switchRole('admin')}
        >
          Admin
        </button>
      </div>
    </div>
  );
};

export default RoleSwitcher;
