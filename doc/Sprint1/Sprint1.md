# Sprint 1 Planning Meeting – YorkU Marketplace
**Date:** October 27, 2025  
**Sprint Duration:** October 27 – November 3, 2025  
**Sprint Goal:**  
Deliver a functional backend and basic authentication flow for YorkU Marketplace, allowing verified York University users to create accounts, log in, and reset passwords.

---

## Sprint Goal
The primary objective for Sprint 1 is to establish a working foundation for the platform with:
- Secure **user signup** restricted to YorkU email addresses.
- Basic **login** and **forgot password** functionalities.
- Live connection between frontend and backend using **Spring Boot** and **React**.
- CI/CD setup on GitHub for future sprint deployments.

---

## Participants
| Name | Role |
|------|------|
| Jeel Patel | Scrum Master / Tester|
| Pooja Shah | QA / Documentation |
| Pulkit Grover | Documentation |
| Purvesh Patel | Product Owner / Frontend & Backend Developer |
| Manav Advani |  Meeting notes |

---

## Team Capacity
| Member | Weekly Hours | Notes |
|---------|---------------|-------|
| Jeel Patel | 6 hrs | Code testing, Standup meetings and meeting notes |
| Pooja Shah | 6 hrs | System Design Docs and frontend design |
| Pulkit Grover | 6 hrs | Management of trello, Product Backlog |
| Purvesh Patel | 10 hrs | UI design and integration, Backend,Website Hosting |
| Manav Advani | 4 hrs | Docs |
| **Total** | **32 hrs** | |

---

## User Stories for Sprint 1
| ID | User Story | Priority | Acceptance Criteria | Status |
|----|-------------|-----------|---------------------|--------|
| US-01 | As a new user, I want to sign up using my YorkU email so that only verified users can access the platform. | High | Validation rejects non-YorkU emails. | ✅ In Progress |
| US-02 | As a user, I want to log in securely so that I can access my dashboard. | High | Successful login with stored credentials. | 🟡 Pending |
| US-03 | As a user, I want to reset my password if I forget it. | Medium | “Forgot Password” form sends reset link. | ✅ Implemented |
| US-04 | As a developer, I want backend APIs live and connected so the frontend can consume them. | High | Spring Boot API reachable and integrated. | ✅ Implemented |
| US-05 | As a team, we want a working CI/CD pipeline. | Low | GitHub Actions runs test and build jobs. | 🟡 In Progress |

---

##  Task Breakdown
| Story ID | Tasks | Assigned To | Est. Hours |
|-----------|--------|--------------|------------|
| US-01 | Implement email validation regex, backend signup endpoint | Pulkit | 4 |
| US-01 | Create signup page UI with form validation | Purvesh | 3 |
| US-02 | Configure Spring Security for login | Manav | 4 |
| US-02 | Create login form and connect to backend | Jeel | 3 |
| US-03 | Implement “Forgot Password” API and mock email response | Pulkit | 3 |
| US-04 | Deploy backend to live environment | Manav | 2 |
| US-04 | Connect React frontend with live backend URL | Jeel | 2 |
| US-05 | Setup GitHub Actions for Maven + React builds | Pooja | 4 |
| US-05 | Write unit tests for backend endpoints | Pooja | 3 |

---

## Decisions & Notes
- **Scope Adjusted:** Marketplace listings and search features postponed to Sprint 2.
- **Technical Stack:** Spring Boot (backend), React (frontend), MySQL (database), Maven (build), GitHub CI/CD.
- **Code Standards:** Each PR requires at least one peer review.
- **Retrospective:** Planned for Nov 3 immediately after Sprint 1 demo.

---

## Deliverables for Sprint 1
- Functional **Signup, Login, and Forgot Password** pages.
- Running **Spring Boot backend** connected to MySQL.
- **Zoom demo recording (~3 min)** showing team presence.
- Updated **README.md** with setup instructions.

---

**Prepared by:**  
Jeel Patel – Scrum Master  
**Date:** November 3, 2025
