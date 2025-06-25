import React, { useState, useEffect } from 'react';
import './Counter.css';

const Counter = () => {
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

  return (
    <div className="counter-container">
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
