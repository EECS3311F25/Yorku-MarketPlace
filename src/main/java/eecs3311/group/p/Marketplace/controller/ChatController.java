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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class ChatController {

    private final ChatService chatService;
    private final AuthService authService;
    private final SimpMessagingTemplate messagingTemplate;
    private final DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public ChatController(ChatService chatService, AuthService authService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.authService = authService;
        this.messagingTemplate = messagingTemplate;
    }

    // Inbox page
    @GetMapping("/inbox")
    public String getInbox(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";
        User currentUser = authService.findByUsername(principal.getName()).orElseThrow();
        List<InboxConversationDTO> conversations = chatService.getUserConversations(currentUser.getId());
        model.addAttribute("conversations", conversations);
        return "inbox";
    }

    // Chat page (renders initial history and chat metadata)
    @GetMapping("/chat/{listingId}/{otherUserId}")
    public String getChatPage(@PathVariable Long listingId,
                              @PathVariable Long otherUserId,
                              Model model,
                              Principal principal) {

        if (principal == null) return "redirect:/login";
        User currentUser = authService.findByUsername(principal.getName()).orElseThrow();
        Optional<User> otherUser = authService.findById(otherUserId);
        // compute deterministic chatId: listing_smallUserId_bigUserId
        long u1 = Math.min(currentUser.getId(), otherUserId);
        long u2 = Math.max(currentUser.getId(), otherUserId);
        String chatId = listingId + "_" + u1 + "_" + u2;

        List<ChatMessage> history = chatService.getChatHistory(listingId, currentUser.getId(), otherUserId);

        model.addAttribute("chatHistory", history);
        model.addAttribute("chatId", chatId);
        model.addAttribute("currentUserId", currentUser.getId());
        model.addAttribute("otherUserId", otherUserId);
        model.addAttribute("listingId", listingId);
        model.addAttribute("otherUser", otherUser);

        return "chat";
    }

    // WebSocket endpoint - receive from clients, save, broadcast to topic
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessageDTO chatMessageDTO) {
        // parse listing id from chatId (format: listing_u1_u2)
        String[] parts = chatMessageDTO.getChatId().split("_");
        Long listingId = Long.parseLong(parts[0]);

        // persist message
        ChatMessage saved = chatService.saveMessage(
                chatMessageDTO.getSenderId(),
                chatMessageDTO.getReceiverId(),
                listingId,
                chatMessageDTO.getContent()
        );

        // build outgoing DTO (with timestamp and senderName)
        ChatMessageDTO outgoing = new ChatMessageDTO();
        outgoing.setChatId(chatMessageDTO.getChatId());
        outgoing.setSenderId(saved.getSender().getId());
        outgoing.setReceiverId(saved.getRecipient().getId());
        outgoing.setSenderName(saved.getSender().getUsername());
        outgoing.setContent(saved.getContent());
        outgoing.setTimestamp(saved.getTimestamp().toString());

        // broadcast on topic for this chat
        messagingTemplate.convertAndSend("/topic/chat/" + chatMessageDTO.getChatId(), outgoing);
    }
}
