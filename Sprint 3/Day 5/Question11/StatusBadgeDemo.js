import React from 'react';
import StatusBadge from './StatusBadge';

const StatusBadgeDemo = ({ isDarkTheme }) => {
  const containerStyles = {
    backgroundColor: isDarkTheme ? '#34495e' : '#f8f9fa',
    padding: '30px',
    borderRadius: '12px',
    margin: '20px 0',
    border: isDarkTheme ? '2px solid #5d6d7e' : '2px solid #e9ecef',
    transition: 'all 0.3s ease'
  };

  const titleStyles = {
    color: isDarkTheme ? '#3498db' : '#2c3e50',
    textAlign: 'center',
    marginBottom: '25px',
    fontSize: '1.8rem',
    fontWeight: '700'
  };

  const sectionStyles = {
    backgroundColor: isDarkTheme ? '#2c3e50' : '#ffffff',
    padding: '20px',
    borderRadius: '8px',
    margin: '15px 0',
    border: isDarkTheme ? '1px solid #5d6d7e' : '1px solid #e9ecef'
  };

  const gridStyles = {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))',
    gap: '15px',
    alignItems: 'center'
  };

  return (
    <div style={containerStyles}>
      <h2 style={titleStyles}>🏷️ Status Badge Components</h2>
      
      {/* Basic Status Types */}
      <div style={sectionStyles}>
        <h3 style={{ color: isDarkTheme ? '#e74c3c' : '#3498db', marginBottom: '15px' }}>
          Basic Status Types
        </h3>
        <div style={gridStyles}>
          <StatusBadge status="success" isDarkTheme={isDarkTheme}>
            Operation Successful
          </StatusBadge>
          <StatusBadge status="error" isDarkTheme={isDarkTheme}>
            Error Occurred
          </StatusBadge>
          <StatusBadge status="warning" isDarkTheme={isDarkTheme}>
            Warning Message
          </StatusBadge>
          <StatusBadge status="info" isDarkTheme={isDarkTheme}>
            Information
          </StatusBadge>
          <StatusBadge status="pending" isDarkTheme={isDarkTheme}>
            Processing...
          </StatusBadge>
        </div>
      </div>

      {/* Different Sizes */}
      <div style={sectionStyles}>
        <h3 style={{ color: isDarkTheme ? '#e74c3c' : '#3498db', marginBottom: '15px' }}>
          Different Sizes
        </h3>
        <div style={{ ...gridStyles, gridTemplateColumns: 'repeat(auto-fit, minmax(150px, 1fr))' }}>
          <div>
            <p style={{ margin: '0 0 8px 0', fontSize: '0.9rem', opacity: 0.8 }}>Small:</p>
            <StatusBadge status="success" size="small" isDarkTheme={isDarkTheme}>
              Success
            </StatusBadge>
          </div>
          <div>
            <p style={{ margin: '0 0 8px 0', fontSize: '0.9rem', opacity: 0.8 }}>Medium:</p>
            <StatusBadge status="warning" size="medium" isDarkTheme={isDarkTheme}>
              Warning
            </StatusBadge>
          </div>
          <div>
            <p style={{ margin: '0 0 8px 0', fontSize: '0.9rem', opacity: 0.8 }}>Large:</p>
            <StatusBadge status="error" size="large" isDarkTheme={isDarkTheme}>
              Error
            </StatusBadge>
          </div>
        </div>
      </div>

      {/* Without Icons */}
      <div style={sectionStyles}>
        <h3 style={{ color: isDarkTheme ? '#e74c3c' : '#3498db', marginBottom: '15px' }}>
          Without Icons
        </h3>
        <div style={gridStyles}>
          <StatusBadge status="success" icon={false} isDarkTheme={isDarkTheme}>
            Text Only Success
          </StatusBadge>
          <StatusBadge status="error" icon={false} isDarkTheme={isDarkTheme}>
            Text Only Error
          </StatusBadge>
          <StatusBadge status="warning" icon={false} isDarkTheme={isDarkTheme}>
            Text Only Warning
          </StatusBadge>
        </div>
      </div>

      {/* Real-world Examples */}
      <div style={sectionStyles}>
        <h3 style={{ color: isDarkTheme ? '#e74c3c' : '#3498db', marginBottom: '15px' }}>
          Real-world Examples
        </h3>
        <div style={{ display: 'grid', gap: '12px' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '10px', flexWrap: 'wrap' }}>
            <span style={{ minWidth: '150px' }}>User Registration:</span>
            <StatusBadge status="success" isDarkTheme={isDarkTheme}>
              Account Created
            </StatusBadge>
          </div>
          
          <div style={{ display: 'flex', alignItems: 'center', gap: '10px', flexWrap: 'wrap' }}>
            <span style={{ minWidth: '150px' }}>Payment Status:</span>
            <StatusBadge status="pending" isDarkTheme={isDarkTheme}>
              Payment Processing
            </StatusBadge>
          </div>
          
          <div style={{ display: 'flex', alignItems: 'center', gap: '10px', flexWrap: 'wrap' }}>
            <span style={{ minWidth: '150px' }}>Server Status:</span>
            <StatusBadge status="error" isDarkTheme={isDarkTheme}>
              Server Down
            </StatusBadge>
          </div>
          
          <div style={{ display: 'flex', alignItems: 'center', gap: '10px', flexWrap: 'wrap' }}>
            <span style={{ minWidth: '150px' }}>Storage Warning:</span>
            <StatusBadge status="warning" isDarkTheme={isDarkTheme}>
              85% Full
            </StatusBadge>
          </div>
          
          <div style={{ display: 'flex', alignItems: 'center', gap: '10px', flexWrap: 'wrap' }}>
            <span style={{ minWidth: '150px' }}>System Info:</span>
            <StatusBadge status="info" isDarkTheme={isDarkTheme}>
              Version 2.1.0
            </StatusBadge>
          </div>
        </div>
      </div>

      {/* Code Example */}
      <div style={sectionStyles}>
        <h3 style={{ color: isDarkTheme ? '#e74c3c' : '#3498db', marginBottom: '15px' }}>
          Usage Examples
        </h3>
        <pre style={{
          backgroundColor: isDarkTheme ? '#1a1a1a' : '#f8f9fa',
          padding: '15px',
          borderRadius: '8px',
          overflow: 'auto',
          fontSize: '0.85rem',
          lineHeight: '1.4',
          color: isDarkTheme ? '#2ecc71' : '#27ae60',
          border: isDarkTheme ? '1px solid #34495e' : '1px solid #e9ecef'
        }}>
{`// Basic usage
<StatusBadge status="success">Success Message</StatusBadge>

// With custom size and no icon
<StatusBadge status="warning" size="large" icon={false}>
  Custom Warning
</StatusBadge>

// Dark theme support
<StatusBadge status="error" isDarkTheme={true}>
  Error in Dark Mode
</StatusBadge>`}
        </pre>
      </div>
    </div>
  );
};

export default StatusBadgeDemo;
