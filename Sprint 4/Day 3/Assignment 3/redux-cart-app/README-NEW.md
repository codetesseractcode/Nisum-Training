# Redux E-commerce Cart App

## Overview
This is a comprehensive e-commerce application built with React and Redux, demonstrating advanced state management for user authentication and cart functionality. The app follows SOLID principles and uses Redux Toolkit for efficient state management.

## 🎯 Assignment Requirements Met

### ✅ Core Features
- **User State Management**: Complete login/logout functionality with user details storage
- **Cart State Management**: Add/remove products with quantity management
- **Redux Implementation**: Centralized state management for users and cart
- **Cart Display**: Real-time cart component showing product count and total
- **Product Interactions**: Full e-commerce product browsing and purchasing flow

### 🏗️ SOLID Principles Applied

#### **Single Responsibility Principle (SRP)**
- **UserSlice**: Only handles user authentication and state
- **CartSlice**: Only manages cart items and calculations
- **ProductsSlice**: Only handles product data and filtering
- **Components**: Each component has a single, well-defined purpose

#### **Open/Closed Principle (OCP)**
- **Redux Slices**: Easy to extend with new actions without modifying existing code
- **Product System**: New product types can be added without changing core logic
- **Cart System**: New cart operations can be added without modifying existing reducers

#### **Liskov Substitution Principle (LSP)**
- **Redux Selectors**: All selectors follow consistent interfaces
- **Component Props**: All similar components accept the same prop structures

#### **Interface Segregation Principle (ISP)**
- **Redux Selectors**: Specific selectors for specific data needs
- **Component Props**: Components only receive props they actually use

#### **Dependency Inversion Principle (DIP)**
- **Components use Redux hooks**: Components depend on abstractions, not concrete implementations
- **Async Actions**: Business logic separated from UI components

## 🛒 Features

### User Management
- **Authentication**: Secure login/logout with mock user data
- **User Profiles**: Store and display user information
- **Role-based Access**: Different user roles (admin, customer)
- **Session Management**: Persistent user state

### Shopping Cart
- **Add to Cart**: Add products from product list
- **Remove from Cart**: Remove individual items
- **Quantity Management**: Increase/decrease item quantities
- **Cart Calculations**: Real-time totals and quantities
- **Cart Persistence**: Cart state maintained across sessions
- **Cart Sidebar**: Slide-out cart with full functionality

### Product Management
- **Product Catalog**: Browse available products
- **Search Functionality**: Search products by name
- **Category Filtering**: Filter by product categories
- **Sorting Options**: Sort by name, price, rating
- **Stock Management**: Track and display product availability
- **Product Details**: Rich product information display

### User Interface
- **Responsive Design**: Works on all screen sizes
- **Modern UI**: Clean, professional design
- **Interactive Elements**: Hover effects and animations
- **Loading States**: Visual feedback during operations
- **Error Handling**: User-friendly error messages

## 🗂️ Project Structure

```
src/
├── store/
│   ├── index.js                 # Redux store configuration
│   └── slices/
│       ├── userSlice.js         # User state management
│       ├── cartSlice.js         # Cart state management
│       └── productsSlice.js     # Products state management
├── components/
│   ├── Header.js & .css         # Header with cart indicator
│   ├── Login.js & .css          # User authentication
│   ├── ProductList.js & .css    # Product browsing
│   └── Cart.js & .css           # Shopping cart
├── App.js                       # Main application component
├── App.css                      # Global styles
└── index.js                     # Application entry point
```

## 🚀 How to Run

### Prerequisites
- Node.js (v14 or higher)
- npm or yarn

### Installation
```bash
# Navigate to the project directory
cd "Assignment 3/redux-cart-app"

# Install dependencies
npm install

# Start the development server
npm start
```

### For PowerShell Users (Windows)
```powershell
# If you encounter execution policy issues
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser

# Or use full paths
& "C:\Program Files\nodejs\npm.cmd" install
& "C:\Program Files\nodejs\npm.cmd" start
```

## 🎮 Usage

### User Authentication
1. **Login**: Use demo credentials provided on the login screen
2. **Demo Accounts**:
   - Admin: `admin@example.com` / `admin123`
   - Customer: `user@example.com` / `user123`
   - Customer: `jane@example.com` / `jane123`

### Shopping Experience
1. **Browse Products**: View the product catalog
2. **Search & Filter**: Use search and category filters
3. **Add to Cart**: Click "Add to Cart" on any product
4. **View Cart**: Click the cart button in the header
5. **Manage Cart**: Adjust quantities or remove items
6. **Logout**: Use the logout button in the header

## 🔧 Redux Implementation

### Store Structure
```javascript
{
  user: {
    currentUser: null,
    isLoggedIn: false,
    isLoading: false,
    error: null
  },
  cart: {
    items: [],
    totalQuantity: 0,
    totalAmount: 0,
    isOpen: false
  },
  products: {
    items: [...],
    filteredItems: [...],
    categories: [...],
    selectedCategory: 'All',
    searchTerm: '',
    sortBy: 'name'
  }
}
```

### Key Actions
- **User**: `loginUser`, `logout`, `clearError`
- **Cart**: `addToCart`, `removeFromCart`, `updateQuantity`, `clearCart`
- **Products**: `filterByCategory`, `searchProducts`, `sortProducts`

### Advanced Features
- **Async Actions**: Login with simulated API calls
- **Computed Values**: Automatic cart totals calculation
- **Selectors**: Optimized data retrieval
- **Middleware**: Redux Toolkit's built-in middleware

## 🧪 Testing the Application

### User Flow Testing
1. **Login Flow**: Test with different user accounts
2. **Cart Management**: Add, remove, and modify cart items
3. **Product Filtering**: Test search and category filters
4. **Responsive Design**: Test on different screen sizes

### Redux DevTools
- Install Redux DevTools browser extension
- Monitor state changes in real-time
- Debug actions and state updates

## 📱 Responsive Design
- **Mobile-first approach**: Optimized for mobile devices
- **Breakpoints**: 768px (tablet) and 480px (mobile)
- **Adaptive layouts**: Cart sidebar becomes full-screen on mobile
- **Touch-friendly**: Large buttons and touch targets

## 🔒 Security Features
- **Input validation**: Form validation and sanitization
- **Mock authentication**: Simulated secure login flow
- **Error handling**: Graceful error management
- **State protection**: Immutable state updates

## 🎨 Design System
- **Color palette**: Professional blue and gray tones
- **Typography**: Clean, readable fonts
- **Spacing**: Consistent padding and margins
- **Components**: Reusable UI elements

## 🚀 Performance Optimizations
- **Redux Toolkit**: Efficient state management
- **Memoized selectors**: Optimized data retrieval
- **Immutable updates**: Immer integration for performance
- **Component optimization**: Minimal re-renders

## 🔄 Future Enhancements
- **Payment integration**: Stripe or PayPal checkout
- **User profiles**: Extended user management
- **Order history**: Track previous purchases
- **Wishlist**: Save products for later
- **Product reviews**: User ratings and reviews
- **Inventory management**: Real-time stock updates

## 📊 Technology Stack
- **React 19.1.0**: Latest React features
- **Redux Toolkit**: Modern Redux development
- **React-Redux**: React bindings for Redux
- **CSS3**: Modern styling with flexbox/grid
- **JavaScript ES6+**: Modern JavaScript features

This implementation demonstrates enterprise-level Redux usage with clean architecture, following all SOLID principles and best practices for scalable React applications.
