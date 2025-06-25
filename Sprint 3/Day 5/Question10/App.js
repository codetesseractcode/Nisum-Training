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
import Dashboard from './Dashboard';
import Layout from './Layout';
import HomePage from './HomePage';
import AboutPage from './AboutPage';

function App() {
  const userName = "Ayusman Pradhan"; // Dynamic value that can be changed
  const [isDarkTheme, setIsDarkTheme] = useState(false);
  const [currentPage, setCurrentPage] = useState('Home');

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

  // Function to render page content based on currentPage
  const renderPageContent = () => {
    switch (currentPage) {
      case 'Home':
        return <HomePage isDarkTheme={isDarkTheme} />;
      case 'About':
        return <AboutPage isDarkTheme={isDarkTheme} />;
      case 'Services':
        return (
          <div>
            <h1 style={{ textAlign: 'center', color: isDarkTheme ? '#3498db' : '#2c3e50' }}>
              🛠️ Our Services
            </h1>
            <div style={{ padding: '20px', textAlign: 'center' }}>
              <p>This is the Services page wrapped in the Layout component.</p>
              {/* You can add your existing components here */}
              <div style={{ margin: '20px 0' }}>
                <Greeting name={userName} isDarkTheme={isDarkTheme} />
                <ThemeToggle isDarkTheme={isDarkTheme} toggleTheme={toggleTheme} />
                <Counter isDarkTheme={isDarkTheme} />
              </div>
            </div>
          </div>
        );
      case 'Contact':
        return (
          <div>
            <h1 style={{ textAlign: 'center', color: isDarkTheme ? '#3498db' : '#2c3e50' }}>
              📞 Contact Us
            </h1>
            <div style={{ padding: '20px', textAlign: 'center' }}>
              <p>This is the Contact page wrapped in the Layout component.</p>
              {/* You can add your existing components here */}
              <div style={{ margin: '20px 0' }}>
                <TodoList todos={sampleTodos} isDarkTheme={isDarkTheme} />
                <ProductCard 
                  title="iPhone 15"
                  price={1099}
                  description="Latest model with improved battery life."
                  isDarkTheme={isDarkTheme}
                />
                <Dashboard 
                  userProfile={sampleUser}
                  notifications={sampleNotifications}
                  isDarkTheme={isDarkTheme}
                />
              </div>
            </div>
          </div>
        );
      default:
        return <HomePage isDarkTheme={isDarkTheme} />;
    }
  };

  return (
    <Layout 
      isDarkTheme={isDarkTheme} 
      toggleTheme={toggleTheme}
      currentPage={currentPage}
      setCurrentPage={setCurrentPage}
    >
      {renderPageContent()}
    </Layout>
  );
}

export default App;
