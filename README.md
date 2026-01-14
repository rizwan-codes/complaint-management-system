# Complaint Management System

A Spring Boot REST API for managing complaints with JWT authentication and role-based access control.

## Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Security with JWT
- Spring Data JPA
- H2 Database
- Maven

## Setup Instructions

1. Clone the repository
2. Run the application:
```bash
mvn spring-boot:run
```
3. Application runs on `http://localhost:8081`

## Sample Credentials

**Admin User:**
- Username: `rizwan`
- Password: `rizwan123`

**Regular User:**
- Username: `yaqoob`
- Password: `yaqoob123`

## API Endpoints

### Authentication (Public)

**Register**
```
POST /auth/register
{
    "username": "yaqoob",
    "email": "yaqoob@example.com",
    "password": "yaqoob123"
}
```

**Login**
```
POST /auth/login
{
    "username": "yaqoob",
    "password": "yaqoob123"
}
```

### Complaints (Protected - Requires JWT Token)

**Create Complaint** (USER/ADMIN)
```
POST /complaints
Authorization: Bearer <token>
{
    "title": "Land dispute issue",
    "description": "Boundary dispute with neighbor",
    "category": "LAND"
}
```

**Get My Complaints** (USER/ADMIN)
```
GET /complaints/my
Authorization: Bearer <token>
```

**Get All Complaints** (ADMIN only)
```
GET /complaints/all
Authorization: Bearer <token>
```

**Search Complaints** (ADMIN only)
```
GET /complaints/search?status=NEW
GET /complaints/search?category=LAND
GET /complaints/search?status=IN_PROGRESS&category=PROPERTY
Authorization: Bearer <token>
```

**Update Status** (ADMIN only)
```
PUT /complaints/{id}/status
Authorization: Bearer <token>
{
    "status": "IN_PROGRESS"
}
```

### Notifications (Protected)

**Get My Notifications** (USER/ADMIN)
```
GET /notifications/my
Authorization: Bearer <token>
```

## Enums

**Category:** LAND, PROPERTY, SERVICE  
**Status:** NEW, IN_PROGRESS, RESOLVED  
**Role:** USER, ADMIN

## Testing with Postman

**Option 1: Import Collection (Recommended)**
1. Open Postman
2. Click "Import"
3. Select `Complaint Managment System.postman_collection.json` from the repository
4. All API requests are pre-configured and ready to use

**Option 2: Manual Testing**
1. Login to get JWT token
2. Copy the token
3. Add header to protected endpoints:
   - Key: `Authorization`
   - Value: `Bearer <your-token>`

## Features

- User registration and JWT authentication
- Role-based access control (USER/ADMIN)
- Create and manage complaints
- Search complaints by status/category
- Automatic notifications on complaint creation and status updates
- Global exception handling
- H2 in-memory database
