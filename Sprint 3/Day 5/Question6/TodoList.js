import React from 'react';
import './TodoList.css';

const TodoList = ({ todos, isDarkTheme }) => {
  // Styles for the todo list container
  const containerStyles = {
    backgroundColor: isDarkTheme ? '#34495e' : '#f8f9fa',
    color: isDarkTheme ? '#ecf0f1' : '#2c3e50',
    padding: '20px',
    borderRadius: '10px',
    margin: '20px 0',
    border: isDarkTheme ? '2px solid #5d6d7e' : '2px solid #dee2e6',
    transition: 'all 0.3s ease',
    maxWidth: '500px'
  };

  // Styles for the list
  const listStyles = {
    listStyle: 'none',
    padding: 0,
    margin: '15px 0 0 0'
  };

  // Styles for individual todo items
  const todoItemStyles = {
    backgroundColor: isDarkTheme ? '#2c3e50' : '#ffffff',
    color: isDarkTheme ? '#bdc3c7' : '#495057',
    padding: '12px 16px',
    margin: '8px 0',
    borderRadius: '6px',
    border: isDarkTheme ? '1px solid #5d6d7e' : '1px solid #e9ecef',
    transition: 'all 0.3s ease',
    cursor: 'default'
  };

  const headerStyles = {
    color: isDarkTheme ? '#3498db' : '#2c3e50',
    textAlign: 'center',
    marginBottom: '10px',
    fontSize: '1.5rem'
  };

  return (
    <div style={containerStyles} className="todo-list-container">
      <h3 style={headerStyles}>📝 Todo List</h3>
      
      {todos && todos.length > 0 ? (
        <ul style={listStyles}>
          {todos.map((todo, index) => (
            <li 
              key={index} 
              style={todoItemStyles}
              className="todo-item"
            >
              {todo}
            </li>
          ))}
        </ul>
      ) : (
        <p style={{ textAlign: 'center', fontStyle: 'italic', opacity: 0.7 }}>
          No todos available
        </p>
      )}
      
      <div style={{ 
        marginTop: '15px', 
        fontSize: '14px', 
        opacity: 0.8, 
        textAlign: 'center' 
      }}>
        {todos && todos.length > 0 ? `${todos.length} todo${todos.length > 1 ? 's' : ''}` : 'Empty list'}
      </div>
    </div>
  );
};

export default TodoList;
