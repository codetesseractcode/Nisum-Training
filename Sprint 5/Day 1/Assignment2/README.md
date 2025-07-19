# Assignment 2: DAO and LDAP Authentication

This Spring Boot application demonstrates both DAO-based and LDAP-based authentication using Spring Security.

## Features

### DAO-Based Authentication
- User authentication against H2 in-memory database
- Custom UserDetailsService implementation
- JPA entities and repositories
- Password encoding with BCrypt
- Role-based access control

### LDAP Authentication
- Embedded LDAP server using UnboundID
- LDIF file configuration
- LDAP user authentication
- Role mapping from LDAP groups

## Pre-configured Test Users

### Database Users (DAO Authentication)
- `admin/admin123` - ADMIN role
- `user/user123` - USER role  
- `testuser/test123` - USER role

### LDAP Users
- `ben/benspassword` - USER role
- `bob/bobspassword` - USER role
- `joe/joespassword` - ADMIN role

## API Endpoints

### Database Authentication Endpoints (`/api/db/*`)
- `POST /api/db/public/register` - Register new user (public)
- `GET /api/db/public/info` - Public information (no auth)
- `GET /api/db/user/profile` - User profile (USER/ADMIN role)
- `GET /api/db/admin/users` - List all users (ADMIN role)

### LDAP Authentication Endpoints (`/api/ldap/*`)
- `GET /api/ldap/public/info` - Public information (no auth)
- `GET /api/ldap/user/profile` - User profile (USER/ADMIN role)
- `GET /api/ldap/admin/info` - Admin information (ADMIN role)

## Running the Application

```bash
mvn spring-boot:run
```

The application will start on port 8080.

## Testing Authentication

### Database Authentication Examples

1. **Register a new user:**
```bash
curl -X POST http://localhost:8080/api/db/public/register \
  -H "Content-Type: application/json" \
  -d '{"username":"newuser","password":"password123","role":"USER"}'
```

2. **Access user profile (with authentication):**
```bash
curl -u admin:admin123 http://localhost:8080/api/db/user/profile
```

3. **Access admin endpoint:**
```bash
curl -u admin:admin123 http://localhost:8080/api/db/admin/users
```

### LDAP Authentication Examples

1. **Access user profile via LDAP:**
```bash
curl -u ben:benspassword http://localhost:8080/api/ldap/user/profile
```

2. **Access admin endpoint via LDAP:**
```bash
curl -u joe:joespassword http://localhost:8080/api/ldap/admin/info
```

## Configuration

- H2 Database Console: http://localhost:8080/h2-console
- LDAP Server Port: 8389
- Application Port: 8080

## Architecture

The application follows SOLID principles:
- **Single Responsibility**: Each class has one responsibility
- **Open/Closed**: Extensible authentication providers
- **Liskov Substitution**: UserDetailsService implementations
- **Interface Segregation**: Separate interfaces for different concerns
- **Dependency Inversion**: Dependency injection throughout

YAGNI principle applied by implementing only required features without over-engineering.
