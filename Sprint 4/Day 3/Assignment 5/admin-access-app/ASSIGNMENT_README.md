# Assignment 5: Admin Access Control with HOC

## 🎯 Objective
Implement a Higher-Order Component (HOC) pattern to control component rendering based on user roles, specifically creating an admin access control system.

## 📋 Requirements Implemented

### ✅ 1. Create withAdminAccess HOC
- **Location**: `src/hoc/withAdminAccess.jsx`
- **Functionality**: Checks if user role is 'admin'
- **Features**:
  - Wraps any component with admin access control
  - Includes proper display name for debugging
  - Uses React.forwardRef for ref forwarding
  - Error handling for missing user context

### ✅ 2. Access Denied Message
- Shows clear "Access Denied" message for non-admin users
- Displays current user role
- Provides helpful hint to use role switcher
- Professional styling with visual indicators

### ✅ 3. ProductEdit Component
- **Location**: `src/components/ProductEdit.jsx`
- **Features**:
  - Full product editing functionality (name, price, description, category, stock)
  - Edit/Save/Cancel workflow
  - Form validation
  - Professional UI with responsive design

### ✅ 4. Wrapped with HOC
- **Location**: `src/components/ProtectedProductEdit.jsx`
- ProductEdit component is wrapped with withAdminAccess HOC
- Clean separation of concerns

### ✅ 5. Role Simulation
- **Location**: `src/contexts/UserContext.jsx`
- React Context for user state management
- Role switching functionality
- Helper methods (isAdmin, isUser)
- **Location**: `src/components/RoleSwitcher.jsx`
- Interactive role switcher component
- Visual feedback for current role

## 🏗️ Project Structure

```
src/
├── components/
│   ├── ProductEdit.jsx          # Main product editing component
│   ├── ProtectedProductEdit.jsx # HOC-wrapped product editor
│   └── RoleSwitcher.jsx         # Role switching interface
├── contexts/
│   └── UserContext.jsx          # User state management
├── hoc/
│   └── withAdminAccess.jsx      # Higher-Order Component
├── App.jsx                      # Main application
└── main.jsx                     # Entry point
```

## 🚀 How to Run

### Prerequisites
- Node.js installed
- npm or yarn package manager

### Steps
1. **Install Dependencies**:
   ```bash
   npm install
   ```

2. **Start Development Server**:
   ```bash
   npm run dev
   ```

3. **Open Browser**:
   - Navigate to `http://localhost:5173`

### Alternative: Use Test Scripts
- **Windows PowerShell**: Run `.\test-assignment.ps1`
- **Bash**: Run `./test-assignment.sh`

## 🧪 Testing the Implementation

### Test Scenarios

1. **User Role (Default)**:
   - Access denied message appears
   - Cannot access ProductEdit component
   - Clear indication of current role

2. **Admin Role**:
   - Full access to ProductEdit component
   - Can modify all product fields
   - Save/Cancel functionality works

3. **Role Switching**:
   - Toggle between User and Admin roles
   - Immediate UI updates
   - Persistent state during session

### Expected Behavior

| Role  | ProductEdit Access | Message Displayed |
|-------|-------------------|-------------------|
| User  | ❌ Denied         | "Access Denied"   |
| Admin | ✅ Granted        | Component loads   |

## 🎨 Features Demonstrated

### HOC Pattern Benefits
- **Reusability**: Can wrap any component with admin access
- **Separation of Concerns**: Access logic separate from UI logic
- **Composability**: Easy to combine with other HOCs
- **Debugging**: Proper display names for React DevTools

### React Best Practices
- Context API for global state
- Functional components with hooks
- Proper prop forwarding
- Error boundaries consideration
- Clean component structure

### UI/UX Features
- Professional styling
- Visual feedback for user actions
- Responsive design
- Accessibility considerations
- Clear error messages

## 🔧 Technical Implementation Details

### withAdminAccess HOC
```jsx
const withAdminAccess = (WrappedComponent) => {
  const AdminAccessComponent = React.forwardRef((props, ref) => {
    const { user } = useUser();
    
    if (!user || user.role !== 'admin') {
      return <AccessDeniedMessage />;
    }
    
    return <WrappedComponent ref={ref} {...props} />;
  });
  
  AdminAccessComponent.displayName = `withAdminAccess(${WrappedComponent.name})`;
  return AdminAccessComponent;
};
```

### Usage Pattern
```jsx
// Wrap any component
const ProtectedComponent = withAdminAccess(MyComponent);

// Use in JSX
<ProtectedComponent {...props} />
```

## 🏆 Assignment Success Criteria

- ✅ HOC created and functional
- ✅ Access control working correctly
- ✅ Product editing capabilities implemented
- ✅ Role simulation working
- ✅ Professional UI/UX
- ✅ Proper React patterns followed
- ✅ Error handling implemented
- ✅ Documentation provided

## 📚 Learning Outcomes

1. **HOC Pattern**: Understanding higher-order components
2. **Access Control**: Implementing role-based permissions
3. **React Context**: Global state management
4. **Component Composition**: Building reusable components
5. **Error Handling**: Graceful failure scenarios
6. **UI/UX Design**: Professional interface development

---

**Assignment completed successfully! 🎉**
