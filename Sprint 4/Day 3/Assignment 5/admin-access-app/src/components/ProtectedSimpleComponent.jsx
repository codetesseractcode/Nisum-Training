import React from 'react';
import withAdminAccess from '../hoc/withAdminAccess';

// A simple component to demonstrate HOC usage
const SimpleAdminComponent = () => {
  return (
    <div style={{
      padding: '20px',
      margin: '20px auto',
      maxWidth: '600px',
      backgroundColor: '#e8f5e8',
      border: '2px solid #4caf50',
      borderRadius: '8px',
      textAlign: 'center'
    }}>
      <h3>🎉 Admin Component Loaded Successfully!</h3>
      <p>This component is only visible to admin users.</p>
      <p>You can perform admin-specific operations here.</p>
    </div>
  );
};

// Wrap the component with the HOC
const ProtectedSimpleComponent = withAdminAccess(SimpleAdminComponent);

export default ProtectedSimpleComponent;
