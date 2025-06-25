import React from 'react';

function Greeting({ name, isDarkTheme }) {
  const greetingStyles = {
    color: isDarkTheme ? '#3498db' : '#2c3e50',
    textShadow: isDarkTheme ? '0 0 10px rgba(52, 152, 219, 0.5)' : 'none',
    transition: 'all 0.3s ease'
  };

  return <h2 style={greetingStyles}>Hello, {name}!</h2>;
}

export default Greeting;
