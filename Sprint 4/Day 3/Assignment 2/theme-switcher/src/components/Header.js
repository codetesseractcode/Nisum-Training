import React from 'react';
import { useTheme } from '../contexts/ThemeContext';
import { ThemeToggle } from './ThemeToggle';
import './Header.css';

// Header component - following Single Responsibility Principle
export function Header() {
  const { theme } = useTheme();

  return (
    <header 
      className="header"
      style={{
        backgroundColor: theme.colors.surface,
        borderBottomColor: theme.colors.border,
        boxShadow: `0 2px 10px ${theme.colors.shadow}`
      }}
    >
      <div className="header-content">
        <div className="header-left">
          <h1 
            className="header-title"
            style={{ color: theme.colors.text }}
          >
            Theme Switcher App
          </h1>
          <p 
            className="header-subtitle"
            style={{ color: theme.colors.textSecondary }}
          >
            React Context Demo
          </p>
        </div>
        <div className="header-right">
          <ThemeToggle />
        </div>
      </div>
    </header>
  );
}
