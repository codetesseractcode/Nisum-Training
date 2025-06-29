import React from 'react';
import logo from './logo.svg';
import './App.css';
import { ThemeProvider } from './contexts/ThemeContext';
import ThemeToggle from './components/ThemeToggle';
import ExampleCard from './components/ExampleCard';

function App() {
  return (
    <ThemeProvider>
      <div className="App">
        <ThemeToggle />
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1>Theme Toggle Demo</h1>
          <p>
            This React app demonstrates a theme toggle using Context API.
          </p>
          <p>
            Click the toggle button in the top-right corner to switch between light and dark themes.
          </p>
          <a
            className="App-link"
            href="https://reactjs.org"
            target="_blank"
            rel="noopener noreferrer"
          >
            Learn React
          </a>
        </header>
        <ExampleCard />
      </div>
    </ThemeProvider>
  );
}

export default App;
