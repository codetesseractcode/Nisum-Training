# Spring Security Demo - Assignment 1

## Overview
This Spring Boot application demonstrates both Basic Authentication and Form-based Authentication using Spring Security with in-memory user authentication and role-based authorization.

## Features Implemented
✅ Spring Boot application with Spring Security
✅ Basic Authentication for REST APIs  
✅ Form-based Authentication for Web Interface
✅ In-memory authentication provider with 3 users
✅ Role-based authorization (USER, MANAGER, ADMIN)
✅ Custom Login/Logout pages
✅ REST endpoints with different access levels
✅ Unit tests for security configurations

## Quick Start

### 1. Run the Application
```bash
mvn spring-boot:run
```

### 2. Access the Application
- **Home Page**: http://localhost:8080
- **Login Page**: http://localhost:8080/login
- **Dashboard**: http://localhost:8080/dashboard (after login)

### 3. Test Users
| Username | Password | Roles |
|----------|----------|-------|
| user | user123 | USER |
| manager | manager123 | USER, MANAGER |
| admin | admin123 | USER, ADMIN |

## Testing Both Authentication Methods

### Form-Based Authentication (Web Browser)
1. Go to http://localhost:8080
2. Click "Login to Dashboard"  
3. Use any of the test credentials above
4. Access different API endpoints from the dashboard

### Basic Authentication (API Testing)
Use curl or Postman with Basic Auth headers:

```bash
# User endpoint (accessible by all authenticated users)
curl -u user:user123 http://localhost:8080/api/user/profile

# Manager endpoint (requires MANAGER role)
curl -u manager:manager123 http://localhost:8080/api/manager/reports

# Admin endpoint (requires ADMIN role)  
curl -u admin:admin123 http://localhost:8080/api/admin/users

# Public endpoint (no authentication required)
curl http://localhost:8080/api/public/info
```

## API Endpoints

### Public Endpoints
- `GET /` - Home page
- `GET /login` - Login page
- `GET /api/public/info` - Public API (no auth required)

### Protected Endpoints
- `GET /dashboard` - User dashboard (any authenticated user)
- `GET /api/user/profile` - User profile (USER, MANAGER, ADMIN)
- `GET /api/manager/reports` - Manager reports (MANAGER only)
- `GET /api/admin/users` - Admin users (ADMIN only)

## Security Configuration Highlights

### SOLID Principles Applied
- **Single Responsibility**: Separate classes for config, controllers, and application
- **Open/Closed**: Easily extensible for additional authentication methods
- **Interface Segregation**: Uses Spring Security interfaces appropriately
- **Dependency Inversion**: Depends on Spring Security abstractions

### YAGNI Principle
- Only implemented required features
- No over-engineering or unnecessary complexity
- Simple, clean configuration

## Running Tests
```bash
mvn test
```

## Architecture
```
src/main/java/com/nisum/security/
├── SecurityDemoApplication.java     # Main Spring Boot application
├── config/
│   └── SecurityConfig.java         # Security configuration
└── controller/
    ├── ApiController.java          # REST API endpoints
    └── WebController.java          # Web page controllers

src/main/resources/
├── application.properties          # App configuration
└── templates/                      # Thymeleaf templates
    ├── home.html
    ├── login.html
    ├── dashboard.html
    └── access-denied.html
```

## Assignment Requirements Fulfilled

✅ **Basic Authentication**: Configured for REST APIs with in-memory users
✅ **Form-based Authentication**: Custom login page with proper handling
✅ **Spring Boot + Spring Security**: Complete integration
✅ **In-memory Authentication**: 3 users with different roles
✅ **REST Endpoints**: Protected endpoints for different user roles
✅ **Custom Login Screen**: Thymeleaf-based login page
✅ **Login/Logout URLs**: Customized authentication flows
✅ **Role-based Testing**: Different access levels verified

## Quick Demo Steps
1. Start application: `mvn spring-boot:run`
2. Open browser: http://localhost:8080
3. Test form login with different users
4. Test API endpoints with curl commands above
5. Verify role-based access restrictions

Hope your grandma feels better soon! 🙏
