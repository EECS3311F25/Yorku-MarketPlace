# Product Backlog (pb.md)

**Project**: YorkU Marketplace  
**Release**: R1 — “MarketPlace for YorkU Students”  
**Timeframe**: Oct 20 – Dec 1, 2025

_Backlog compiled from the Release Plan and Sprint-1 standups._

## References
- Release scope and timeline summarized in `rpm.md`.
- Standup (Oct 27): hosted site on dpdns domain, set up database via Nile Web Services. 
- Standup (Oct 30): login system + `maven-publish.yml`; redirect loop when creating listing; plan SMTP verification limited to `@yorku.ca`.
- Standup (Nov 3): forgot-password feature and demo video complete. 
- **Standup (Nov 8): Image upload service wired, basic thumbnail generation working; listing model updated to support image metadata.**
- **Standup (Nov 10): Listing edit/delete implemented but inconsistent ownership checks; began integrating browse/search filters.**
- **Standup (Nov 12): Ownership validation added on backend; fixed issue where wrong users could edit listings; search UI prototyped.**
- **Standup (Nov 14): Edit/delete fully restricted to creator; image service stabilized; next feature identified as exchange-location selection UI.**

---

## Working Agreements

- **Definition of Ready (DoR)**: User story has clear persona, goal, benefit; acceptance criteria (Given/When/Then); test ideas; sized ≤ 8 SP; dependencies identified; no external blockers.
- **Definition of Done (DoD)**: Reviewed PR merged to `main`; CI green; unit tests for new code; lint/style pass; README/CHANGELOG updated; feature visible in test env; demo script updated.

---

## Epics (from Release Plan)
- **E1 — Account & Verification** (SSO/email, profile scaffold).
- **E2 — Listings & Search** (create/browse/detail/upload).
- **E3 — Messaging & Safety** (DMs, report) — partial in R1.
- **E4 — Transactions & Reviews** — defer except “mark sold”.

---

## User Stories

| ID | Epic | Title | As a… I want… so that… | Acceptance Criteria (Given/When/Then) | Priority | Est. SP | Notes/Deps |
|---|---|---|---|---|:--:|:--:|---|
| US-001 | E1 | Email login + domain allow-list | **YorkU student**; **sign up/login** with email; **keep community York-only** | **Given** a user with `@yorku.ca` email **When** they register **Then** account is created and verification email sent; non-York emails rejected | P0 | 8 | SMTP verification planned; restrict to `@yorku.ca`. |
| US-002 | E1 | Forgot password | **user**; **reset password via link**; **recover access** | **Given** registered email **When** reset requested **Then** email sent; token valid 30 min; password policy enforced | P0 | 5 | Implemented in Sprint-1.  |
| US-003 | E1 | Basic profile scaffold | **user**; **set display name and program**; **build trust** | Edit/save profile; default avatar; validation errors shown inline | P2 | 3 | Depends on US-001 |
| US-004 | E2 | Create listing | **seller**; **post item with title, price, images**; **sell faster** | Required fields; preview; saved draft; published visible in detail page | P0 | 8 | Blocked by login redirect loop. |
| US-005 | E2 | Listing detail page | **buyer**; **view listing**; **decide quickly** | Carousel; seller card; report button; contact CTA from detail | P1 | 5 | Depends on US-004 |
| US-006 | E2 | Image upload service | **dev**; **store & serve images**; **fast previews** | Accept JPG/PNG; 5MB limit; thumbnail generated; invalid rejected | P1 | 5 | **Core of Sprint-2 work; image hosting stable as of Nov 14.** |
| US-007 | E2 | Browse & search (MVP) | **buyer**; **filter by category/price**; **find items** | Text search; category filter; sort by recent | P2 | 8 | **Sprint-2 active story; search bar + category prototype done.** |
| US-008 | E3 | Report listing/user | **user**; **report suspicious content**; **stay safe** | Report form; moderation inbox; audit trail | P3 | 5 | |
| US-009 | E3 | Direct messages (stub) | **buyer/seller**; **DM** to coordinate | Start convo from listing; unread badge | P3 | 8 | Partial in R1 |
| US-010 | E4 | Mark as SOLD & rating (defer) | **seller**; **close deal & rate** | Change status; leave 1-5 stars + comment | P4 | 5 | Defer to late R1 |
| **US-011 (NEW)** | E2 | Listing edit/delete ownership enforcement | **seller**; ensure only the creator can edit/delete | Given other users attempt modification → action denied | P0 | 3 | **Added from Sprint-2 standups. Fully implemented Nov 14.** |
| **US-012 (NEW)** | E2 | Exchange/meetup location selection | **buyer/seller**; choose safe meetup spot | Location field added; UI draft | P1 | 5 | **Identified in Nov 14 standup; to begin in Sprint-2.** |
| **US-013 (NEW)** | E2 | Image hosting reliability improvements | **dev**; ensure stable uploads | Retry logic; fallback path | P2 | 3 | **Arised from Sprint-2 image hosting tests.** |

---

## Defects / Tech Debt captured from Standups

| ID | Area | Title | Description | Priority | Fix Plan |
|---|---|---|---|:--:|---|
| BUG-001 | Auth/Routes | Login→Listing redirect loop | Creating a listing bounces back to login due to session validation | P0 | Rebuild HTML/CSS template; audit route guards; add e2e test. |
| **BUG-002** | Listings | Wrong owner editing/deleting | Non-creators could modify listings before enforcement | P0 | **Ownership validation added Nov 12–14; tests added.** |
| OPS-001 | CI/CD | Maven publish workflow | `maven-publish.yml` added; validate on PRs and release tags | P2 | Add secrets, dry-run, then enable. |
| OPS-002 | Hosting | Domain + DB setup | Site hosted on **dpdns**; DB on **Nile**; persist credentials | P1 | Infra docs + backups.  |
| **OPS-003** | Images | Image hosting instability | Occasional failures during upload in early Sprint-2 | P2 | Retry logic + log monitoring (US-013). |

---

## Non-Functional Requirements

- Auth & listing routes require logged-in session.  
- Email verification for all new accounts.  
- Availability: 99.5% during R1 demo week; daily backups for DB. 
---

## Sprint 1 Plan (from Release Plan) & Progress

- Planned: Auth, Email Verification, Docs/Demo.  
- Done: Forgot password; demo video recorded.   
- In-progress: Login with domain allow-list (`@yorku.ca`), fix redirect loop, SMTP verification.  
- Infra: Hosted on dpdns; DB running on Nile. 

---

## **Sprint 2 Plan (from Release Plan) & Progress**

- Focus: **Image hosting, search/browse, profile, moderation basics**.  
- Done (as of Nov 14):  
  - Image upload service functional  
  - Edit/delete ownership enforced  
  - Search UI prototype  
- In-progress:  
  - Full search backend filters  
  - Exchange-location page draft  
- Newly identified stories: **US-011, US-012, US-013**

---

## Backlog Ordering (current)

1. US-001 (Email login + allow-list) — P0  
2. BUG-001 (Redirect loop) — P0  
3. US-004 (Create listing) — P0  
4. **US-011 (Listing edit/delete ownership)** — P0  
5. US-006 (Image upload) — P1  
6. **US-012 (Exchange location)** — P1  
7. US-007 (Browse/search MVP) — P2  
8. US-003 (Profile) — P2  
9. OPS-001 (Maven publish) — P2  
10. US-008 (Report) — P3  
11. US-009 (DMs stub) — P3  
12. US-010 (Mark SOLD & rating) — P4  
13. **US-013 (Image reliability)** — P4  

