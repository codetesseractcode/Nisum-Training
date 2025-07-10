import { createSlice } from '@reduxjs/toolkit';

// Mock user data - following YAGNI principle
const mockUsers = [
  {
    id: 1,
    email: 'admin@example.com',
    password: 'admin123',
    name: 'Admin User',
    role: 'admin',
    avatar: '👨‍💼'
  },
  {
    id: 2,
    email: 'user@example.com',
    password: 'user123',
    name: 'John Doe',
    role: 'customer',
    avatar: '👤'
  },
  {
    id: 3,
    email: 'jane@example.com',
    password: 'jane123',
    name: 'Jane Smith',
    role: 'customer',
    avatar: '👩'
  }
];

// Initial state
const initialState = {
  currentUser: null,
  isLoggedIn: false,
  isLoading: false,
  error: null
};

// User slice - following Single Responsibility Principle
const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    // Login start
    loginStart: (state) => {
      state.isLoading = true;
      state.error = null;
    },
    
    // Login success
    loginSuccess: (state, action) => {
      state.isLoading = false;
      state.currentUser = action.payload;
      state.isLoggedIn = true;
      state.error = null;
    },
    
    // Login failure
    loginFailure: (state, action) => {
      state.isLoading = false;
      state.error = action.payload;
      state.isLoggedIn = false;
    },
    
    // Logout
    logout: (state) => {
      state.currentUser = null;
      state.isLoggedIn = false;
      state.error = null;
    },
    
    // Clear error
    clearError: (state) => {
      state.error = null;
    }
  }
});

// Action creators
export const { 
  loginStart, 
  loginSuccess, 
  loginFailure, 
  logout, 
  clearError 
} = userSlice.actions;

// Async thunk for login - following Dependency Inversion Principle
export const loginUser = (credentials) => async (dispatch) => {
  dispatch(loginStart());
  
  try {
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 1000));
    
    const user = mockUsers.find(
      u => u.email === credentials.email && u.password === credentials.password
    );
    
    if (user) {
      // Remove password from user object
      const { password, ...userWithoutPassword } = user;
      dispatch(loginSuccess(userWithoutPassword));
    } else {
      dispatch(loginFailure('Invalid email or password'));
    }
  } catch (error) {
    dispatch(loginFailure('Login failed. Please try again.'));
  }
};

// Selectors - following Interface Segregation Principle
export const selectCurrentUser = (state) => state.user.currentUser;
export const selectIsLoggedIn = (state) => state.user.isLoggedIn;
export const selectIsLoading = (state) => state.user.isLoading;
export const selectError = (state) => state.user.error;

export default userSlice.reducer;
