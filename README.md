# 🏫 YorkU Marketplace (Group P)

A **Spring Boot + Spring Security**–based prototype for a **campus-only online marketplace** at **York University**.  
The application currently supports **user authentication**, **email verification**, **password reset**, and secure **login/logout** flows with a responsive **Tailwind + Thymeleaf** frontend.

> **Course:** EECS 3311 — Software Design (Fall 2025)  
> **Repository:** `Yorku-MarketPlace`  
> **Java:** 21  
> **Spring Boot:** 3.3+  
> **Build Tool:** Maven  

---

## ✨ Features (Implemented)

✅ **Authentication Flow**
- Secure **sign up**, **sign in**, and **logout** using **Spring Security 6**.
- Passwords hashed with **BCryptPasswordEncoder**.
- Prevents duplicate **email** and **username** registrations.
- Email verification using a 6-digit code.
- Password reset with secure tokens and expiry time.
- Session-based login with `/home` protected route.

✅ **Frontend (Thymeleaf + TailwindCSS)**
- Pages: `login.html`, `signup.html`, `verify.html`, `forgot-password.html`, `reset-password.html`, `home.html`.
- Clean, responsive UI with alerts and form validation.
- Works seamlessly with Spring Security error parameters (`?error`, `?logout`, etc.).

✅ **Backend (Spring Boot)**
- Entity: `User`
- Repository: `UserRepository` (JPA)
- Services: `AuthService`, `CustomUserDetailsService`
- Controller: `AuthController`
- Config: `SecurityConfig` with `DaoAuthenticationProvider`

✅ **Database**
- Uses **PostgreSQL** (local or Docker) with Spring Data JPA.
- Supports auto table creation via Hibernate.

---

## 🛠️ Installation & Setup

### 1️⃣ Prerequisites
Make sure you have:
- **Java 21**
- **Maven 3.9+**
- **PostgreSQL 14+** (or use server already provided in this project)
- **Git**

---

### 2️⃣ Clone the Repository
```bash
git clone https://github.com/<your-org-or-username>/Yorku-MarketPlace.git
cd Yorku-MarketPlace
```
### 3️⃣ Configure PostgreSQL and SMTP if you're using local SQL and Email server (Skip if you want to use the one provided in Project)
Install PostgreSQL on your PC / Docker
Then edit your src/main/resources/application.yml
change the configured url to something like this jdbc:postgresql://localhost:5432/marketplace_db
and change the username and password.
Repeat the same steps for smtp settings

### 4️⃣ Build & Run the App
```bash mvn clean install
mvn spring-boot:run
```

Then Wait for 15-30 secs till you see Tomcat started on port 8080 (http) with context path '/'
then open:
👉 http://localhost:8080/login


### 🧩 Project Structure
src/
├── main/
│   ├── java/com/marketplace/
│   │   ├── controller/AuthController.java
│   │   ├── service/AuthService.java
│   │   ├── service/CustomUserDetailsService.java
│   │   ├── config/SecurityConfig.java
│   │   ├── model/User.java
│   │   └── repository/UserRepository.java
│   ├── resources/
│   │   ├── templates/
│   │   │   ├── login.html
│   │   │   ├── signup.html
│   │   │   ├── verify.html
│   │   │   ├── home.html
│   │   │   ├── forgot-password.html
│   │   │   └── reset-password.html
│   │   └── application.properties
│   └── static/
│       └── styles.css
└── test/

### 🧱 Future Work

Marketplace listings (CRUD)

Messaging system (buyer ↔ seller)

Role-based access (admin / moderator)

Image upload and profile customization