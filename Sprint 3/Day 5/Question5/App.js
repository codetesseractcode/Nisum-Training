import React, { useState } from 'react';
import logo from './logo.svg';
import './App.css';
import './GlobalTheme.css';
import Greeting from './Greeting';
import Counter from './Counter';
import ProductCard from './ProductCard';
import ThemeToggle from './ThemeToggle';

function App() {
  const userName = "Ayusman Pradhan"; // Dynamic value that can be changed
  const [isDarkTheme, setIsDarkTheme] = useState(false);

  const toggleTheme = () => {
    setIsDarkTheme(!isDarkTheme);
  };

  // Global theme styles
  const appStyles = {
    backgroundColor: isDarkTheme ? '#1a1a1a' : '#ffffff',
    color: isDarkTheme ? '#ffffff' : '#000000',
    minHeight: '100vh',
    transition: 'all 0.3s ease'
  };

  const headerStyles = {
    backgroundColor: isDarkTheme ? '#2c3e50' : '#282c34',
    color: isDarkTheme ? '#ecf0f1' : '#ffffff',
    transition: 'all 0.3s ease'
  };

  return (
    <div className="App" style={appStyles}>
      <header className="App-header" style={headerStyles}>
        <img src={logo} className="App-logo" alt="logo" />
        <Greeting name={userName} isDarkTheme={isDarkTheme} />
        <ThemeToggle isDarkTheme={isDarkTheme} toggleTheme={toggleTheme} />
        <Counter isDarkTheme={isDarkTheme} />
        <ProductCard 
          title="iPhone 15"
          price={1099}
          description="Latest model with improved battery life."
          isDarkTheme={isDarkTheme}
        />
        <ProductCard isDarkTheme={isDarkTheme} />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
          style={{ color: isDarkTheme ? '#3498db' : '#61dafb' }}
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
