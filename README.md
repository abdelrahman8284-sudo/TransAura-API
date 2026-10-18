# 🎙 SPOKIFY – AI Powered Audio Transcription Platform

## 📌 Description

SPOKIFY is a full-stack audio transcription platform designed to convert voice recordings into organized and meaningful text content.

The platform allows users to:

- 🎤 Record audio directly from the browser
- 📤 Upload audio files
- 📝 Store generated transcriptions
- ✨ Save enhanced transcription results
- 📌 Manage summaries, detected topics, and extracted tasks
- 🎧 Replay uploaded recordings
- 📂 Access personal transcription history
- 🔐 Authenticate securely using JWT Authentication
- 🔄 Reset passwords via email verification

The backend architecture was redesigned and rebuilt using **Spring Boot** and **MongoDB**, replacing the original backend implementation with a more scalable and maintainable enterprise-level solution.

The backend communicates with the frontend and AI processing services through RESTful APIs, handling:
- User authentication & authorization
- Audio file management
- Transcription storage
- Audio streaming
- User transcription history
- Secure data persistence

The application follows a clean layered architecture:

- Controller → Service → Repository
- DTO-based communication
- RESTful API design
- Secure authentication & authorization
- Modular and maintainable backend structure

This project was developed as a Graduation Project focused on:
- Modern Backend Engineering
- Audio Processing Systems
- RESTful API Development
- Full-Stack Application Architecture

## 🛠 Tech Stack

### Backend
- **Java 21**
- **Spring Boot**
- **Spring Security**
- **JWT Authentication**
- **Spring Data MongoDB**
- **Spring Validation**
- **Maven**
- **Lombok**
- **Java Mail Sender**

### Database
- **MongoDB**

## 🐳 Containerization
- Docker
- Docker Compose
- Multi-stage Docker Build
- MongoDB Container
- Docker Volumes

### Frontend
- **React**
- **TypeScript**
- **Tailwind CSS**
- **Axios**

### Mobile Application
- **Flutter**

### Audio & File Handling
- **Multipart File Upload**
- **Local Audio Storage**
- **Audio Streaming**
- **MediaRecorder API**

### Architecture & APIs
- **RESTful APIs**
- **Layered Architecture**
- **DTO-Based Communication**
- **CORS Configuration**

---

## 🚀 Features

- 🔐 JWT-based Authentication & Authorization
- 👤 User Registration & Login System
- 🔄 Password Reset via Email Verification
- 🎤 Real-time Audio Recording
- 📤 Audio File Upload Support
- 💾 Audio File Storage Management
- 📝 Transcription Management
- ✨ Enhanced Text Storage
- 📌 Topic Detection Results Management
- ✅ Task Extraction Results Management
- 🧠 Summary Results Management
- 🎧 Audio Playback Support
- 📂 User Transcription History
- 🔍 Fetch Transcriptions by User
- 🗑 Delete Transcriptions
- ☁️ Local Audio Resource Serving
- 🌐 Frontend Integration using REST APIs
- 🔒 Secure Password Encoding
- ⚠️ Global Exception Handling
- 🧱 Clean Layered Backend Architecture
- 📦 MongoDB Document-Based Persistence
- 🐳 Dockerized Backend Application
- 📦 Docker Compose for Running the Backend and MongoDB
- 💾 Persistent MongoDB Data Using Docker Volumes
- ⚙️ Environment Variable Configuration
- 🚀 Simplified Setup Without Installing Java, Maven, or MongoDB Locally

## 📡 API Endpoints

### 🔐 Authentication

| Method | Endpoint | Description |
|------|------|------|
| POST | `/api/v1/auth/register` | Register a new user |
| POST | `/api/v1/auth/login` | Authenticate user and generate JWT token |
| PUT | `/api/v1/auth/updateProfile/{id}` | Update user profile information |
| POST | `/api/v1/auth/forgot-password` | Send password reset email |
| POST | `/api/v1/auth/reset-password` | Reset password using reset token |
| GET | `/api/v1/auth/user/{id}` | Get user information by ID |

---

### 🎙 Transcriptions

