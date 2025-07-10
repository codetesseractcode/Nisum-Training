import React from 'react';
import { Provider } from 'react-redux';
import { useSelector } from 'react-redux';
import { store } from './store';
import { selectIsLoggedIn } from './store/slices/userSlice';
import { Header } from './components/Header';
import { Login } from './components/Login';
import { ProductList } from './components/ProductList';
import { Cart } from './components/Cart';
import './App.css';

// App Content Component - following Dependency Inversion Principle
function AppContent() {
  const isLoggedIn = useSelector(selectIsLoggedIn);

  return (
    <div className="App">
      <Header />
      {isLoggedIn ? (
        <>
          <ProductList />
          <Cart />
        </>
      ) : (
        <Login />
      )}
    </div>
  );
}

// Main App Component - following Single Responsibility Principle
function App() {
  return (
    <Provider store={store}>
      <AppContent />
    </Provider>
  );
}

export default App;
