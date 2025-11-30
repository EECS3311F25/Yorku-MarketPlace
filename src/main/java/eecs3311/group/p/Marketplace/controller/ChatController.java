package eecs3311.group.p.Marketplace.controller;

import eecs3311.group.p.Marketplace.dto.ChatMessageDTO;
import eecs3311.group.p.Marketplace.dto.InboxConversationDTO;
import eecs3311.group.p.Marketplace.model.ChatMessage;
import eecs3311.group.p.Marketplace.model.User;
import eecs3311.group.p.Marketplace.service.AuthService;
import eecs3311.group.p.Marketplace.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class ChatController {

    private final ChatService chatService;
    private final AuthService authService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(ChatService chatService, AuthService authService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.authService = authService;
        this.messagingTemplate = messagingTemplate;
    }

    // 1. Inbox Page
    @GetMapping("/inbox")
    public String getInbox(Model model, Principal principal) {
        User currentUser = authService.findByUsername(principal.getName()).orElseThrow();
        List<InboxConversationDTO> conversations = chatService.getUserConversations(currentUser.getId());
        model.addAttribute("conversations", conversations);
        return "inbox";
    }

    // 2. Chat Page
    @GetMapping("/chat/{listingId}/{otherUserId}")
    public String getChatPage(@PathVariable Long listingId, 
                              @PathVariable Long otherUserId, 
                              Model model, 
                              Principal principal) {
        
        User currentUser = authService.findByUsername(principal.getName()).orElseThrow();
        
        // Generate a unique room ID: "ListingID_BuyerID"
        // If current user is owner, otherUser is buyer. Room = listing_other
        // If current user is buyer, Room = listing_current
        // We need to know who is the owner of the listing to determine the 'BuyerID' part of the key consistently.
        // Simplified Logic: Just sort the two User IDs to make a unique key + Listing ID.
        // RoomId = ListingID_SmallUserID_BigUserID
        
        long u1 = Math.min(currentUser.getId(), otherUserId);
        long u2 = Math.max(currentUser.getId(), otherUserId);
        String chatId = listingId + "_" + u1 + "_" + u2;
        List<ChatMessage> history = chatService.getChatHistory(listingId, currentUser.getId(), otherUserId);
        model.addAttribute("chatHistory", history);
        model.addAttribute("chatId", chatId);
        model.addAttribute("currentUserId", currentUser.getId());
        model.addAttribute("otherUserId", otherUserId);
        model.addAttribute("listingId", listingId);
        // Load history (Optional: you can also fetch this via AJAX, but Model is easier)
        // Note: Your current chat.html doesn't iterate over history from Model, 
        // it expects WebSocket pushes. You might want to modify chat.html to show history on load.
        
        return "chat";
    }

    // 3. Handle sending messages via WebSocket
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessageDTO chatMessageDTO) {
        
        // Parse listingId from the chatId (Format: ListingID_UserA_UserB)
        String[] parts = chatMessageDTO.getChatId().split("_");
        Long listingId = Long.parseLong(parts[0]);

        // Save to DB
        ChatMessage saved = chatService.saveMessage(
                chatMessageDTO.getSenderId(),
                chatMessageDTO.getReceiverId(),
                listingId,
                chatMessageDTO.getContent()
        );

        // Send to the specific room topic
        messagingTemplate.convertAndSend("/topic/chat/" + chatMessageDTO.getChatId(), chatMessageDTO);
    }
}