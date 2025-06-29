import React from 'react';
import './App.css';
import { ThemeProvider } from './contexts/ThemeContext';
import ThemeToggle from './components/ThemeToggle';
import ProductList from './components/ProductList';

function App() {
  return (
    <ThemeProvider>
      <div className="App">
        <ThemeToggle />
        <ProductList />
      </div>
    </ThemeProvider>
  );
}

export default App;
