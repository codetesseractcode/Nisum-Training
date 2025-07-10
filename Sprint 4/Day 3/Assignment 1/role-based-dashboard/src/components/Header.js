import React from 'react';
import { useUser } from '../contexts/UserContext';
import './Header.css';

export function Header() {
  const { user, logout, isLoggedIn } = useUser();

  if (!isLoggedIn) {
    return null;
  }

  return (
    <header className="dashboard-header">
      <div className="header-content">
        <div className="header-left">
          <h1>Dashboard</h1>
        </div>
        <div className="header-right">
          <div className="user-info">
            <span className="user-name">Welcome, {user.name}</span>
            <span className={`user-role role-${user.role}`}>
              {user.role.toUpperCase()}
            </span>
          </div>
          <button onClick={logout} className="logout-button">
            Logout
          </button>
        </div>
      </div>
    </header>
  );
}
