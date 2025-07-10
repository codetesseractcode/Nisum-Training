import React from 'react';
import { UserProvider, useUser } from './contexts/UserContext';
import { Login } from './components/Login';
import { Header } from './components/Header';
import { Dashboard } from './components/Dashboard';
import './App.css';

function AppContent() {
  const { isLoggedIn } = useUser();

  console.log('App rendering, isLoggedIn:', isLoggedIn); // Debug log

  return (
    <div className="App">
      <Header />
      {!isLoggedIn ? <Login /> : <Dashboard />}
    </div>
  );
}

function App() {
  console.log('App component loaded'); // Debug log
  return (
    <UserProvider>
      <AppContent />
    </UserProvider>
  );
}

export default App;
