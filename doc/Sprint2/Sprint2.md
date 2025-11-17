# Sprint 2 Planning Meeting – YorkU Marketplace

**Date:** November 4, 2025  
**Sprint Duration:** Nov 4 – Nov 17, 2025  
**Sprint Goal:**  
Deliver image hosting, listing search/browse, profile setup, and complete editing/deleting with proper ownership controls.

---

## Sprint Goal
Establish full **listing workflows**:
- Upload images with correct storage
- Edit/delete listings only by their owner
- Add browse + search functionality
- Add profile basics (name/program)
- Prepare for exchange-location UI
- Strengthen backend structure from Sprint 1

---

## Participants
| Name | Role |
|------|------|
| Jeel Patel | Scrum Master / Tester |
| Pooja Shah | QA / Docs |
| Pulkit Grover | Product Backlog / Trello |
| Purvesh Patel | Full-Stack Developer / Deployment |
| Manav Advani | Docs / Notes |

---

## Team Capacity (Sprint 2)
| Member | Weekly Hours | Tasks |
|---------|--------------|--------|
| Jeel | 6 | Testing, Listing UI, Search |
| Pooja | 6 | QA, Docs Updates |
| Pulkit | 6 | PB, Trello, Testing |
| Purvesh | 10 | Image Hosting, Backend Integration |
| Manav | 4 | Documentation, Demo script |

**Total Capacity:** **32 hours**  
**Expected Velocity:** 22–26 SP (based on Sprint-1 actual velocity)

---

# SPRINT-2 USER STORIES (Committed)

| ID | Story | Priority | SP | Notes |
|----|--------|-----------|----|--------|
| **US-006** | Image Upload Service | P1 | 5 | Local + remote hosting |
| **US-007** | Search/Browse MVP | P2 | 8 | Filters + search bar |
| **US-003** | Profile Scaffold | P2 | 3 | Name + program |
| **US-011** | Edit/Delete Ownership Enforcement | P0 | 3 | Fixed in standups |
| **US-012** | Meetup/Exchange Location (UI Design)** | P1 | 5 | Start UI only in Sprint 2 |
| **BUG-002** | Wrong Owner Editing | P0 | 2 | Fix validated in standup |
| **US-013** | Image Hosting Reliability | P2 | 3 | Retry + fallback |

**Total Points:** 29 SP (within velocity range)

---

# TASK BREAKDOWN

| Story | Tasks | Owner | Est. Hrs |
|--------|--------|---------|-----------|
| US-006 | Implement upload API + thumbnail generation | Purvesh | 5 |
| US-006 | Connect image hosting to listings | Jeel | 3 |
| US-007 | Search bar + category filter UI | Jeel | 4 |
| US-007 | Backend filter endpoints | Purvesh | 4 |
| US-003 | Profile form UI + validation | Pulkit | 3 |
| US-011 | Ownership check middleware | Purvesh | 2 |
| US-012 | UI mockups + page structure | Manav | 3 |
| US-013 | Retry logic for uploads | Purvesh | 3 |
| BUG-002 | Add backend guard + test cases | Pooja | 2 |

---

# DEPENDENCIES
- Image hosting must be stable before browse/search goes live  
- Edit/delete ownership must be fixed before enabling search indexing  
- Profile is independent → can be done early  

---

# RISKS & MITIGATION
| Risk | Mitigation |
|------|-------------|
| Image hosting instability | Add fallback path + retries |
| Search performance slow | Add pagination + indexing |
| Midterms reducing capacity | Freeze scope at 29 SP |

---

# DELIVERABLES
- Image hosting fully functional  
- Search/browse working  
- Profile page operational  
- Ownership-protected listing editing  
- Updated PB.md & design docs  
- Updated README & API docs  
- Sprint 2 demo video (all members present)

