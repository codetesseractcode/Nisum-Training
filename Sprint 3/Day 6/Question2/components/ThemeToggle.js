import React from 'react';
import { useTheme } from '../contexts/ThemeContext';
import './ThemeToggle.css';

const ThemeToggle = () => {
  const { theme, toggleTheme, isDark } = useTheme();

  return (
    <div className="theme-toggle-container">
      <button 
        className={`theme-toggle ${theme}`}
        onClick={toggleTheme}
        aria-label={`Switch to ${isDark ? 'light' : 'dark'} mode`}
      >
        <div className="toggle-slider">
          <span className="toggle-icon">
            {isDark ? '🌙' : '☀️'}
          </span>
        </div>
        <span className="toggle-text">
          {isDark ? 'Dark Mode' : 'Light Mode'}
        </span>
      </button>
    </div>
  );
};

export default ThemeToggle;
