import React from 'react';
import { useTheme } from '../contexts/ThemeContext';
import './Button.css';

// Reusable Button component - following Interface Segregation Principle
export function Button({ 
  variant = 'primary', 
  size = 'medium', 
  children, 
  className = '', 
  ...props 
}) {
  const { theme } = useTheme();

  const getButtonStyles = () => {
    const baseStyles = {
      borderColor: theme.colors.border,
      transition: 'all 0.3s ease'
    };

    switch (variant) {
      case 'primary':
        return {
          ...baseStyles,
          backgroundColor: theme.colors.primary,
          color: '#ffffff'
        };
      case 'secondary':
        return {
          ...baseStyles,
          backgroundColor: theme.colors.surface,
          color: theme.colors.text,
          border: `2px solid ${theme.colors.border}`
        };
      case 'outline':
        return {
          ...baseStyles,
          backgroundColor: 'transparent',
          color: theme.colors.primary,
          border: `2px solid ${theme.colors.primary}`
        };
      default:
        return baseStyles;
    }
  };

  return (
    <button
      className={`button button-${variant} button-${size} ${className}`}
      style={getButtonStyles()}
      {...props}
    >
      {children}
    </button>
  );
}
