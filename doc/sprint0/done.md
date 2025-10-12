# York Marketplace — Definition of Done

## Standard Definition of Done (applies to all user stories)

A user story is considered **DONE** when:

###  Code Implementation
- All acceptance criteria (Criteria of Satisfaction) are met.  
- Code has been reviewed, approved, and merged into the main branch.  
- The feature runs successfully without breaking existing functionality.  
- Follows consistent naming conventions, coding standards, and project structure.  

### Testing
- Unit tests and/or integration tests written and passed.  
- Manual testing completed for UI functionality.  
- No major or high-severity bugs remain open.  

### Documentation
- Feature is documented in the project’s `README.md` or related wiki page.  
- API endpoints (if any) are described with request/response examples.  
- Clear user instructions or screenshots are added (if applicable).  

###  Version Control
- Commits are meaningful and reference the corresponding user story or task (e.g., “US-5: Added listing creation form”).  
- The feature branch is merged into the main branch after peer review.  

### Deployment
- Feature deployed successfully to the staging or test environment.  
- Verified to work as expected in the deployed environment.  

### UI/UX Standards
- Design is consistent with the **York Marketplace** theme.  
- Works across supported browsers and screen sizes (Chrome, Safari, Edge).  
- Accessibility standards (contrast, alt text, keyboard navigation) are respected.  

### Security
- User data validated and sanitized to prevent injection or XSS attacks.  
- Authentication and authorization checks implemented where needed.  
- Follows the YorkU email verification and secure login requirements.  

---

## Additional Definition of Done (specific to this project)

###  YorkU Email Verification
- Only `@yorku.ca` or `@my.yorku.ca` users can register.  
- Unverified accounts are prevented from accessing protected pages.  

###  User Experience
- Notifications, dark mode, and mobile responsiveness are implemented according to design specs.  
- No broken links, missing images, or placeholder text in production build.  

###  Performance
- Page loads within **3 seconds** on standard campus Wi-Fi.  
- Images optimized for fast loading.  

### Data Integrity
- Listings, chats, and user data persist in the database correctly.  
- Deleting a user or listing removes all related data appropriately.  

###  Admin Oversight
- Admin can view, report, and remove inappropriate listings.  
- Admin actions are logged for accountability.  

---

## 🤝 Team Agreement
All members have reviewed and agreed to this **Definition of Done**.  
Each feature must meet **all** the above standards before being marked complete in **Trello** or **GitHub Projects**.

