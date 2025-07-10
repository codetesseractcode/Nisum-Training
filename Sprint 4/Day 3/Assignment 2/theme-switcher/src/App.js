import React from 'react';
import { ThemeProvider } from './contexts/ThemeContext';
import { Header } from './components/Header';
import { MainContent } from './components/MainContent';
import './App.css';

// Main App component - following Dependency Inversion Principle
function App() {
  return (
    <ThemeProvider>
      <div className="App">
        <Header />
        <MainContent />
      </div>
    </ThemeProvider>
  );
}

export default App;
