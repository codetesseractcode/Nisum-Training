import React, { createContext, useContext, useState, useEffect } from 'react';

// Theme constants - following YAGNI principle (only what we need)
export const THEMES = {
  LIGHT: 'light',
  DARK: 'dark'
};

// Theme configuration - following Open/Closed Principle
const themeConfig = {
  [THEMES.LIGHT]: {
    name: 'Light',
    colors: {
      background: '#ffffff',
      surface: '#f8f9fa',
      primary: '#007bff',
      secondary: '#6c757d',
      text: '#212529',
      textSecondary: '#6c757d',
      border: '#dee2e6',
      shadow: 'rgba(0, 0, 0, 0.1)'
    }
  },
  [THEMES.DARK]: {
    name: 'Dark',
    colors: {
      background: '#1a1a1a',
      surface: '#2d2d2d',
      primary: '#0d6efd',
      secondary: '#6c757d',
      text: '#ffffff',
      textSecondary: '#adb5bd',
      border: '#495057',
      shadow: 'rgba(0, 0, 0, 0.3)'
    }
  }
};

// Create Context
const ThemeContext = createContext();

// Custom hook for using theme context (Dependency Inversion Principle)
export function useTheme() {
  const context = useContext(ThemeContext);
  if (!context) {
    throw new Error('useTheme must be used within a ThemeProvider');
  }
  return context;
}

// Theme provider component (Single Responsibility Principle)
export function ThemeProvider({ children }) {
  // Initialize theme from localStorage or default to light
  const [currentTheme, setCurrentTheme] = useState(() => {
    const savedTheme = localStorage.getItem('theme');
    return savedTheme && Object.values(THEMES).includes(savedTheme) 
      ? savedTheme 
      : THEMES.LIGHT;
  });

  // Persist theme to localStorage whenever it changes
  useEffect(() => {
    localStorage.setItem('theme', currentTheme);
    // Apply theme to document root for global CSS variables
    document.documentElement.setAttribute('data-theme', currentTheme);
  }, [currentTheme]);

  // Theme toggle function
  const toggleTheme = () => {
    setCurrentTheme(prev => 
      prev === THEMES.LIGHT ? THEMES.DARK : THEMES.LIGHT
    );
  };

  // Get current theme configuration
  const theme = themeConfig[currentTheme];

  // Context value
  const value = {
    currentTheme,
    theme,
    toggleTheme,
    isLight: currentTheme === THEMES.LIGHT,
    isDark: currentTheme === THEMES.DARK
  };

  return (
    <ThemeContext.Provider value={value}>
      {children}
    </ThemeContext.Provider>
  );
}
