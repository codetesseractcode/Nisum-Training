# Theme Switcher - React Context Demo

## Overview
This is a React application demonstrating theme management using React Context API. The app features a light/dark theme switcher with persistent storage and smooth transitions, following SOLID and YAGNI principles.

## 🎯 Assignment Requirements Met

### ✅ Core Features
- **React Context Implementation**: ThemeContext manages theme state across the app
- **Light/Dark Theme Switcher**: Toggle between light and dark modes
- **Multiple Components**: Header, MainContent, Card, Button, and ThemeToggle all consume theme context
- **Header Toggle Button**: Easy theme switching with visual feedback
- **Persistent Storage**: Theme preference saved in localStorage

### 🏗️ SOLID Principles Applied

#### **Single Responsibility Principle (SRP)**
- **ThemeContext**: Only handles theme state and logic
- **ThemeToggle**: Only handles theme switching UI
- **Header**: Only manages header layout and navigation
- **MainContent**: Only manages main content display
- **Card**: Only provides reusable card layout
- **Button**: Only provides reusable button functionality

#### **Open/Closed Principle (OCP)**
- **Theme Configuration**: Easy to extend with new themes without modifying existing code
- **Button Component**: Support for new variants without changing core logic
- **Card Component**: Extensible for new card types

#### **Liskov Substitution Principle (LSP)**
- **Button Variants**: All button types can be used interchangeably
- **Theme Objects**: All theme configurations follow the same interface

#### **Interface Segregation Principle (ISP)**
- **useTheme Hook**: Provides only necessary theme-related methods
- **Button Props**: Separated concerns for different button functionalities

#### **Dependency Inversion Principle (DIP)**
- **Components depend on abstractions**: All components use useTheme hook, not direct context
- **Theme configuration**: Easily replaceable theme definitions

### 🧩 YAGNI Principle Applied
- **Minimal theme options**: Only light/dark (no unnecessary complexity)
- **Essential components**: Only what's needed for demonstration
- **Simple state management**: No over-engineered state solutions

## 🎨 Features

### Theme Management
- **Light & Dark Modes**: Complete visual transformation
- **Persistent Storage**: Remembers user preference
- **Smooth Transitions**: Animated theme changes
- **CSS Variables**: Efficient theme application

### Interactive Components
- **Theme Toggle Button**: Animated toggle with icons
- **Responsive Cards**: Auto-adapting to theme changes
- **Interactive Buttons**: Multiple variants with theme support
- **Color Palette Display**: Live theme color preview

### User Experience
- **Smooth Animations**: All transitions are fluid
- **Accessibility**: Focus states and proper contrast
- **Responsive Design**: Works on all screen sizes
- **Visual Feedback**: Clear indication of active theme

## 🏃‍♂️ How to Run

### Prerequisites
- Node.js (v14 or higher)
- npm or yarn

### Installation
```bash
# Navigate to the project directory
cd "Assignment 2/theme-switcher"

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

### Theme Switching
1. **Click the theme toggle** in the header (🌙/☀️ button)
2. **Watch the smooth transition** between light and dark modes
3. **Refresh the page** - your theme preference is saved!

### Testing Context Integration
1. **Observe all components** changing simultaneously
2. **Check the color palette** updates in real-time
3. **Notice the status indicator** reflects current theme
4. **Try different button variants** with both themes

## 📁 Project Structure

```
src/
├── contexts/
│   └── ThemeContext.js          # Theme context and provider
├── components/
│   ├── Header.js & .css         # Header with theme toggle
│   ├── MainContent.js & .css    # Main content area
│   ├── Card.js & .css           # Reusable card component
│   ├── Button.js & .css         # Reusable button component
│   └── ThemeToggle.js & .css    # Theme toggle button
├── App.js                       # Main application component
├── App.css                      # Global styles and CSS variables
└── index.js                     # Application entry point
```

## 🎯 Key Components

### ThemeContext
- Manages theme state with useReducer pattern
- Provides theme configuration and toggle function
- Handles localStorage persistence
- Applies CSS variables to document root

### ThemeToggle
- Animated toggle button with icons
- Visual feedback for current theme
- Accessible button with proper ARIA labels

### Header
- Responsive header layout
- Theme-aware styling
- Integrated theme toggle

### MainContent
- Demonstrates various theme-consuming components
- Interactive examples and live color palette
- Status indicator for current theme

### Card & Button
- Reusable components with theme support
- Multiple variants and sizes
- Consistent styling across themes

## 🌟 Technical Highlights

### Context Implementation
- Clean separation of concerns
- Efficient re-rendering with selective updates
- Type-safe theme configuration

### Styling Architecture
- CSS Variables for efficient theme switching
- Smooth transitions between themes
- Consistent design system

### Performance Optimizations
- Minimal re-renders with proper context structure
- Efficient CSS variable updates
- Lazy loading of theme configurations

## 🚀 Future Enhancements
- System theme detection (prefers-color-scheme)
- More theme options (blue, green, etc.)
- Theme scheduling (auto-switch based on time)
- Custom theme builder
- Theme import/export functionality

## 📱 Browser Support
- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## 📊 Bundle Size
- Minimal overhead with React Context
- Efficient CSS-in-JS alternative
- Small bundle size with tree-shaking

This implementation demonstrates professional-grade theme management using React Context while maintaining clean, maintainable code that follows industry best practices.

### Code Splitting

This section has moved here: [https://facebook.github.io/create-react-app/docs/code-splitting](https://facebook.github.io/create-react-app/docs/code-splitting)

### Analyzing the Bundle Size

This section has moved here: [https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size](https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size)

### Making a Progressive Web App

This section has moved here: [https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app](https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app)

### Advanced Configuration

This section has moved here: [https://facebook.github.io/create-react-app/docs/advanced-configuration](https://facebook.github.io/create-react-app/docs/advanced-configuration)

### Deployment

This section has moved here: [https://facebook.github.io/create-react-app/docs/deployment](https://facebook.github.io/create-react-app/docs/deployment)

### `npm run build` fails to minify

This section has moved here: [https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify](https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify)
