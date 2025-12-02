# Sprint 3 Documentation

---

## 1. Sprint 3 Demo Summary

We completed our Sprint 3 demo and recorded a walkthrough that highlights the main feature added in this final sprint, along with a short recap of the existing login flow and home page. This sprint focused on introducing real-time communication to the marketplace to make the platform feel more complete and interactive.

### Recap of Existing Features

We began the demo by logging in with a valid YorkU email and showing the transition into the home screen. This confirmed that the authentication system from previous sprints continues to work smoothly and maintains the YorkU-only access requirement.

### New Feature: Real-Time Chatting System

The major feature for Sprint 3 was the addition of a live chat system.  
We showcased a split-screen view that displayed the buyer’s chat window on one side and the seller’s window on the other. During the demo, we sent a message from one user and showed how it appeared instantly on the other user’s screen, demonstrating that real-time communication was functioning correctly.

We also introduced a dedicated chat window separate from the main listing view. This gives the marketplace a more realistic interaction flow, similar to actual buyer–seller platforms.

### Backend Additions and Improvements

After the UI demo, we walked through the backend and explained the new components supporting the chat system. This included our **ChatMessage model**, which stores:

- message ID  
- content  
- timestamp  

We also demonstrated how chat history is organized to support a user’s inbox and conversation list.

Finally, we showed how our **WebSocket implementation** enables real-time messaging and keeps both chat windows updated instantly as messages are sent.

This feature completes one of the final major components of our YorkU Marketplace project.

---

## 2. Sprint 3 Retrospective

### Practices We Should Continue
We would continue working the same way we have over the last few sprints, since our communication, task division, and regular meetings have consistently kept us organized and productive throughout the project.

### New Practices for Next Sprint
Since this is the last sprint, we would not have the opportunity to introduce any new practices.

### Harmful Practices to Stop
We fortunately have come to the conclusion that we have no harmful practices involved for this sprint, and we have refined our workflow enough to avoid repeating past issues.

### Best Experience During Sprint 3
Our best experience was implementing the chatting feature and seeing the separate chat window work smoothly. Watching real-time messages appear instantly on both sides and completing the final major feature of the project was extremely rewarding.

---

## 3. Notes for the TA

The Sprint 3 demo video includes all team members and shows the new chatting system working in real time using WebSockets, along with a short recap of our existing login and home screen flow.

All installation steps and setup instructions are included in our project README file.

The demo shows the actual live application running, not screenshots or pre-recorded segments.

Thank you for reviewing our Sprint 3 work and for following our progress throughout the project.
