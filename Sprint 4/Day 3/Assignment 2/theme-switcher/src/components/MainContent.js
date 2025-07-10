import React from 'react';
import { useTheme } from '../contexts/ThemeContext';
import { Card } from './Card';
import { Button } from './Button';
import './MainContent.css';

// Main content component - following Single Responsibility Principle
export function MainContent() {
  const { theme, currentTheme, toggleTheme, isLight } = useTheme();

  return (
    <main 
      className="main-content"
      style={{
        backgroundColor: theme.colors.background,
        color: theme.colors.text
      }}
    >
      <div className="content-container">
        <div className="intro-section">
          <h2 style={{ color: theme.colors.text }}>
            Welcome to the Theme Switcher Demo
          </h2>
          <p style={{ color: theme.colors.textSecondary }}>
            This application demonstrates React Context usage for theme management. 
            Click the theme toggle in the header to switch between light and dark modes.
          </p>
        </div>

        <div className="cards-grid">
          <Card title="Current Theme">
            <p style={{ color: theme.colors.textSecondary }}>
              Active theme: <strong style={{ color: theme.colors.text }}>
                {theme.name}
              </strong>
            </p>
            <p style={{ color: theme.colors.textSecondary }}>
              Theme mode: <strong style={{ color: theme.colors.text }}>
                {currentTheme}
              </strong>
            </p>
            <p style={{ color: theme.colors.textSecondary }}>
              This card automatically updates its styling based on the current theme 
              using React Context.
            </p>
          </Card>

          <Card title="Theme Features">
            <p style={{ color: theme.colors.textSecondary }}>
              ✅ Persistent theme selection (localStorage)
            </p>
            <p style={{ color: theme.colors.textSecondary }}>
              ✅ Smooth transitions between themes
            </p>
            <p style={{ color: theme.colors.textSecondary }}>
              ✅ System-wide theme application
            </p>
            <p style={{ color: theme.colors.textSecondary }}>
              ✅ Responsive design for all screen sizes
            </p>
          </Card>

          <Card title="Interactive Elements">
            <p style={{ color: theme.colors.textSecondary }}>
              Test different button variants with the current theme:
            </p>
            <div className="button-showcase">
              <Button variant="primary" onClick={toggleTheme}>
                Toggle Theme
              </Button>
              <Button variant="secondary">
                Secondary Button
              </Button>
              <Button variant="outline">
                Outline Button
              </Button>
            </div>
          </Card>

          <Card title="Theme Colors">
            <div className="color-palette">
              <div 
                className="color-item"
                style={{ backgroundColor: theme.colors.primary }}
              >
                <span className="color-label">Primary</span>
              </div>
              <div 
                className="color-item"
                style={{ backgroundColor: theme.colors.surface }}
              >
                <span 
                  className="color-label"
                  style={{ color: theme.colors.text }}
                >
                  Surface
                </span>
              </div>
              <div 
                className="color-item"
                style={{ backgroundColor: theme.colors.background }}
              >
                <span 
                  className="color-label"
                  style={{ color: theme.colors.text }}
                >
                  Background
                </span>
              </div>
            </div>
          </Card>
        </div>

        <div className="status-section">
          <div 
            className="status-indicator"
            style={{
              backgroundColor: isLight ? '#ffeaa7' : '#6c5ce7',
              color: isLight ? '#2d3436' : '#ffffff'
            }}
          >
            <span className="status-emoji">
              {isLight ? '☀️' : '🌙'}
            </span>
            <span className="status-text">
              {isLight ? 'Light Mode Active' : 'Dark Mode Active'}
            </span>
          </div>
        </div>
      </div>
    </main>
  );
}
