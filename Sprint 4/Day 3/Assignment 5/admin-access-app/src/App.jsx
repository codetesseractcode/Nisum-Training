import React from 'react';
import { UserProvider } from './contexts/UserContext';
import RoleSwitcher from './components/RoleSwitcher';
import ProtectedProductEdit from './components/ProtectedProductEdit';
import './App.css'

// Demo component to test HOC functionality
import withAdminAccess from './hoc/withAdminAccess';

const SimpleTestComponent = () => (
  <div style={{
    padding: '15px',
    margin: '20px auto',
    maxWidth: '500px',
    backgroundColor: '#fff',
    border: '1px solid #ddd',
    borderRadius: '4px',
    textAlign: 'center',
    color: '#333'
  }}>
    <h4>Admin Test Component</h4>
    <p>This component is also protected by the HOC</p>
  </div>
);

const ProtectedSimpleComponent = withAdminAccess(SimpleTestComponent);

function App() {
  const appStyle = {
    minHeight: '100vh',
    backgroundColor: '#f8f9fa',
    padding: '20px',
    fontFamily: 'Arial, sans-serif',
    color: '#333'
  };

  const headerStyle = {
    textAlign: 'center',
    marginBottom: '30px',
    color: '#333'
  };

  const containerStyle = {
    maxWidth: '600px',
    margin: '0 auto'
  };

  return (
    <UserProvider>
      <div style={appStyle}>
        <div style={containerStyle}>
          <header style={headerStyle}>
            <h1>Admin Access Control</h1>
            <p>HOC Implementation Demo</p>
          </header>
          
          <RoleSwitcher />
          <ProtectedProductEdit />
          <ProtectedSimpleComponent />
        </div>
      </div>
    </UserProvider>
  );
}

export default App;
