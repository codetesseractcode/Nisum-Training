import React from 'react';

const AboutPage = ({ isDarkTheme }) => {
  const sectionStyles = {
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
    marginBottom: '20px',
    fontSize: '2.2rem',
    fontWeight: '700'
  };

  const profileCardStyles = {
    backgroundColor: isDarkTheme ? '#2c3e50' : '#ffffff',
    padding: '30px',
    borderRadius: '15px',
    textAlign: 'center',
    boxShadow: '0 6px 20px rgba(0, 0, 0, 0.1)',
    border: isDarkTheme ? '2px solid #5d6d7e' : '2px solid #e9ecef',
    margin: '20px 0'
  };

  const timelineStyles = {
    backgroundColor: isDarkTheme ? '#2c3e50' : '#ffffff',
    padding: '25px',
    borderRadius: '10px',
    margin: '15px 0',
    borderLeft: `4px solid ${isDarkTheme ? '#e74c3c' : '#3498db'}`,
    boxShadow: '0 2px 8px rgba(0, 0, 0, 0.1)'
  };

  return (
    <div className="about-page">
      <h1 style={titleStyles}>ℹ️ About This Project</h1>
      
      <div style={sectionStyles}>
        <h2 style={{ color: isDarkTheme ? '#e74c3c' : '#3498db', textAlign: 'center' }}>
          👨‍💻 Developer Profile
        </h2>
        
        <div style={profileCardStyles}>
          <div style={{ 
            width: '120px', 
            height: '120px', 
            borderRadius: '50%', 
            backgroundColor: isDarkTheme ? '#3498db' : '#e74c3c',
            margin: '0 auto 20px',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            fontSize: '3rem'
          }}>
            👨‍💻
          </div>
          <h3 style={{ margin: '0 0 10px 0', fontSize: '1.8rem' }}>Ayusman Pradhan</h3>
          <p style={{ 
            color: isDarkTheme ? '#bdc3c7' : '#7f8c8d', 
            margin: '0 0 20px 0',
            fontSize: '1.1rem'
          }}>
            Full-Stack Developer
          </p>
          <p style={{ lineHeight: '1.6', fontSize: '1rem' }}>
            Passionate about creating amazing user experiences with React, Node.js, 
            and modern web technologies. This project showcases various React concepts 
            including component architecture, state management, and responsive design.
          </p>
        </div>
      </div>

      <div style={sectionStyles}>
        <h2 style={{ color: isDarkTheme ? '#e74c3c' : '#3498db', textAlign: 'center' }}>
          🎯 Project Overview
        </h2>
        <p style={{ fontSize: '1.1rem', lineHeight: '1.6', textAlign: 'center' }}>
          This React application demonstrates a comprehensive component library 
          with modern web development practices, responsive design, and clean architecture.
        </p>
        
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))', gap: '20px', marginTop: '20px' }}>
          <div style={timelineStyles}>
            <h4 style={{ color: isDarkTheme ? '#f39c12' : '#e67e22', margin: '0 0 10px 0' }}>
              📋 Planning Phase
            </h4>
            <p style={{ margin: 0, lineHeight: '1.5' }}>
              Designed component architecture and planned the layout structure 
              using CSS Grid and Flexbox for optimal responsiveness.
            </p>
          </div>
          
          <div style={timelineStyles}>
            <h4 style={{ color: isDarkTheme ? '#2ecc71' : '#27ae60', margin: '0 0 10px 0' }}>
              🔧 Development Phase
            </h4>
            <p style={{ margin: 0, lineHeight: '1.5' }}>
              Built reusable components with React Hooks, implemented state management, 
              and created a flexible theme system.
            </p>
          </div>
          
          <div style={timelineStyles}>
            <h4 style={{ color: isDarkTheme ? '#9b59b6' : '#8e44ad', margin: '0 0 10px 0' }}>
              🎨 Styling Phase
            </h4>
            <p style={{ margin: 0, lineHeight: '1.5' }}>
              Applied responsive design principles, created dark/light themes, 
              and optimized for all device sizes.
            </p>
          </div>
        </div>
      </div>

      <div style={sectionStyles}>
        <h2 style={{ color: isDarkTheme ? '#e74c3c' : '#3498db', textAlign: 'center' }}>
          🏗️ Layout Architecture
        </h2>
        <div style={{ 
          backgroundColor: isDarkTheme ? '#2c3e50' : '#ffffff',
          padding: '25px',
          borderRadius: '10px',
          border: isDarkTheme ? '1px solid #5d6d7e' : '1px solid #e9ecef'
        }}>
          <h3 style={{ color: isDarkTheme ? '#f39c12' : '#e67e22' }}>CSS Grid Structure:</h3>
          <pre style={{ 
            backgroundColor: isDarkTheme ? '#1a1a1a' : '#f8f9fa',
            padding: '15px',
            borderRadius: '8px',
            overflow: 'auto',
            fontSize: '0.9rem',
            lineHeight: '1.4',
            color: isDarkTheme ? '#2ecc71' : '#27ae60'
          }}>
{`grid-template-areas: 
  "header"
  "main"  
  "footer";
  
grid-template-rows: auto 1fr auto;`}
          </pre>
          <p style={{ marginTop: '15px', lineHeight: '1.6' }}>
            This layout ensures the header and footer take only the space they need, 
            while the main content area expands to fill the remaining space, 
            creating a perfect full-height layout.
          </p>
        </div>
      </div>

      <div style={sectionStyles}>
        <h2 style={{ color: isDarkTheme ? '#e74c3c' : '#3498db', textAlign: 'center' }}>
          📚 Technologies Used
        </h2>
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '15px' }}>
          {[
            { name: 'React.js', icon: '⚛️', desc: 'Component-based UI library' },
            { name: 'CSS Grid', icon: '🔲', desc: 'Modern layout system' },
            { name: 'Flexbox', icon: '📐', desc: 'Flexible box layout' },
            { name: 'JavaScript ES6+', icon: '🟨', desc: 'Modern JavaScript features' },
            { name: 'Responsive Design', icon: '📱', desc: 'Mobile-first approach' },
            { name: 'Component Architecture', icon: '🧩', desc: 'Reusable components' }
          ].map((tech, index) => (
            <div key={index} style={{
              backgroundColor: isDarkTheme ? '#2c3e50' : '#ffffff',
              padding: '20px',
              borderRadius: '8px',
              textAlign: 'center',
              border: isDarkTheme ? '1px solid #5d6d7e' : '1px solid #e9ecef',
              transition: 'transform 0.3s ease'
            }}>
              <div style={{ fontSize: '2rem', marginBottom: '10px' }}>{tech.icon}</div>
              <h4 style={{ margin: '0 0 8px 0', color: isDarkTheme ? '#3498db' : '#2c3e50' }}>
                {tech.name}
              </h4>
              <p style={{ margin: 0, fontSize: '0.9rem', opacity: 0.8 }}>
                {tech.desc}
              </p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default AboutPage;
