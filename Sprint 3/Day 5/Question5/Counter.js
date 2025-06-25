import React, { useState, useEffect } from 'react';
import './Counter.css';

const Counter = ({ isDarkTheme }) => {
  const [count, setCount] = useState(0);

  // Arrow function event handlers
  const handleIncrement = () => {
    setCount(count + 1);
  };

  const handleDecrement = () => {
    setCount(count - 1);
  };

  const handleReset = () => {
    setCount(0);
  };
  // useEffect to log count changes
  useEffect(() => {
    console.log(`Count changed to: ${count}`);
  }, [count]);

  // Dynamic styles based on theme
  const containerStyles = {
    backgroundColor: isDarkTheme ? '#34495e' : '#f5f5f5',
    color: isDarkTheme ? '#ecf0f1' : '#333',
    border: isDarkTheme ? '2px solid #5d6d7e' : '2px solid #ddd',
    transition: 'all 0.3s ease'
  };

  return (
    <div className="counter-container" style={containerStyles}>
      <h1>Count: {count}</h1>
      <div className="button-group">
        <button className="increment-btn" onClick={handleIncrement}>
          Increment
        </button>
        <button 
          className="decrement-btn" 
          onClick={handleDecrement}
          disabled={count === 0}
        >
          Decrement
        </button>
        <button className="reset-btn" onClick={handleReset}>
          Reset
        </button>
      </div>
    </div>
  );
};

export default Counter;
