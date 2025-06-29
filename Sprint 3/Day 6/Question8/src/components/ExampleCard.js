import React from 'react';
import { useTheme } from '../contexts/ThemeContext';
import './ExampleCard.css';

const ExampleCard = () => {
  const { theme, isDark } = useTheme();

  return (
    <div className={`example-card ${theme}`}>
      <h3>Example Component</h3>
      <p>
        This card demonstrates how any component can access the current theme using the useTheme hook.
      </p>
      <p>
        Current theme: <strong>{isDark ? 'Dark Mode' : 'Light Mode'}</strong>
      </p>
      <div className="theme-info">
        <span className="theme-indicator">
          {isDark ? '🌙' : '☀️'}
        </span>
        <span>Theme context is working!</span>
      </div>
    </div>
  );
};

export default ExampleCard;
