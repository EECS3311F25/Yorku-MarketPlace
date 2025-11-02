
# Product Backlog (pb.md)

**Project**: YorkU Marketplace  
**Release**: R1 — “MarketPlace for YorkU Students”  
**Timeframe**: Oct 20 – Dec 1, 2025

_Backlog compiled from the Release Plan and Sprint‑1 standups._

## References
- Release scope and timeline summarized in `rpm.md`. fileciteturn1file3
- Standup (Oct 27): hosted site on dpdns domain, set up database via Nile Web Services. fileciteturn1file1
- Standup (Oct 30): login system + `maven-publish.yml`; redirect loop when creating listing; plan SMTP verification limited to `@yorku.ca`. fileciteturn1file2
- Standup (Nov 3): forgot‑password feature and demo video complete. fileciteturn1file0

---

## Working Agreements

- **Definition of Ready (DoR)**: User story has clear persona, goal, benefit; acceptance criteria (Given/When/Then); test ideas; sized ≤ 8 SP; dependencies identified; no external blockers.
- **Definition of Done (DoD)**: Reviewed PR merged to `main`; CI green; unit tests for new code; lint/style pass; README/CHANGELOG updated; feature visible in test env; demo script updated.

---

## Epics (from Release Plan)
- **E1 — Account & Verification** (SSO/email, profile scaffold). fileciteturn1file3
- **E2 — Listings & Search** (create/browse/detail/upload). fileciteturn1file3
- **E3 — Messaging & Safety** (DMs, report) — partial in R1. fileciteturn1file3
- **E4 — Transactions & Reviews** — defer except “mark sold”. fileciteturn1file3

---

## User Stories

| ID | Epic | Title | As a… I want… so that… | Acceptance Criteria (Given/When/Then) | Priority | Est. SP | Notes/Deps |
|---|---|---|---|---|:--:|:--:|---|
| US‑001 | E1 | Email login + domain allow‑list | **YorkU student**; **sign up/login** with email; **keep community York‑only** | **Given** a user with `@yorku.ca` email **When** they register **Then** account is created and verification email sent; non‑York emails rejected | P0 | 8 | SMTP verification planned; restrict to `@yorku.ca`. fileciteturn1file2 |
| US‑002 | E1 | Forgot password | **user**; **reset password via link**; **recover access** | **Given** registered email **When** reset requested **Then** email sent; token valid 30 min; password policy enforced | P0 | 5 | Implemented in Sprint‑1. fileciteturn1file0 |
| US‑003 | E1 | Basic profile scaffold | **user**; **set display name and program**; **build trust** | Edit/save profile; default avatar; validation errors shown inline | P2 | 3 | Depends on US‑001 |
| US‑004 | E2 | Create listing | **seller**; **post item with title, price, images**; **sell faster** | Required fields; preview; saved draft; published visible in detail page | P0 | 8 | Blocked by login redirect loop. fileciteturn1file2 |
| US‑005 | E2 | Listing detail page | **buyer**; **view listing**; **decide quickly** | Carousel; seller card; report button; contact CTA from detail | P1 | 5 | Depends on US‑004 |
| US‑006 | E2 | Image upload service | **dev**; **store & serve images**; **fast previews** | Accept JPG/PNG; 5MB limit; thumbnail generated; invalid rejected | P1 | 5 | |
| US‑007 | E2 | Browse & search (MVP) | **buyer**; **filter by category/price**; **find items** | Text search; category filter; sort by recent | P2 | 8 | |
| US‑008 | E3 | Report listing/user | **user**; **report suspicious content**; **stay safe** | Report form; moderation inbox; audit trail | P3 | 5 | |
| US‑009 | E3 | Direct messages (stub) | **buyer/seller**; **DM** to coordinate | Start convo from listing; unread badge | P3 | 8 | Partial in R1 |
| US‑010 | E4 | Mark as SOLD & rating (defer) | **seller**; **close deal & rate** | Change status; leave 1‑5 stars + comment | P4 | 5 | Defer to late R1 |

---

## Defects / Tech Debt captured from Standups

| ID | Area | Title | Description | Priority | Fix Plan |
|---|---|---|---|:--:|---|
| BUG‑001 | Auth/Routes | Login→Listing redirect loop | Creating a listing bounces back to login due to session validation | P0 | Rebuild HTML/CSS template; audit route guards; add e2e test. fileciteturn1file2 |
| OPS‑001 | CI/CD | Maven publish workflow | `maven-publish.yml` added; validate on PRs and release tags | P2 | Add secrets, dry‑run, then enable. fileciteturn1file2 |
| OPS‑002 | Hosting | Domain + DB setup | Site hosted on **dpdns**; DB on **Nile**; persist credentials | P1 | Infra docs + backups. fileciteturn1file1 |

---

## Non‑Functional Requirements

- Auth & listing routes require logged‑in session (302s forbidden).  
- Email verification for all new accounts. fileciteturn1file
- Availability: 99.5% during R1 demo week; daily backups for DB. filecite turn file

---

## Sprint 1 Plan (from Release Plan) & Progress

- Planned: Auth, Email Verification, Docs/Demo. fileciteturn1file3  
- Done: Forgot password; demo video recorded. fileciteturn1file0  
- In‑progress: Login with domain allow‑list (`@yorku.ca`), fix redirect loop, SMTP verification. fileciteturn1file2  
- Infra: Hosted on dpdns; DB running on Nile. fileciteturn1file1

---

## Backlog Ordering (current)

1. US‑001 (Email login + allow‑list) — P0  
2. BUG‑001 (Redirect loop) — P0  
3. US‑004 (Create listing) — P0  
4. US‑005 (Listing detail) — P1  
5. OPS‑001 (Maven publish) — P2  
6. US‑003 (Profile) — P2  
7. US‑006 (Image upload) — P1  
8. US‑007 (Browse/search MVP) — P2  
9. US‑008 (Report) — P3  
10. US‑009 (DMs stub) — P3  
11. US‑010 (Mark SOLD & rating) — P4  

> **Note**: Story points assume a small team with academic load; cap stories at 8 SP. Velocity will be re‑estimated after Sprint‑1 review.