| Method | Endpoint | Description |
|------|------|------|
| POST | `/api/transcriptions` | Create and save a transcription with audio upload |
| GET | `/api/transcriptions/getAll` | Retrieve all transcriptions |
| GET | `/api/getTrancriptionById/{id}` | Retrieve transcription by ID |
| DELETE | `/api/transcriptions/{id}` | Delete transcription by ID |
| GET | `/api/transcriptions/user/{userId}` | Retrieve all transcriptions for a specific user |

---

## 🔐 Authentication

The application uses **JWT (JSON Web Token)** based authentication for securing APIs and managing user sessions.

### Authentication Features

- Secure user registration & login
- Password hashing using Spring Security
- Stateless authentication using JWT
- Protected API endpoints
- Password reset via email verification
- User profile management

### Authentication Flow

1. User registers or logs in
2. Backend validates credentials
3. JWT token is generated
4. Frontend stores the token
5. Token is sent with protected API requests

Example Authorization Header:

```http
Authorization: Bearer your_jwt_token

## 📸 Screenshots

Project screenshots will be added in the following folder:

📂 `screenshots/`

The screenshots section may include:

- 🔐 Authentication pages (Login / Register)
- 🎙 Audio upload & recording interface
- 📝 Transcription results
- 📋 AI-generated summaries, tasks, and detected topics
- 📂 User transcription history
- 📱 Mobile application screens
- 🗄 MongoDB stored transcription documents

> ⚠️ Screenshots will be added soon.

---

## ⚙️ Installation Guide

Follow the steps below to run the backend locally.

---

### 1️⃣ Prerequisites

Make sure you have installed:

- Java 21
- Maven
- MongoDB
- Git
- IntelliJ IDEA / STS (recommended)

---

### 2️⃣ Clone the Repository

```bash
git clone https://github.com/abdelrahman8284-sudo/TransAura-API.git
cd spokify-backend
```

---

### 3️⃣ Environment Variables

This project uses environment variables for sensitive credentials.

Create the following variables in your IDE or system environment:

```env
JWT_SECRET_KEY=your_jwt_secret

MY_EMAIL_USER=your_email@gmail.com
MY_EMAIL_PASS=your_gmail_app_password
```

---

### 4️⃣ MongoDB Configuration

Make sure MongoDB is running locally on:

```txt
localhost:27017
```

Database name:

```txt
Spokify
```

---

### 5️⃣ Application Configuration

Default backend port:

```txt
4000
```

Multipart upload limit:

```txt
100MB
```

---

### 6️⃣ Build the Project

```bash
mvn clean install
```

---

### 7️⃣ Run the Application

#### ▶️ Run Normally

```bash
mvn spring-boot:run
```

#### ▶️ Run with Dev Profile

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Backend will start on:

```txt
http://localhost:4000
```

---

### 8️⃣ Main Backend Features

- JWT Authentication & Authorization
- MongoDB Integration
- Audio File Upload
- User Transcription Management
- Email-Based Password Reset
- AI-ready Backend APIs
- RESTful API Architecture

---

### ✅ Notes

- MongoDB must be running before starting the application.
- Gmail SMTP requires an App Password, not your normal Gmail password.
- Uploaded audio files support up to 100MB.
- Frontend and Mobile applications connect to this backend through REST APIs.
- AI processing services are integrated externally through frontend/mobile layers.

### 🐳 Run with Docker

Run the backend and MongoDB using Docker Compose without installing Java, Maven, or MongoDB directly on your machine.

#### 1️⃣ Prerequisites
Make sure you have installed:
- **Docker Desktop**
- **Git**

> ℹ️ *Docker Desktop includes Docker Compose.*

#### 2️⃣ Clone the Repository
```bash
git clone https://github.com/abdelrahman8284-sudo/TransAura-API.git
cd spokify-backend
```

#### 3️⃣ Configure Environment Variables
Create your local `.env` file from the provided example:

**Windows (PowerShell):**
```powershell
Copy-Item .env.example .env
```

**Linux / macOS:**
```bash
cp .env.example .env
```

Open the `.env` file and provide the required values:
```env
PORT=4000
MONGO_DB_URL=mongodb://mongodb:27017/Spokify
MY_EMAIL_USER=your_email@gmail.com
MY_EMAIL_PASS=your_gmail_app_password
JWT_SECRET_KEY=your_jwt_secret
```

**Important:**
- Replace the placeholder values with your own credentials.
- Use a Gmail App Password for email functionality.
- Keep your `.env` file private. Never commit it to GitHub.
- The MongoDB URL uses `mongodb` as the hostname because it is the MongoDB service name in Docker Compose.

#### 4️⃣ Build and Start the Application
From the project root directory, run:
```bash
docker compose up --build
```
Docker Compose will build the backend image, create the application and MongoDB containers, and start the services.

> ℹ️ *On the first run, Docker may need to download the required images and Maven dependencies.*

#### 5️⃣ Access the Backend
Once the application starts successfully, the backend will be available at:
`http://localhost:4000`

