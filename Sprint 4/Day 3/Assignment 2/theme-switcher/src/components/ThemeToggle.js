import React from 'react';
import { useTheme } from '../contexts/ThemeContext';
import './ThemeToggle.css';

// Theme toggle button component - following Single Responsibility Principle
export function ThemeToggle() {
  const { toggleTheme, isDark, theme } = useTheme();

  return (
    <button
      onClick={toggleTheme}
      className="theme-toggle"
      style={{
        backgroundColor: theme.colors.surface,
        color: theme.colors.text,
        borderColor: theme.colors.border
      }}
      aria-label={`Switch to ${isDark ? 'light' : 'dark'} theme`}
    >
      <span className="theme-toggle-icon">
        {isDark ? '☀️' : '🌙'}
      </span>
      <span className="theme-toggle-text">
        {isDark ? 'Light' : 'Dark'}
      </span>
    </button>
  );
}
