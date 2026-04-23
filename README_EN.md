# EaseChat

![License](https://img.shields.io/badge/license-MIT-green)
![Java](https://img.shields.io/badge/Java-21-orange)
![Vue](https://img.shields.io/badge/Vue-3-brightgreen)

[English](README_EN.md) | [中文](README.md)

---

## EaseChat - Real-time Chat Application

A modern multi-user real-time chat application that provides an intuitive user interface and powerful backend support.

## 📋 Features

- ✨ User registration and login authentication
- 💬 Create and join chat rooms
- 📤 Support for text, image, and audio messages
- 👤 User profile management
- 🎨 Responsive design interface
- 🔐 Secure authentication mechanism

## 🛠 Tech Stack

### Frontend
- **Framework**: Vue 3
- **Build Tool**: Vite
- **UI Library**: Element Plus
- **HTTP Client**: Axios
- **Routing**: Vue Router
- **Development Port**: 5173

### Backend
- **Framework**: Spring Boot 3.4.2
- **Java Version**: 21
- **Database**: MySQL 8.x
- **ORM**: MyBatis-Plus
- **Server Port**: 8081

## 📦 Requirements

### Prerequisites

**Backend Requirements:**
- Java 21 or higher
- MySQL 5.7 or higher
- Maven 3.6+

**Frontend Requirements:**
- Node.js 20.19.0 or higher (or >= 22.12.0)
- npm or yarn

## 🚀 Quick Start

### 1. Database Configuration

```sql
-- Create database
CREATE DATABASE easechat CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Import database schema
SOURCE easechat/src/main/resources/db.sql;
```

### 2. Backend Setup

```bash
# Navigate to backend directory
cd easechat

# Update database configuration
# Edit src/main/resources/application.properties
# Modify: spring.datasource.username, spring.datasource.password, spring.datasource.url

# Build the project
mvn clean package

# Run the backend server
mvn spring-boot:run
```

The backend server will start on `http://localhost:8081`

### 3. Frontend Setup

```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Start the development server
npm run dev
```

The frontend application will be available at `http://localhost:5173`

### 4. Access the Application

Open your browser and visit `http://localhost:5173` to use the application

## 📁 Project Structure

```
Easechat/
├── easechat/                    # Backend (Spring Boot)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/           # Java source code
│   │   │   └── resources/       # Configuration files and database scripts
│   │   └── test/                # Test code
│   ├── pom.xml                 # Maven configuration file
│   └── mvnw                    # Maven wrapper
│
├── frontend/                    # Frontend (Vue 3)
│   ├── src/
│   │   ├── components/         # Vue components
│   │   ├── views/              # Page views
│   │   ├── router/             # Router configuration
│   │   ├── utils/              # Utility functions
│   │   ├── assets/             # Static assets
│   │   └── main.js             # Entry file
│   ├── package.json            # npm configuration file
│   └── vite.config.js          # Vite configuration file
│
├── README.md                    # Project README (中文)
├── README_EN.md                 # Project README (English)
├── SETUP.md                     # Detailed setup guide
└── HELP.md                      # Help documentation
```

## 🔧 Main Modules

### Backend Modules
- **Controller**: HTTP request handlers
- **Entity**: Database entity models
- **Mapper**: MyBatis data access layer
- **Config**: Application configuration (CORS, database, etc.)
- **Common**: Common utilities and exception handling

### Frontend Modules
- **Home**: Home page
- **Login**: Login page
- **Register**: Registration page
- **Router**: Application routing configuration

## 📝 API Endpoints

### User Related
- `POST /api/user/register` - User registration
- `POST /api/user/login` - User login
- `GET /api/user/current` - Get current user info
- `POST /api/user/update` - Update user profile

### Chat Room Related
- `GET /api/room/list` - Get chat room list
- `GET /api/room/self/list` - Get my chat rooms
- `POST /api/room/save` - Create new chat room
- `POST /api/room/delete` - Delete chat room

### Blog Related
- `GET /api/blog/list` - Get blog list
- `POST /api/blog/save` - Create blog
- `POST /api/blog/delete` - Delete blog

## 🐛 Troubleshooting

### Backend fails to start
- Check if Java version is 21+
- Ensure MySQL database is running
- Verify database configuration in application.properties

### Frontend cannot connect to backend
- Check backend API address configuration in request.js
- Ensure backend server is running
- Check for CORS errors in browser console

### Database connection failure
- Verify MySQL username and password
- Confirm easechat database has been created
- Check if db.sql script has been executed

For more help, see [SETUP.md](SETUP.md) and [HELP.md](easechat/HELP.md)

## 📄 License

MIT License - See LICENSE file for details

## 👨‍💻 Developers

Developer and maintainer information

---

**Last Updated**: February 2026
