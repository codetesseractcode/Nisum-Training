import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import { ThemeProvider } from './contexts/ThemeContext';
import { WishlistProvider } from './contexts/WishlistContext';
import ThemeToggle from './components/ThemeToggle';
import Navigation from './components/Navigation';
import ProductList from './components/ProductList';
import ProductDetail from './components/ProductDetail';
import WishlistPage from './components/WishlistPage';

function App() {
  return (
    <ThemeProvider>
      <WishlistProvider>
        <Router>
          <div className="App">
            <ThemeToggle />
            <Navigation />
            <Routes>
              <Route path="/" element={<ProductList />} />
              <Route path="/products/:id" element={<ProductDetail />} />
              <Route path="/wishlist" element={<WishlistPage />} />
            </Routes>
          </div>
        </Router>
      </WishlistProvider>
    </ThemeProvider>
  );
}

export default App;