MongoDB runs in its own container, and its data is stored in a Docker volume named `mongo_data`.

#### 6️⃣ View Application Logs
To follow the logs of all services:
```bash
docker compose logs -f
```

To view only the backend logs:
```bash
docker compose logs -f app
```

#### 7️⃣ Stop the Application
Press `Ctrl + C` in the terminal, then run:
```bash
docker compose down
```
This stops and removes the containers but preserves the MongoDB data in the `mongo_data` volume.

To stop the containers and delete the stored MongoDB data as well, run:
```bash
docker compose down -v
```
> ⚠️ **Warning:** The `-v` option deletes the MongoDB volume and its stored data.

---

### 📝 Notes
- Docker Desktop must be running before executing Docker commands.
- The first build may take longer because Docker downloads images and dependencies.
- You do not need to install Java, Maven, or MongoDB locally when using Docker.
- The `.env` file is required for the application configuration.
- Email password-reset functionality requires valid Gmail SMTP credentials.
- MongoDB data persists across container restarts through the `mongo_data` volume.

# 🗄 Database Schema

## 📂 users Collection

```json
{
  "_id": "ObjectId",
  "email": "user@example.com",
  "userName": "abdelrahman",
  "password": "hashed_password",
  "resetToken": "token",
  "resetTokenExpires": "2026-05-11T10:30:00",
  "profileImage": "profile-image-url",
  "createdAt": "2026-05-11T10:00:00",
  "updatedAt": "2026-05-11T10:00:00"
}
```

### Fields

| Field | Type | Description |
|------|------|------|
| _id | ObjectId | Unique user identifier |
| email | String | User email (unique) |
| userName | String | Username |
| password | String | Encrypted password |
| resetToken | String | Password reset token |
| resetTokenExpires | DateTime | Reset token expiration |
| profileImage | String | User profile image URL |
| createdAt | DateTime | Account creation timestamp |
| updatedAt | DateTime | Last update timestamp |

---

## 📂 transcriptions Collection

```json
{
  "_id": "ObjectId",
  "transcription": "Original transcription text",
  "enhanced": "Enhanced AI transcription",
  "audio": {
    "originalName": "recording.mp3",
    "mimeType": "audio/mpeg",
    "size": 643725,
    "url": "2026/05/11/audio-file.mp3"
  },
  "metadata": {
    "summary": "Meeting summary",
    "topics": "AI, Backend",
    "filename": "recording.mp3",
    "upload_date": "2026-05-11T13:39:23",
    "language": "ar"
  },
  "tasks": "Finish backend integration",
  "user": "userId",
  "createdAt": "2026-05-11T10:39:23",
  "updatedAt": "2026-05-11T10:39:23"
}
```

### Fields

| Field | Type | Description |
|------|------|------|
| _id | ObjectId | Unique transcription identifier |
| transcription | String | Original transcription text |
| enhanced | String | Enhanced transcription text |
| audio | Object | Uploaded audio information |
| metadata | Object | AI-generated metadata |
| tasks | String | Extracted tasks |
| user | String | Owner user ID |
| createdAt | DateTime | Creation timestamp |
| updatedAt | DateTime | Last update timestamp |

---

# 🗺 Database Relationships

### Relationship Overview

- One User → Many Transcriptions
- Each Transcription belongs to one User
- Audio and Metadata are embedded documents inside Transcription

