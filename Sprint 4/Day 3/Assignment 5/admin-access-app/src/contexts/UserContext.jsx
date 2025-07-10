import React, { createContext, useContext, useState } from 'react';

const UserContext = createContext();

export const useUser = () => {
  const context = useContext(UserContext);
  if (!context) {
    throw new Error('useUser must be used within a UserProvider');
  }
  return context;
};

export const UserProvider = ({ children }) => {
  const [user, setUser] = useState({
    id: 1,
    name: 'John Doe',
    role: 'user' // Default role, can be 'admin' or 'user'
  });

  const switchRole = (newRole) => {
    if (newRole === 'admin' || newRole === 'user') {
      setUser(prev => ({ ...prev, role: newRole }));
    } else {
      console.warn('Invalid role. Only "admin" and "user" are supported.');
    }
  };

  const value = {
    user,
    setUser,
    switchRole,
    isAdmin: user.role === 'admin',
    isUser: user.role === 'user'
  };

  return (
    <UserContext.Provider value={value}>
      {children}
    </UserContext.Provider>
  );
};
