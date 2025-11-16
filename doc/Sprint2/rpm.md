
# Release Planning Meeting (rpm.md)

**Project**: YorkU Marketplace  
**Release ID**: R1 — “MarketPlace for YorkU Students”  
**Target Window**: Oct 20 – Dec 1, 2025

---

## Participants
- **Product Owner**: PurveshKumar Patel  
- **Scrum Master**: Jeelkumar Patel  
- **Developers**: PurveshKumar Patel, JeelKumar Patel, Manav Advani, Pooja Shah, Pulkit Grover  
- **QA**: Pooja Shah  

---

## Release Goal
Deliver a usable campus-only marketplace with verified login, listing creation/browse, and a demoable buyer–seller flow.

---

## Scope (Epics)
- **E1** Account & Verification (minimal SSO, profile scaffold) → *US-001, US-002, US-003*  
- **E2** Listings & Search (create/browse/detail/upload) → *US-004, US-005, US-006, US-007*  
- **E3** Messaging & Safety (DMs, report) — partial in R1 → *US-008, US-009*  
- **E4** Transactions & Reviews — deferred except “mark sold” → *US-010*

---

## Timeline

| Sprint | Duration | Focus | Related User Stories |
|--------|-----------|--------|----------------------|
| **Sprint 1** | Oct 20 – Nov 3 | Authentication, Email Verification, Docs, Demo | **US-001**, **US-002**, **US-004**, **US-005**, **DOC-01** |
| **Sprint 2** | Nov 4 – Nov 17 | Image Hosting, Search/Browse, Profile, Basic Moderation | **US-003**, **US-006**, **US-007**, **US-008** |
| **Sprint 3** | Nov 18 – Dec 1 | Messaging, Mark Sold & Rating, Polish & Performance | **US-009**, **US-010**, **BUG-001**, **OPS-001** |

---

## Capacity & Velocity Assumptions
- Team capacity ≈ **4 story points/day** (balancing classes & midterms).  
- Estimated velocity ≈ 24 SP per sprint; plan at **80% confidence**.

---

## Done / Ready
- **Definition of Ready (DoR)** and **Definition of Done (DoD)** as detailed in `pb.md`.  
- Code hosted on GitHub under `feature/*` branches with mandatory PR reviews and unit tests via CI.

---

## Risks & Mitigations
| Risk | Impact | Mitigation |
|------|---------|-------------|
| **SSO approval delay** | Login blocked for demo | Use email/password fallback with feature flag; swap to SSO later |
| **Image hosting cost** | Budget overrun | Use MinIO/local in dev; S3 with lifecycle rules in prod |
| **Time crunch (midterms)** | Sprint 1 scope at risk | Narrow to vertical slice (MVP); push Messaging to Sprint 3 |

---

## Release Metrics
- Successful **sign-ups** (US-001)  
- Working **Forgot Password** flow (US-002)  
- Persistent **data hosting** via Nile DB (OPS-002)  
- **Demo completeness** and UI stability at Sprint Review  


---

# Sprint Progress Summary (Updated from Standup Reports)

## Sprint 2 Progress  
### Based on Standups:
- **Nov 10 Standup** fileciteturn1file3  
  - Image service hosted locally and syncing properly.
  - Listings now include username, price, description, image.
  - Edit/delete functionality planned.

- **Nov 12 Standup** fileciteturn1file2  
  - Edit/delete implemented.
  - Issues discovered: sometimes edits fail (404), sometimes wrong users can edit.
  - Next: restrict editing to listing owners only.
  - Minor blockers due to inconsistent API behaviour.

- **Nov 14 Standup** fileciteturn1file1  
  - Edit/delete finalized so only creators can modify posts.
  - Next: add “meetup/exchange location selection”.
  - Blocker: team still designing approach.

## Updated Sprint 2 Scope  
- Finalized ownership‑restricted edit/delete.  
- Stable image hosting + listing creation pipeline.  
- Next priority: exchange‑location selection UI + backend.  
- Blockers: design decision pending at team meeting.

