import React, { useState } from 'react';
import { useUser, USER_ROLES } from '../contexts/UserContext';
import './Login.css';

// Mock user data (following YAGNI principle - keeping it simple)
const MOCK_USERS = [
  {
    id: 1,
    name: 'John Admin',
    email: 'admin@example.com',
    role: USER_ROLES.ADMIN
  },
  {
    id: 2,
    name: 'Jane User',
    email: 'user@example.com',
    role: USER_ROLES.USER
  },
  {
    id: 3,
    name: 'Guest User',
    email: 'guest@example.com',
    role: USER_ROLES.GUEST
  }
];

export function Login() {
  const { login, isLoggedIn } = useUser();
  const [selectedUser, setSelectedUser] = useState('');

  const handleLogin = () => {
    const user = MOCK_USERS.find(u => u.id === parseInt(selectedUser));
    if (user) {
      login(user);
    }
  };

  if (isLoggedIn) {
    return null; // Don't show login if already logged in
  }

  return (
    <div className="login-container">
      <div className="login-card">
        <h2>Login to Dashboard</h2>
        <div className="login-form">
          <label htmlFor="user-select">Select User:</label>
          <select 
            id="user-select"
            value={selectedUser} 
            onChange={(e) => setSelectedUser(e.target.value)}
            className="user-select"
          >
            <option value="">Choose a user...</option>
            {MOCK_USERS.map(user => (
              <option key={user.id} value={user.id}>
                {user.name} ({user.role})
              </option>
            ))}
          </select>
          <button 
            onClick={handleLogin} 
            disabled={!selectedUser}
            className="login-button"
          >
            Login
          </button>
        </div>
      </div>
    </div>
  );
}
