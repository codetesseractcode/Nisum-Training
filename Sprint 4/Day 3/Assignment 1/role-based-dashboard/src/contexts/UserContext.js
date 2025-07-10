import React, { createContext, useContext, useReducer } from 'react';

// Define user roles as constants (following YAGNI - You Aren't Gonna Need It)
export const USER_ROLES = {
  ADMIN: 'admin',
  USER: 'user',
  GUEST: 'guest'
};

// Initial state
const initialState = {
  user: null,
  isLoggedIn: false,
  permissions: []
};

// Action types
const ActionTypes = {
  LOGIN: 'LOGIN',
  LOGOUT: 'LOGOUT'
};

// Reducer function following Single Responsibility Principle
function userReducer(state, action) {
  switch (action.type) {
    case ActionTypes.LOGIN:
      return {
        ...state,
        user: action.payload.user,
        isLoggedIn: true,
        permissions: getPermissionsByRole(action.payload.user.role)
      };
    case ActionTypes.LOGOUT:
      return {
        ...state,
        user: null,
        isLoggedIn: false,
        permissions: []
      };
    default:
      return state;
  }
}

// Helper function to get permissions based on role (following Single Responsibility)
function getPermissionsByRole(role) {
  const permissionMap = {
    [USER_ROLES.ADMIN]: ['add_product', 'edit_product', 'delete_product', 'view_analytics', 'manage_users'],
    [USER_ROLES.USER]: ['view_products', 'edit_profile'],
    [USER_ROLES.GUEST]: ['view_products']
  };
  
  return permissionMap[role] || [];
}

// Create Context
const UserContext = createContext();

// Custom hook for using the context (following Dependency Inversion Principle)
export function useUser() {
  const context = useContext(UserContext);
  if (!context) {
    throw new Error('useUser must be used within a UserProvider');
  }
  return context;
}

// Provider component (following Single Responsibility)
export function UserProvider({ children }) {
  const [state, dispatch] = useReducer(userReducer, initialState);

  // Action creators
  const login = (userData) => {
    dispatch({
      type: ActionTypes.LOGIN,
      payload: { user: userData }
    });
  };

  const logout = () => {
    dispatch({
      type: ActionTypes.LOGOUT
    });
  };

  // Check if user has specific permission
  const hasPermission = (permission) => {
    return state.permissions.includes(permission);
  };

  // Check if user has specific role
  const hasRole = (role) => {
    return state.user?.role === role;
  };

  const value = {
    ...state,
    login,
    logout,
    hasPermission,
    hasRole
  };

  return (
    <UserContext.Provider value={value}>
      {children}
    </UserContext.Provider>
  );
}
