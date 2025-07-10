import React from 'react';
import { useTheme } from '../contexts/ThemeContext';
import './Card.css';

// Reusable Card component - following Open/Closed Principle
export function Card({ title, children, className = '', ...props }) {
  const { theme } = useTheme();

  return (
    <div
      className={`card ${className}`}
      style={{
        backgroundColor: theme.colors.surface,
        borderColor: theme.colors.border,
        boxShadow: `0 4px 15px ${theme.colors.shadow}`,
        color: theme.colors.text
      }}
      {...props}
    >
      {title && (
        <div className="card-header">
          <h3 
            className="card-title"
            style={{ color: theme.colors.text }}
          >
            {title}
          </h3>
        </div>
      )}
      <div className="card-content">
        {children}
      </div>
    </div>
  );
}
