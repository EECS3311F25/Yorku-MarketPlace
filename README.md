# YorkU Marketplace (Group P)

A Spring Boot–based campus-only marketplace prototype for York University. It currently implements **user sign up / sign in** with hashed passwords, **Thymeleaf** views, and a **PostgreSQL** datasource via Spring Data JPA.

> **Course:** EECS 3311 — Software Design (Fall 2025)  
> **Repo:** `Yorku-MarketPlace`  
> **Java:** 21  
> **Boot Parent:** 4.0.0-M3  

---

## ✨ Features (current)
- **Authentication flow (prototype):** `/login`, `/signup`, `/home` with `BCryptPasswordEncoder` password hashing.
- **User entity & JPA repository:** `User`, `UserRepository` with unique username.
- **Server-side rendering:** Thymeleaf templates in `src/main/resources/templates` and shared CSS in `static/styles.css`.
- **PostgreSQL integration:** configured via Spring Boot datasource properties.

Planned next: listings, search/filter, buyer–seller messaging, moderation tools, and role-based access.

---

