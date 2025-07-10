# Role-Based Access Control Dashboard

## Overview
This is a React application demonstrating Role-Based Access Control (RBAC) using React Context API. The application follows SOLID principles and implements a clean, maintainable architecture.

## Features

### 🔐 Authentication & Authorization
- User authentication with mock data
- Role-based permissions (Admin, User, Guest)
- Context-based state management
- Secure component rendering based on user roles

### 👥 User Roles & Permissions

#### Admin
- Full access to all features
- Can add products
- Can view analytics
- Can manage users
- Can edit profile
- Can view products

#### User
- Can view products
- Can edit profile
- Limited dashboard access

#### Guest
- Can only view products
- Read-only access

### 🎨 User Interface
- Modern, responsive design
- Clean component architecture
- Permission-based UI rendering
- Beautiful gradients and animations

## Architecture & SOLID Principles

### Single Responsibility Principle (SRP)
- **UserContext**: Only handles user state and authentication
- **PermissionGuard**: Only handles permission-based rendering
- **Dashboard components**: Each component has a single, well-defined purpose

### Open/Closed Principle (OCP)
- **PermissionGuard**: Extensible for new permission types without modification
- **Role system**: Easy to add new roles without changing existing code

### Liskov Substitution Principle (LSP)
- **Components**: All dashboard components can be used interchangeably
- **Guards**: AuthGuard and PermissionGuard follow the same interface

### Interface Segregation Principle (ISP)
- **Context hooks**: Specific hooks for specific functionality
- **Permission checks**: Granular permission checking methods

### Dependency Inversion Principle (DIP)
- **Components depend on abstractions**: Components use context hooks, not direct context
- **Mock data**: Easily replaceable with real API calls

## Project Structure

```
src/
├── contexts/
│   └── UserContext.js          # User state management
├── components/
│   ├── guards/
│   │   └── PermissionGuard.js  # Permission-based rendering
│   ├── Dashboard.js            # Main dashboard component
│   ├── Dashboard.css           # Dashboard styles
│   ├── Header.js               # Navigation header
│   ├── Header.css              # Header styles
│   ├── Login.js                # Authentication component
│   └── Login.css               # Login styles
├── App.js                      # Main application component
├── App.css                     # Global styles
└── index.js                    # Application entry point
```

## How to Run

### Prerequisites
- Node.js (v14 or higher)
- npm or yarn

### Installation & Setup

1. **Navigate to the project directory:**
   ```bash
   cd "Assignment 1/role-based-dashboard"
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Start the development server:**
   ```bash
   npm start
   ```

4. **Open your browser:**
   The application will automatically open at `http://localhost:3000`

### PowerShell Users (Windows)
If you encounter execution policy issues, try:
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

Or run npm using the full path:
```powershell
& "C:\Program Files\nodejs\npm.cmd" install
& "C:\Program Files\nodejs\npm.cmd" start
```

## Usage

### Login Process
1. When you first open the application, you'll see a login screen
2. Select one of the three mock users:
   - **John Admin** (admin) - Full access
   - **Jane User** (user) - Limited access
   - **Guest User** (guest) - View-only access
3. Click "Login" to authenticate

### Dashboard Features
- **Header**: Shows user info and logout functionality
- **Welcome Section**: Displays current user and role
- **Feature Cards**: Role-based access to different features
- **Permissions Info**: Shows what the current user can access

### Testing Different Roles
1. Login as **Admin** to see all features
2. Logout and login as **User** to see limited access
3. Logout and login as **Guest** to see read-only access

## Code Quality Features

### Context Management
- Centralized user state
- Immutable state updates with useReducer
- Clean separation of concerns

### Permission System
- Granular permission checking
- Easy to extend and maintain
- Performance optimized

### Component Architecture
- Reusable components
- Props-based configuration
- Clear component boundaries

### Styling
- Modern CSS with flexbox/grid
- Responsive design
- Consistent design system

## Future Enhancements
- Real API integration
- Persistent authentication
- More granular permissions
- User management interface
- Analytics dashboard
- Product management features

## Technical Stack
- **React 19.1.0**: Latest React features
- **React Context API**: State management
- **CSS3**: Modern styling with gradients and animations
- **Modern JavaScript**: ES6+ features

This implementation demonstrates best practices for role-based access control in React applications while maintaining clean, maintainable, and scalable code architecture.

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
