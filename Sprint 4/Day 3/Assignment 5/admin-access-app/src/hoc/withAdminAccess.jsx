import React from 'react';
import { useUser } from '../contexts/UserContext';

const withAdminAccess = (WrappedComponent) => {
  const AdminAccessComponent = React.forwardRef((props, ref) => {
    const { user } = useUser();
    
    if (!user || user.role !== 'admin') {
      return (
        <div style={{
          padding: '20px',
          border: '1px solid #ccc',
          borderRadius: '4px',
          backgroundColor: '#f9f9f9',
          textAlign: 'center',
          margin: '20px 0',
          color: '#333'
        }}>
          <h3>Access Denied</h3>
          <p>Admin access required</p>
          <p>Current role: <strong>{user?.role || 'Unknown'}</strong></p>
        </div>
      );
    }
    
    return <WrappedComponent ref={ref} {...props} />;
  });
  
  // Set display name for better debugging
  AdminAccessComponent.displayName = `withAdminAccess(${WrappedComponent.displayName || WrappedComponent.name || 'Component'})`;
  
  return AdminAccessComponent;
};

export default withAdminAccess;
