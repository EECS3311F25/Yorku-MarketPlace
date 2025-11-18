Sprint 2 Demo and Retrospective

Project: YorkU Marketplace
Team: Manav Advani, Jeel Patel, Pulkit Grover, Pooja Shah, Purvesh Patel
Demo Date: November 17, 2025

1. Sprint 2 Demo Summary
We completed our Sprint 2 demo and recorded a five-minute walkthrough that includes a short recap of our Sprint 1 features and two major new features that we added during this sprint.

Recap of Sprint 1
We began by showing the login flow using a valid YorkU email. Only domains ending in @my.yorku.ca or @yorku.ca are accepted. The login worked end-to-end using our Spring Boot backend and our existing security layer. This confirmed that our foundations from Sprint 1 were stable.

New Feature 1: Creating a Product Listing
We demonstrated the full listing creation workflow. The user must fill in all required information before submitting. This includes the item name, description, category, condition, contact info, and price. We now have more listing templates than the four simple ones we used in Sprint 1. We also added verification checks that prevent unverified or incomplete listings from being submitted.

New Feature 2: Buyer and Seller Points of View
We showed two different perspectives in the marketplace.
In the seller view, the user can create listings, view their own items, edit them, and delete them.
In the buyer view, users can browse listings created by others and open item details.
These two views help simulate a real marketplace experience and show how our user roles behave differently.

Additional Improvements:
We gave a brief overview of the updated backend structure. Our controllers, services, and models now support listing creation, retrieval, editing, and deletion. The system overall is larger and more organized compared to Sprint 1.
We also demonstrated the edit and delete actions on listings and showed how our validation and verification logic connects with the backend.

2. Sprint 2 Retrospective
Practices We Should Continue:
We want to keep our daily standup routine. They have been helpful for staying aligned and keeping everyone accountable.
We also want to continue saving longer technical discussions for after the standup so the meeting stays short and efficient.
Another practice we want to keep is requiring at least one teammate to approve code before merging. This helped maintain code quality throughout the sprint.
Finally, the team has been consistent with our 9:30 AM meetings, and this has made a huge difference in our workflow.

New Practices We Want to Try Next Sprint
We want to adopt a more structured Git workflow using short-lived feature branches and pull requests instead of committing directly to the develop branch. This should reduce conflicts and make integration smoother.
We also want to integrate our Spring test files into the GitHub testing pipeline so that failing builds cannot be merged into the main branch.

Harmful Practices to Stop
We noticed that relying only on Discord chat for complicated issues usually slowed us down. Switching to voice calls or meeting in person solves the problem much faster.
We also want to avoid working in isolated silos for too long. A few features took extra time to integrate because we waited too long to sync them together.

Best Experience During Sprint 2
Our best moment was seeing the new listing features working end-to-end with the backend. Being able to show listings from both the buyer and seller perspectives made the project feel much more complete and realistic.

3. Notes for the TA
The Sprint 2 demo video includes all team members. It shows a working version of our new features and a brief recap of the Sprint 1 functionality.
All installation and setup instructions are included in our project README file.
The demo shows the real application running live, not a sequence of screenshots or pre-edited recordings.
Thank you for reviewing our Sprint 2 work. We are excited to continue improving the project in Sprint
