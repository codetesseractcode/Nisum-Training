import React, { useState } from 'react';
import logo from './logo.svg';
import MyPicture from './My_picture.jpg';
import './App.css';
import './GlobalTheme.css';
import Greeting from './Greeting';
import Counter from './Counter';
import ProductCard from './ProductCard';
import ThemeToggle from './ThemeToggle';
import TodoList from './TodoList';
import UserProfile from './UserProfile';
import Notifications from './Notifications';

function App() {
  const userName = "Ayusman Pradhan"; // Dynamic value that can be changed
  const [isDarkTheme, setIsDarkTheme] = useState(false);

  // Sample todos data
  const sampleTodos = [
    "Learn React Components",
    "Build a Todo List",
    "Implement Theme Toggle",
    "Practice useState Hook",
    "Create Product Cards",
    "Master CSS Styling"
  ];

  const emptyTodos = []; // Empty array to demonstrate empty state

  // Sample user data
  const sampleUser = {
    name: "Ayusman Pradhan",
    email: "pradhanayusman22k@gmail.com",
    avatarUrl: MyPicture,
    bio: "Full-stack developer passionate about React, Node.js, and creating amazing user experiences. Always learning and building cool projects!"
  };

  // Sample notifications data
  const sampleNotifications = [
    {
      title: "Welcome to React Components!",
      message: "You've successfully created your first set of React components. Keep exploring!",
      timestamp: "2025-06-16T09:30:00Z",
      isRead: false
    },
    {
      title: "Theme Toggle Activated",
      message: "You can now switch between light and dark themes across the entire application.",
      timestamp: "2025-06-16T08:15:00Z",
      isRead: false
    },
    {
      title: "Counter Component Updated",
      message: "Your counter component is working perfectly with useState hooks.",
      timestamp: "2025-06-15T16:45:00Z",
      isRead: true
    },
    {
      title: "Product Cards Created",
      message: "Successfully implemented product cards with props and default values.",
      timestamp: "2025-06-15T14:20:00Z",
      isRead: true
    },
    {
      title: "Todo List Implemented",
      message: "Your todo list component is now rendering items dynamically from props.",
      timestamp: "2025-06-15T12:10:00Z",
      isRead: true
    }
  ];

  const toggleTheme = () => {
    setIsDarkTheme(!isDarkTheme);
  };

  // Global theme styles
  const appStyles = {
    backgroundColor: isDarkTheme ? '#1a1a1a' : '#ffffff',
    color: isDarkTheme ? '#ffffff' : '#000000',
    minHeight: '100vh',
    transition: 'all 0.3s ease'
  };

  const headerStyles = {
    backgroundColor: isDarkTheme ? '#2c3e50' : '#282c34',
    color: isDarkTheme ? '#ecf0f1' : '#ffffff',
    transition: 'all 0.3s ease'
  };

  return (
    <div className="App" style={appStyles}>
      <header className="App-header" style={headerStyles}>
        <img src={logo} className="App-logo" alt="logo" />
        <Greeting name={userName} isDarkTheme={isDarkTheme} />
        <ThemeToggle isDarkTheme={isDarkTheme} toggleTheme={toggleTheme} />
        <TodoList todos={sampleTodos} isDarkTheme={isDarkTheme} />
        <TodoList todos={emptyTodos} isDarkTheme={isDarkTheme} />
        <Counter isDarkTheme={isDarkTheme} />
        <ProductCard 
          title="iPhone 15"
          price={1099}
          description="Latest model with improved battery life."
          isDarkTheme={isDarkTheme}
        />
        <ProductCard isDarkTheme={isDarkTheme} />
        <UserProfile 
          name={sampleUser.name}
          email={sampleUser.email}
          avatarUrl={sampleUser.avatarUrl}
          bio={sampleUser.bio}
          isDarkTheme={isDarkTheme}
        />
        <UserProfile isDarkTheme={isDarkTheme} />
        <Notifications notifications={sampleNotifications} isDarkTheme={isDarkTheme} />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
          style={{ color: isDarkTheme ? '#3498db' : '#61dafb' }}
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
